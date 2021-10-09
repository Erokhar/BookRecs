package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor,Long> {

    List<BookAuthor> findByBookIdIn(Set<Integer> ids);

}
