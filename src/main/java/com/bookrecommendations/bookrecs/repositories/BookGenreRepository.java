package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre,Long> {

    List<BookGenre> findByBookId(Integer book_id);
    List<BookGenre> findByGenreId(Integer genre_id);
    List<BookGenre> findByGenreIdIn(Set<Integer> genre_id);
    List<BookGenre> findByBookIdIn(Set<Integer> genre_id);
}
