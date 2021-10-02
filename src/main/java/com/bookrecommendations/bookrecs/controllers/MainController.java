package com.bookrecommendations.bookrecs.controllers;

import com.bookrecommendations.bookrecs.models.Book;
import com.bookrecommendations.bookrecs.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ComponentScan("com.bookrecommendations.bookrecs.repositories")
public class MainController {

    @Autowired
    BookRepository bookRepository;

    Logger logger = LoggerFactory.getLogger(MainController.class);

    public MainController(){}

    @RequestMapping("/index")
    public ModelAndView process(ModelAndView modelAndView) {
        List<Book> books = bookRepository.findAll();
        modelAndView.addObject("strings",books);
        modelAndView.setViewName("browseView");
        return modelAndView;
    }
}
