package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Book;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.repository.BookRepository;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IBookService;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElse(new Book());
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public boolean deleteBook(Book book) {
        try {
            bookRepository.delete(book);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
