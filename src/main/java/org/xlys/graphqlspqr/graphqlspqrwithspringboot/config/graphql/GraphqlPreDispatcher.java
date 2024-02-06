package org.xlys.graphqlspqr.graphqlspqrwithspringboot.config.graphql;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.spqr.spring.autoconfigure.BaseAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver.InheritedPropertiesFeatureResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @date 2024/2/3 10:50 AM
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "graphql.spqr.customized.dispatcher.enabled", havingValue = "true", matchIfMissing = false)
public class GraphqlPreDispatcher {


    @Resource
    GraphQLSchema schema;

    @Resource
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostConstruct
    public void init() {
        log.info("start to initialize GraphqlPreDispatcher ");
        // step1: fetch all graphql api
        try {
//            Method graphQLApiComponents = ReflectionUtils.findMethod(BaseAutoConfiguration.class, "findGraphQLApiComponents");
//            Object graphqlApis = graphQLApiComponents.invoke(null);
//            System.out.println("graphqlApis = " + graphqlApis);
//            List<GraphQLFieldDefinition> queryFieldDefinitions = schema.getQueryType().getFieldDefinitions();
//            List<GraphQLFieldDefinition> mutationFieldDefinitions = schema.getMutationType().getFieldDefinitions();
//            ArrayList<Object> operationSourcesApi = new ArrayList<>();
//            operationSourcesApi.addAll(queryFieldDefinitions);
//            operationSourcesApi.addAll(mutationFieldDefinitions);
            registerDynamicApi("test", InheritedPropertiesFeatureResolver.class,"getParent");
            //note: ERROR--> IllegalArgumentException: Expected parsed RequestPath in request attribute "org.springframework.web.util.ServletRequestPathUtils.PATH".
            requestMappingHandlerMapping.getHandlerMethods();
        } catch (Exception e) {
            log.error("Error ");
            e.printStackTrace();
        }

        // step2: convert graphql api to restful api

        // step3: register new generated restful api

    }


    public void registerDynamicApi(String path, Class<?> controllerClass, String methodName) {
        try {
            Method method = ReflectionUtils.findMethod(controllerClass, methodName);
            Object controllerInstance = ReflectionUtils.accessibleConstructor(controllerClass, null).newInstance();

//            RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
//            options.setPatternParser(new PathPatternParser());
            RequestMappingInfo mapping = RequestMappingInfo
                    .paths(path)
                    .methods(RequestMethod.GET)
//                    .options(options)
                    .build();

            assert method != null;
            HandlerMethod handlerMethod = new HandlerMethod(controllerInstance, method);

            requestMappingHandlerMapping.registerMapping(mapping, handlerMethod, method);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
