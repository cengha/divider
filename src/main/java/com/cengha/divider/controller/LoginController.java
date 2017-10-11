package com.cengha.divider.controller;

import com.cengha.divider.model.User;
import com.cengha.divider.service.UserService;
import com.cengha.divider.spring.annotation.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/login")
    public String getLogin(@RequestParam(value = "error",defaultValue = "false")Boolean error,
                           Model model){
        model.addAttribute("error",error);
        return "login";
    }


    @GetMapping(path = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

    @GetMapping(path="/register")
    public String registration(@ActiveUser User activeUser,
                               Model model){
        if(activeUser!=null) return "redirect:/index.html";
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ActiveUser User activeUser,
                               @ModelAttribute(name = "user")User user,
                               @CookieValue(value = "cartCookieId", defaultValue = "cartCookieId")String cartCookieId,
                               HttpServletResponse response,
                               Model model){
        if(activeUser!=null) return "redirect:/index.html";

        user = userService.registerAndAuthenticateUser(user);
        model.addAttribute("user",user);
        return "redirect:/index.html";
    }
}
