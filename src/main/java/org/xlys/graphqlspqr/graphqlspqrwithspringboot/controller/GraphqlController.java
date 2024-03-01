package org.xlys.graphqlspqr.graphqlspqrwithspringboot.controller;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import io.leangen.graphql.spqr.spring.web.GraphQLController;
import io.leangen.graphql.spqr.spring.web.dto.GraphQLRequest;
import io.leangen.graphql.spqr.spring.web.dto.TransportType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.server.ServerWebExchange;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.utils.HttpRequestUtil;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@RestController
@Slf4j
public class GraphqlController {

    @Resource
    private GraphQL graphQL;


    @PostMapping(value = "/graphql")
    public Map<String, Object> execute(@RequestBody Map<String, String> request, ServerWebExchange exchange)
            throws GraphQLException {
        ExecutionResult result = graphQL.execute(request.get("query"));
        return result.getData();
    }

    @Autowired
    ApplicationContext context;

    public Object resolve(HttpServletRequest request) {
        // step1: extract query content and assemble GraphQLRequest
        String queryContent = "";
        try {
            queryContent = HttpRequestUtil.getRequestJsonString(request);
            log.info("Query content: [{}]", queryContent);
        } catch (IOException e) {
            log.error("Error {} occurs when extract body content of request.", e.getMessage());
        }
        GraphQLRequest requestBody = new GraphQLRequest(null, queryContent, null, Collections.emptyMap());
        GraphQLRequest requestParams = new GraphQLRequest(null, queryContent, null, Collections.emptyMap());

        // step2: find SpringMVC container and extract GraphqlExecutor Bean
        // step2.1: try to find instance from Spring Container
        /*Map<String, GraphQLController> controllers = context.getBeansOfType(GraphQLController.class);
        if (controllers.isEmpty()) {
            log.warn("Can't find any available GraphQLController instance in context.");
        }*/

        // step2.2: try to find instance from SpringMVC Container
        ServletContext servletContext = request.getServletContext();
        // 从ServletContext中获取WebApplicationContext
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        // 从ApplicationContext中获取Spring MVC的容器
        WebApplicationContext springMvcContext = (WebApplicationContext) applicationContext;
        GraphQLController controller = (GraphQLController) springMvcContext.getBean("graphQLController");

        // step3: leverage GraphQLExecutor to execute GraphQLRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = requestAttributes.getRequest();
        Object executionResult = controller.jsonPost(requestBody, requestParams, servletRequest, TransportType.HTTP);
        return executionResult;

    }
}

