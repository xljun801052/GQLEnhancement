package org.xlys.graphqlspqr.graphqlspqrwithspringboot.features.distinguishAPIName;


import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.leangen.graphql.spqr.spring.web.GraphQLController;
import io.leangen.graphql.spqr.spring.web.dto.GraphQLRequest;
import io.leangen.graphql.spqr.spring.web.dto.TransportType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.features.responseEncapsulation.CustomizedGQLResponse;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.utils.HttpRequestUtil;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;


@RestController
@Slf4j
public class CustomizedGQLDispatcher {

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Entry point for restful api invocation to gql engine
     */
    public Object dispatch() {
        // step1: extract query content and assemble GraphQLRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        String rawQueryContent = "";
        String gqlQueryContent = "";
        Map<String, Object> gqlQueryParameters = Collections.emptyMap();
        try {
            rawQueryContent = HttpRequestUtil.getRequestJsonString(request);
            gqlQueryContent = JSONObject.parseObject(rawQueryContent).get("query").toString();
            gqlQueryParameters = mapper.convertValue(JSONObject.parseObject(rawQueryContent).get("variables"), Map.class);
            log.info("Query content: {}", gqlQueryContent);
            log.info("Query Parameters: {}", gqlQueryParameters);
        } catch (IOException e) {
            log.error("Error {} occurs when perform the extraction from raw request.", e.getMessage());
        }
        GraphQLRequest requestBody = new GraphQLRequest(null, gqlQueryContent, null, gqlQueryParameters);
        GraphQLRequest requestParams = requestBody;

        // step2: find SpringMVC container and extract GraphqlExecutor Bean
        // step2.1: try to find instance from Spring Container---> child container cannot access parent container
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
        ServletWebRequest gqlRequestFacade = new DispatcherServletWebRequest(request);
        Object executionResult = controller.jsonPost(requestBody, requestParams, gqlRequestFacade, TransportType.HTTP);

        // step4: encapsulate GraphQL Response and adapt to restful style
        if (executionResult instanceof Mono) {
            Map<String, Object> gqlResponse = (Map<String, Object>) ((Mono<?>) executionResult).block();
            List<Map<String, Object>> errors = (List<Map<String, Object>>) gqlResponse.get("errors");
            Map<String, Object> data = (Map<String, Object>) gqlResponse.get("data");
            if (isNull(errors)) {
                log.info("Completely succeed");
                response.setStatus(HttpStatus.OK.value());
                return new CustomizedGQLResponse<>(HttpStatus.OK.value(), "Completely succeed", data, Collections.emptyList());
            } else if (isNull(data)) {
                log.info("Completely failed");
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new CustomizedGQLResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Completely failed", Collections.emptyMap(), errors);
                /*new HashMap<String, Object>() {{
                    put("exception", new RuntimeException("Completely failed exception, pls refer to errors part for more details.").getMessage());
                    put("errors", errors);
                }};*/
            } else {
                log.info("Partially succeed");
                response.setStatus(501);
                return new CustomizedGQLResponse<>(501, "Partially succeed", data, errors);
                /*new HashMap<String, Object>() {{
                    put("exception", new RuntimeException("Partially succeed exception, pls refer to errors part for more details.").getMessage());
                    put("errors", errors);
                    put("data", data);
                }};*/
            }
        }
        return executionResult;

    }
}

