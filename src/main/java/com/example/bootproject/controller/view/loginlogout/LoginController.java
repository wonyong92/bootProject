package com.example.bootproject.controller.view.loginlogout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/is-login")
    @ResponseBody
    public boolean isLogin(HttpSession session){
        return session.getAttribute("id")!=null?true:false;
    }
}
