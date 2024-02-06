package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Hobby;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IHobbyService;

import java.util.List;

@Service
@GraphQLApi
public class HobbyResolver {

    @Autowired
    IHobbyService hobbyService;

    @GraphQLQuery(name = "getHobbyById")
    public Hobby getHobbyById(@GraphQLArgument(name = "id") Integer id) {
        return hobbyService.getHobbyById(id);
    }

    @GraphQLQuery(name = "getAllHobbys", description = "Get all Hobbys")
    public List<Hobby> getAllHobbys() {
        return hobbyService.getAllHobbys();
    }

    @GraphQLMutation(name = "addHobby")
    public Hobby addHobby(@GraphQLArgument(name = "newHobby") Hobby Hobby) {
        return hobbyService.addHobby(Hobby);
    }

    @GraphQLMutation(name = "updateHobby")
    public Hobby updateHobby(@GraphQLArgument(name = "modifiedHobby") Hobby Hobby) {
        return hobbyService.updateHobby(Hobby);
    }

    @GraphQLMutation(name = "deleteHobby")
    public void deleteHobby(@GraphQLArgument(name = "Hobby") Hobby Hobby) {
        hobbyService.deleteHobby(Hobby);
    }
}
