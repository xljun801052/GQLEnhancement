package org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes;

import org.xlys.graphqlspqr.graphqlspqrwithspringboot.annotation.Invisible;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GRAPHQL_BOOK",schema = "XLYS")
public class Book {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PAGE_COUNT")
    private int pageCount;

    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER)
    private List<Author> authors;

    @Invisible
    @Transient
    private boolean deleted;

    public Book() {
    }

//    public Book(Integer id, String name, int pageCount, Author authorId) {
//        this.id = id;
//        this.name = name;
//        this.pageCount = pageCount;
//        this.authorId = authorId;
//    }
//
//    private static List<Book> books = Arrays.asList(
//            new Book(1, "Harry Potter and the Philosopher's Stone", 223, "author-1"),
//            new Book(2, "Moby Dick", 635, "author-2"),
//            new Book(3, "Interview with the vampire", 371, "author-3")
//    );
//
//    public static Book getById(String id) {
//        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}