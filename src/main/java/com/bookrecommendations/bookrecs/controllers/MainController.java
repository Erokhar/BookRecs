package com.bookrecommendations.bookrecs.controllers;

import com.bookrecommendations.bookrecs.models.Book;
import com.bookrecommendations.bookrecs.models.BookUser;
import com.bookrecommendations.bookrecs.repositories.BookRepository;
import com.bookrecommendations.bookrecs.repositories.BookUserRepository;
import com.bookrecommendations.bookrecs.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@ComponentScan("com.bookrecommendations.bookrecs.repositories")
public class MainController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

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

        modelAndView.setViewName("libraryView");
        return modelAndView;
    }

}

//TODO Library
//TODO Authentification System
//TODO Session Management (login/logout)
//TODO Add pictures to the books
//TODO Add Search functionality


