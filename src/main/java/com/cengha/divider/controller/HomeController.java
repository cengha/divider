package com.cengha.divider.controller;

import com.cengha.divider.model.User;
import com.cengha.divider.spring.annotation.ActiveUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/index", "/"})
public class HomeController {

    @GetMapping
    public String getIndex(@ActiveUser User user,
                           Model model){
        model.addAttribute("user", user);
        return "index";
    }
}
