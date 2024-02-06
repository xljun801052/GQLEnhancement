package org.xlys.graphqlspqr.graphqlspqrwithspringboot.controller;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
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
}

