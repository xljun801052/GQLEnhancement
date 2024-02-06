package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service;

import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Author;

import java.util.List;

public interface IAuthorService {

    Author getAuthorById(Integer id);

    List<Author> getAllAuthors();

    Author addAuthor(Author author);

    Author updateAuthor(Author author);

    boolean deleteAuthor(Author author);
}
