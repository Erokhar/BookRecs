package com.bookrecommendations.bookrecs.models;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_author")
@Component("book_author")
public class BookAuthor {
    @Id
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "book_id")
    private Integer bookId;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
