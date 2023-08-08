package com.boot.board230801.controller;

import com.boot.board230801.model.User;
import com.boot.board230801.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
  @Autowired
  UserService userService;

  //post 호출인데 GetMapping 받는것 확인
  @GetMapping("/login")
  public String login(){
    return "account/login";
  }
  @GetMapping("/register")
  public String register(){
    return "account/register";
  }
  @PostMapping("/register")
  public String register(User user){
    userService.save(user);

    return "redirect:/";
  }
}
