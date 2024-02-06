package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Author;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.repository.AuthorRepository;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IAuthorService;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElse(new Author());
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author addAuthor(Author Author) {
        return authorRepository.saveAndFlush(Author);
    }

    @Override
    public Author updateAuthor(Author Author) {
        return authorRepository.saveAndFlush(Author);
    }

    @Override
    public boolean deleteAuthor(Author Author) {
        try {
            authorRepository.delete(Author);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
