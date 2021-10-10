package com.bookrecommendations.bookrecs.controllers;

import com.bookrecommendations.bookrecs.models.*;
import com.bookrecommendations.bookrecs.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/browseView")
    public ModelAndView process(ModelAndView modelAndView, HttpServletRequest request) {
        if(request.getSession().getAttribute("userid")==null){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        Set<Integer> bookIds = new HashSet<>();
        Map<Integer, Book> mapBooksById = new HashMap<>();
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            mapBooksById.put(book.getBookId(), book);
            bookIds.add(book.getBookId());
        }

        books = setAuthorsOfBooks(bookIds, mapBooksById);

        modelAndView.addObject("books", books);
        modelAndView.setViewName("browseView");
        return modelAndView;
    }

    @PostMapping("/addToLibrary")
    public String addToLibrary(ModelAndView modelAndView, HttpServletRequest request) {
        if(request.getSession().getAttribute("userid")==null){
            return "redirect:/";
        }
        BookUser bookUser = new BookUser();
        Boolean inLibrary = false;
        bookUser.setBookid(Integer.parseInt(request.getParameter("bookId")));
        bookUser.setUserid(Integer.parseInt(request.getSession().getAttribute("userid").toString()));
        List<BookUser> resultSet = bookUserRepository.findAll();
        for (BookUser bu : resultSet) {
            if (bookUser.getBookid() == (bu.getBookid())) {
                inLibrary = true;
            }
        }
        if (!inLibrary) {
            bookUser.setBookUserId(resultSet.size() + 1);
            bookUserRepository.save(bookUser);
        }
        return "redirect:/library";
    }

    @RequestMapping("/library")
    public ModelAndView getLibraryView(ModelAndView modelAndView, HttpServletRequest request) {
        if(request.getSession().getAttribute("userid")==null){
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
        Set<Integer> bookIds = new HashSet<>();
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

        mapGenresByBooks = getMapGenresByBooks(bookIds, mapGenresById);
        bookList = setAuthorsOfBooks(bookIds, mapBooksById);

        modelAndView.addObject("mapGenresByBooks", mapGenresByBooks);
        modelAndView.addObject("bookList", bookList);
        modelAndView.setViewName("libraryView");
        return modelAndView;
    }

    public Map<Integer, String> getMapGenresByBooks(Set<Integer> bookIds, Map<Integer, Genre> mapGenresById) {
        Set<Integer> genreIds = new HashSet<>();
        List<BookGenre> bookGenreList = bookGenreRepository.findByBookIdIn(bookIds);
        for (BookGenre bookGenre : bookGenreList) {
            genreIds.add(bookGenre.getGenre_id());
        }
        List<Genre> genreList = genreRepository.findByGenreIdIn(genreIds);
        for (Genre genre : genreList) {
            mapGenresById.put(genre.getGenreId(), genre);
        }
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

    public List<Book> setAuthorsOfBooks(Set<Integer> bookIds, Map<Integer, Book> mapBooksById) {
        Set<Integer> authorIds = new HashSet<>();
        List<BookAuthor> bookAuthorList = bookAuthorRepository.findByBookIdIn(bookIds);
        for (BookAuthor bookAuthor : bookAuthorList) {
            authorIds.add(bookAuthor.getAuthorId());
        }
        List<Author> authors = authorRepository.findByAuthorIdIn(authorIds);
        logger.info("" + authorIds.size());
        for (Author author : authors) {
            for (BookAuthor bookAuthor : bookAuthorList) {
                if (author.getAuthorId().equals(bookAuthor.getAuthorId())) {
                    mapBooksById.get(bookAuthor.getBookId()).setBookAuthor(author.getAuthorName());
                }
            }
        }
        List<Book> books = new ArrayList<>();
        for (Integer key : mapBooksById.keySet()) {
            books.add(mapBooksById.get(key));
        }

        return books;
    }

    @RequestMapping("/bookView")
    public ModelAndView getBookView(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("bookView");
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView getLoginView(ModelAndView modelAndView, HttpServletRequest request) {
        if (request.getSession().getAttribute("wrongPassword") != null) {
            modelAndView.addObject("wrongPassword", request.getSession().getAttribute("wrongPassword"));
            request.getSession().removeAttribute("wrongPassword");
        }
        if (request.getSession().getAttribute("userNotFound") != null) {
            modelAndView.addObject("userNotFound", request.getSession().getAttribute("userNotFound"));
            request.getSession().removeAttribute("userNotFound");
        }
        modelAndView.setViewName("loginView");
        return modelAndView;
    }

    @RequestMapping("/connectUser")
    public ModelAndView connectUser(ModelAndView modelAndView, HttpServletRequest request) {
        List<User> users = userRepository.findByUsername(request.getParameter("userLogin"));
        User user;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
            if (user.getUserpassword().equals(request.getParameter("userPassword"))) {
                request.getSession().setAttribute("userid", user.getUserId());
                modelAndView.setViewName("redirect:/browseView");
            } else {
                request.getSession().setAttribute("wrongPassword", "Wrong Password!");
                //modelAndView.addObject("wrongPassword","Wrong Password!");
                modelAndView.setViewName("redirect:/");
                return modelAndView;
            }
        } else {
            request.getSession().setAttribute("userNotFound", "Username Not Found!");
            //modelAndView.addObject("userNotFound","Username Not Found!");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        return modelAndView;
    }

    @RequestMapping("/logoutUser")
    public ModelAndView logoutUser(ModelAndView modelAndView, HttpServletRequest request){
        request.getSession().removeAttribute("userid");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}

//TODO Improve Library
//TODO BookView
//TODO Session Management (login/logout)
//TODO Add Search functionality


