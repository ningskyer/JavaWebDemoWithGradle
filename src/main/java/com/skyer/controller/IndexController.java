package com.skyer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;   
import org.slf4j.LoggerFactory; 

@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public ModelAndView index() {
    	Logger logger = LoggerFactory.getLogger(IndexController.class);

        logger.info("server index :{}","SLF4J");

        return new ModelAndView("index");

    }
    @RequestMapping({"/hello"})
    public ModelAndView hello() {

        return new ModelAndView("hello");

    }
    @RequestMapping({"/test"})
    public ModelAndView test() {

        return new ModelAndView("hello");

    }
}