package org.xlys.graphqlspqr.graphqlspqrwithspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
}
