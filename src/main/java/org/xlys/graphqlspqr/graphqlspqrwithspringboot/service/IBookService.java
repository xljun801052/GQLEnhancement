package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service;

import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Book;

import java.util.List;

public interface IBookService {

    Book getBookById(Integer id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(Book book);
}
