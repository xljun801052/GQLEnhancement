package org.xlys.graphqlspqr.graphqlspqrwithspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
