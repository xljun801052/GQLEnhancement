package org.xlys.graphqlspqr.graphqlspqrwithspringboot.features.distinguishAPIName;

import graphql.schema.GraphQLSchema;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 1. automatically generate restful apis based on graphql apis
 * 2. register restful apis
 * */
/**
 * TODO
 *
 * @author Administrator
 * @date 2024/2/3 10:50 AM
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "graphql.spqr.customized.dispatcher.enabled", havingValue = "true", matchIfMissing = false)
public class RestfulAPILayerAutoGenerator {

    public static final String RestfulApiLayerEntry = "dispatch";
    public static final String GQLApiLayerEntry = "executeJsonPost";
    @Resource
    ConfigurableApplicationContext context;
    @Resource
    GraphQLSchema schema;
    @Resource
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void init() {
        log.info("start to initialize GraphqlPreDispatcher ");
        // step1: fetch all graphql api
        /**
         * findGraphQLApiComponents
         * */
        final String[] apiBeanNames = context.getBeanNamesForAnnotation(GraphQLApi.class);


        // step2: convert graphql api to restful api

        // step3: register new generated restful api
//        registerDynamicApi(RestfulApiLayerEntry, GraphqlController.class, RestfulApiLayerEntry,new Class[]{HttpServletRequest.class});
        registerDynamicApi("gql2RestfulApiEntry", CustomizedGQLDispatcher.class, RestfulApiLayerEntry, new Class[]{});
        //note: ERROR--> IllegalArgumentException: Expected parsed RequestPath in request attribute "org.springframework.web.util.ServletRequestPathUtils.PATH".

    }


    public void registerDynamicApi(String path, Class<?> controllerClass, String methodName, Class<?>[] parameterTypes) {
        try {
            Method method = ReflectionUtils.findMethod(controllerClass, methodName, parameterTypes);
            Object controllerInstance = ReflectionUtils.accessibleConstructor(controllerClass, new Class[]{}).newInstance();

            RequestMappingInfo mapping = RequestMappingInfo
                    .paths(path)
                    .methods(RequestMethod.POST)
                    .build();

            assert method != null;
            requestMappingHandlerMapping.registerMapping(mapping, controllerInstance, method);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
