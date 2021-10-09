package com.bookrecommendations.bookrecs.models;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "book_genre")
@Component("book_genre")
public class BookGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "book_genre_id")
    private Integer bookGenreId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "genre_id")
    private Integer genreId;

    public Integer getBookGenre_id() {
        return bookGenreId;
    }

    public void setBookGenre_id(Integer bookGenre_id) {
        this.bookGenreId = bookGenre_id;
    }

    public Integer getBook_id() {
        return bookId;
    }

    public void setBook_id(Integer book_id) {
        this.bookId = book_id;
    }

    public Integer getGenre_id() {
        return genreId;
    }

    public void setGenre_id(Integer genre_id) {
        this.genreId = genre_id;
    }
}
