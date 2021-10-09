package com.bookrecommendations.bookrecs.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book_user")
@Component("book_user")
public class BookUser implements Serializable {

    @Id
    @Column(name = "book_user_id")
    private Integer bookUserId;

    @Column(name = "book_id")
    private Integer bookid;

    
    @Column(name = "user_id")
    private Integer userid;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
