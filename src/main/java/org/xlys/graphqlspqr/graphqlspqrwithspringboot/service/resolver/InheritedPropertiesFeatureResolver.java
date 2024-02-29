package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.inheritedTypes.ChildType;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.inheritedTypes.ParentType;

import java.util.List;
import java.util.Random;

/**
 * TODO
 *
 * @author Administrator
 * @date 2024/1/26 4:59 PM
 */
//@Service
@RestController
@GraphQLApi
@NoArgsConstructor
public class InheritedPropertiesFeatureResolver {

    public static final List<ParentType> parentPool = List.of(
            new ParentType(1, "Yellow", "Dark"),
            new ParentType(2, "Brown", "White"),
            new ParentType(3, "Black", "Black")
    );

    public static final List<ChildType> childPool = List.of(
            new ChildType(11, "Michael", 99.99),
            new ChildType(22, "Brown", 98.89),
            new ChildType(33, "Black", 89.89)
    );

    @GraphQLQuery(name = "getRandomParent")
    public ParentType getParent() {
        return parentPool.get(new Random().nextInt(parentPool.size()));
    }

    @GraphQLQuery(name = "getRandomChild")
    public ChildType getChild() {
        return childPool.get(new Random().nextInt(childPool.size()));
    }
}
