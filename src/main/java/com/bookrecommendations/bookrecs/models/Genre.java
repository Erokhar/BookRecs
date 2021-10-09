package com.bookrecommendations.bookrecs.models;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
@Component("genre")
public class Genre {

    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "title")
    private String title;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer book_id) {
        this.genreId = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
