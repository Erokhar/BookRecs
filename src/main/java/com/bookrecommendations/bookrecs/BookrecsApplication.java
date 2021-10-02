package com.bookrecommendations.bookrecs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication()
@EnableJpaRepositories("com.bookrecommendations.bookrecs.repositories")
@EntityScan("com.bookrecommendations.bookrecs.*")
public class BookrecsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(BookrecsApplication.class, args);    }

}
