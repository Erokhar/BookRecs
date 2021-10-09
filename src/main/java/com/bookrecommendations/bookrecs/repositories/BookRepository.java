package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookTitle(String BookTitle);

    List<Book> findByBookId(int bookId);
    List<Book> findBooksByBookIdIn(@Param("ids")Set<Integer> ids);

    @Query(value = "select * from book b LIMIT 2", nativeQuery = true)
    List<Book> findAll();
}
