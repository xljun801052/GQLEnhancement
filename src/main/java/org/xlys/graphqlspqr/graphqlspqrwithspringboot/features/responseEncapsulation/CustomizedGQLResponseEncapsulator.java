package org.xlys.graphqlspqr.graphqlspqrwithspringboot.features.responseEncapsulation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * encapsulate response from GraphQL engine and adapt to restful api style
 *
 * @author Administrator
 * @date 2024/3/1 9:35 PM
 */
@Component
@Slf4j
public class CustomizedGQLResponseEncapsulator {

//    public Object reOrganizeResponse(Object gqlResponse) {
//        ((Mono) gqlResponse).block()
//    }
}
