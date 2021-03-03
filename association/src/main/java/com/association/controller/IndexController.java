package com.association.controller;

import com.association.service.impl.ILoginServiceImpl;
import org.apache.catalina.filters.ExpiresFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/Index")
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    private ILoginServiceImpl login;


}
