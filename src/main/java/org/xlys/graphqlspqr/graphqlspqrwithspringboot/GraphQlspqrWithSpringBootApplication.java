package org.xlys.graphqlspqr.graphqlspqrwithspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Features:
 *  1. all properties
 *  2. api name
 *  3. inherited properties
 *  4. error handling and response encapsulation
 *
 * */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true) // note: Error--->The mapped handler method class 'XXX' is not an instance of the actual controller bean
public class GraphQlspqrWithSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphQlspqrWithSpringBootApplication.class, args);
    }

}
