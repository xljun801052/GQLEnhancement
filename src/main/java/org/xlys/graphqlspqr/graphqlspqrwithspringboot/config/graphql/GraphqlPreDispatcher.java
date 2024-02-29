package org.xlys.graphqlspqr.graphqlspqrwithspringboot.config.graphql;

import graphql.schema.GraphQLSchema;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.annotations.WithResolverBuilder;
import io.leangen.graphql.spqr.spring.autoconfigure.BaseAutoConfiguration;
import io.leangen.graphql.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver.InheritedPropertiesFeatureResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    ConfigurableApplicationContext context;

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

            /**
             * findGraphQLApiComponents
             * */
            final String[] apiBeanNames = context.getBeanNamesForAnnotation(GraphQLApi.class);
            final ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

//            Map<String, BaseAutoConfiguration.SpqrBean> result = new HashMap<>();
            for (String beanName : apiBeanNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                AnnotatedType beanType;
                Set<WithResolverBuilder> resolverBuilders;
                if (beanDefinition.getSource() instanceof StandardMethodMetadata) {
                    StandardMethodMetadata metadata = (StandardMethodMetadata) beanDefinition.getSource();
                    beanType = metadata.getIntrospectedMethod().getAnnotatedReturnType();
                    resolverBuilders = AnnotatedElementUtils.findMergedRepeatableAnnotations(metadata.getIntrospectedMethod(), WithResolverBuilder.class);
                } else {
                    BeanDefinition current = beanDefinition;
                    BeanDefinition originatingBeanDefinition = current;
                    while (current != null) {
                        originatingBeanDefinition = current;
                        current = current.getOriginatingBeanDefinition();
                    }
                    ResolvableType resolvableType = originatingBeanDefinition.getResolvableType();
                    if (resolvableType != ResolvableType.NONE && Utils.isNotEmpty(originatingBeanDefinition.getBeanClassName())
                            //Sanity check only -- should never happen
                            && !originatingBeanDefinition.getBeanClassName().startsWith("org.springframework.")) {
                        beanType = GenericTypeReflector.annotate(resolvableType.getType());
                    } else {
                        beanType = GenericTypeReflector.annotate(AopUtils.getTargetClass(context.getBean(beanName)));
                    }
                    resolverBuilders = AnnotatedElementUtils.findMergedRepeatableAnnotations(beanType, WithResolverBuilder.class);
                }
//                List<BaseAutoConfiguration.ResolverBuilderBeanCriteria> builders = resolverBuilders.stream()
//                        .map(builder -> new BaseAutoConfiguration.ResolverBuilderBeanCriteria(builder.value(), builder.qualifierValue(), builder.qualifierType()))
//                        .collect(Collectors.toList());
//                result.put(beanName, new BaseAutoConfiguration.SpqrBean(context, beanName, beanType, builders));
            }

            registerDynamicApi("test", InheritedPropertiesFeatureResolver.class, "getParent");
            //note: ERROR--> IllegalArgumentException: Expected parsed RequestPath in request attribute "org.springframework.web.util.ServletRequestPathUtils.PATH".
//            requestMappingHandlerMapping.getHandlerMethods();
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
//            HandlerMethod handlerMethod = new HandlerMethod(controllerInstance, method);

            requestMappingHandlerMapping.registerMapping(mapping, controllerInstance, method);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
