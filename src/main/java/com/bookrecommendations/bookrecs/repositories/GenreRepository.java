package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    List<Genre> findByGenreId(@Param("genre_id")Integer genre_id);
    List<Genre> findByGenreIdIn(@Param("genre_id") Set<Integer> genre_id);
    List<Genre> findAll();

}
