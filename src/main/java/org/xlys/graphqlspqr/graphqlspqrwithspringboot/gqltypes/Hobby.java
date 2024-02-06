package org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes;

import javax.persistence.*;

@Table(name = "GRAPHQL_HOBBY",schema = "XLYS")
@Entity
public class Hobby {


    @Id
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}