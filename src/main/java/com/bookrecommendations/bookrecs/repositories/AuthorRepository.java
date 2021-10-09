package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Override
    List<Author> findAll();
    @Override
    Optional<Author> findById(Long aLong);

    List<Author> findByAuthorIdIn(Set<Integer> ids);
}
