package com.bookrecommendations.bookrecs.repositories;

import com.bookrecommendations.bookrecs.models.Book;
import com.bookrecommendations.bookrecs.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
    List<User> findByUserId(int userId);

    @Query(value = "select * from user LIMIT 2", nativeQuery = true)
    List<User> findAll();
}
