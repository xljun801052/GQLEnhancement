package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Author;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IAuthorService;

import java.util.List;

@Service
@GraphQLApi
public class AuthorResolver {

    @Autowired
    IAuthorService authorService;

    @GraphQLQuery(name = "getAuthorById")
    public Author getAuthorById(@GraphQLArgument(name = "id") Integer id) {
        return authorService.getAuthorById(id);
    }

    @GraphQLQuery(name = "getAllAuthors", description = "Get all Authors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GraphQLMutation(name = "addAuthor")
    public Author addAuthor(@GraphQLArgument(name = "newAuthor") Author author) {
        return authorService.addAuthor(author);
    }

    @GraphQLMutation(name = "updateAuthor")
    public Author updateAuthor(@GraphQLArgument(name = "modifiedAuthor") Author author) {
        return authorService.updateAuthor(author);
    }

    @GraphQLMutation(name = "deleteAuthor")
    public void deleteAuthor(@GraphQLArgument(name = "Author") Author author) {
        authorService.deleteAuthor(author);
    }
}
