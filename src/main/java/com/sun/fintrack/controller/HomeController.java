package com.sun.fintrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  @GetMapping(value = {"/", "/login"})
  public ModelAndView login() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("login");

    return mav;
  }
}
