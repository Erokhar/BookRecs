package com.bookrecommendations.bookrecs.controllers;

import com.bookrecommendations.bookrecs.models.*;
import com.bookrecommendations.bookrecs.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@ComponentScan("com.bookrecommendations.bookrecs.repositories")
public class MainController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookGenreRepository bookGenreRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookAuthorRepository bookAuthorRepository;

    @Autowired
    BookUserRepository bookUserRepository;

    Logger logger = LoggerFactory.getLogger(MainController.class);

    public MainController() {
    }

    @RequestMapping("/")
    public ModelAndView process(ModelAndView modelAndView, HttpServletRequest request) {
        request.getSession().setAttribute("userid", "1");
        List<Book> books = bookRepository.findAll();
        modelAndView.addObject("books", books);
        modelAndView.setViewName("browseView");
        return modelAndView;
    }

    @PostMapping("/addToLibrary")
    public String addToLibrary(ModelAndView modelAndView, HttpServletRequest request) {
        BookUser bookUser = new BookUser();
        bookUser.setBookid(Integer.parseInt(request.getParameter("bookId")));
        bookUser.setUserid(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
        bookUserRepository.save(bookUser);
        return "redirect:/library";
    }

    @RequestMapping("/library")
    public ModelAndView getLibraryView(ModelAndView modelAndView, HttpServletRequest request) {
        Set<Integer> bookIds = new HashSet<>();
        Set<Integer> genreIds = new HashSet<>();
        Set<Integer> authorIds = new HashSet<>();
        Map<Integer, String> mapGenresByBooks;
        Map<Integer, Genre> mapGenresById = new HashMap<>();
        Map<Integer, Book> mapBooksById = new HashMap<>();

        List<BookUser> bookUserList = bookUserRepository.findByUserid(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
        for (BookUser bookUser : bookUserList) {
            bookIds.add(bookUser.getBookid());
        }
        List<Book> bookList = bookRepository.findBooksByBookIdIn(bookIds);
        for (Book book : bookList) {
            mapBooksById.put(book.getBookId(), book);
        }
        List<BookGenre> bookGenreList = bookGenreRepository.findByBookIdIn(bookIds);
        for (BookGenre bookGenre : bookGenreList) {
            genreIds.add(bookGenre.getGenre_id());
        }
        List<Genre> genreList = genreRepository.findByGenreIdIn(genreIds);
        for (Genre genre : genreList) {
            mapGenresById.put(genre.getGenreId(), genre);
        }
        mapGenresByBooks = getMapGenresByBooks(bookIds, bookGenreList, mapGenresById);

        //TODO Do the same process as the book_genre for the book_author

        List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookIdIn(bookIds);
        for (BookAuthor bookAuthor : bookAuthorList) {
            authorIds.add(bookAuthor.getAuthorId());
        }
        List<Author> authors = authorRepository.findByAuthorIdIn(authorIds);
        for (Author author : authors) {
            for (BookAuthor bookAuthor : bookAuthorList) {
                if (author.getAuthorId().equals(bookAuthor.getAuthorId())) {
                    mapBooksById.get(bookAuthor.getBookId()).setBookAuthor(author.getAuthorName());
                }
            }
        }

        modelAndView.addObject("mapGenresByBooks", mapGenresByBooks);
        modelAndView.addObject("bookList", bookList);
        modelAndView.setViewName("libraryView");
        return modelAndView;
    }

    public Map<Integer, String> getMapGenresByBooks(Set<Integer> bookIds, List<BookGenre> bookGenreList, Map<Integer, Genre> mapGenresById) {
        Map<Integer, String> mapGenresByBooks = new HashMap<>();
        for (Integer bookId : bookIds) {
            for (BookGenre bookGenre : bookGenreList) {
                if (bookId == bookGenre.getBook_id()) {
                    mapGenresByBooks.putIfAbsent(bookId, "");
                    if (mapGenresById.get(bookGenre.getGenre_id()) != null && !mapGenresByBooks.get(bookId).equals("")) {
                        mapGenresByBooks.put(bookId, mapGenresByBooks.get(bookId) + ", " + mapGenresById.get(bookGenre.getGenre_id()).getTitle());
                    } else {
                        mapGenresByBooks.put(bookId, mapGenresById.get(bookGenre.getGenre_id()).getTitle());
                    }
                }
            }
        }

        return mapGenresByBooks;
    }

}

//TODO Library
//TODO Authentification System
//TODO Session Management (login/logout)
//TODO Add pictures to the books
//TODO Add Search functionality


