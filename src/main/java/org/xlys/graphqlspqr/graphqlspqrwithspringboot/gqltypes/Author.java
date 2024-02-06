package org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GRAPHQL_AUTHOR",schema = "XLYS")
public class Author {

    @Id
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private List<Hobby> hobbies;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Author() {
    }

//    public Author(String id, String firstName, String lastName) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//
//    private static List<Author> authors = Arrays.asList(
//            new Author("author-1", "Joanne", "Rowling"),
//            new Author("author-2", "Herman", "Melville"),
//            new Author("author-3", "Anne", "Rice")
//    );


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
