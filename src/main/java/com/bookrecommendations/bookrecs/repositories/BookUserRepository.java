package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, Long> {
    List<BookUser> findByBookidAndUserid(int bookid, int userid);
    List<BookUser> findByBookid(int bookid);
    List<BookUser> findByUserid(int userid);
    List<BookUser> findAll();
}
