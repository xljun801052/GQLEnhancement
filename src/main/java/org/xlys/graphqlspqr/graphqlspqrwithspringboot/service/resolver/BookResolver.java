package org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Book;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.IBookService;

import java.util.List;

@Service
@GraphQLApi
public class BookResolver {

    @Autowired
    IBookService bookService;

    @GraphQLQuery(name = "getBookById")
    public Book getBookById(@GraphQLArgument(name = "id") Integer id) {
        return bookService.getBookById(id);
    }

    @GraphQLQuery(name = "getAllBooks", description = "Get all books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GraphQLMutation(name = "addBook")
    public Book addBook(@GraphQLArgument(name = "newBook") Book book) {
        return bookService.addBook(book);
    }

    @GraphQLMutation(name = "updateBook")
    public Book updateBook(@GraphQLArgument(name = "modifiedBook") Book book) {
        return bookService.updateBook(book);
    }

    @GraphQLMutation(name = "deleteBook")
    public void deleteBook(@GraphQLArgument(name = "book") Book book) {
        bookService.deleteBook(book);
    }
}
