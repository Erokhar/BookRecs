package com.bookrecommendations.bookrecs.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="book")
@Component("book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_Id")
    private int bookId;

    @Column(name = "title")
    private String bookTitle;

    //private String bookAuthor;

    @Column(name = "description")
    private String bookDescription;

    @Column(name = "isbn13")
    private String isbn13;

    @Column(name = "coverLink")
    private String bookCoverLink;

    @Column(name = "language_id")
    private int language_id;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "num_pages")
    private int numPages;

    public Book(){}

    public Book(String bookTitle,String bookDescription){
        this.bookTitle = bookTitle;
        //this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

//    public String getBookAuthor() {
//        return bookAuthor;
//    }
//
//    public void setBookAuthor(String bookAuthor) {
//        this.bookAuthor = bookAuthor;
//    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookCoverLink() {
        return bookCoverLink;
    }

    public void setBookCoverLink(String bookCoverLink) {
        this.bookCoverLink = bookCoverLink;
    }


    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}
