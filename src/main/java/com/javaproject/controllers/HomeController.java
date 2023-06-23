package com.javaproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String getHomePage() {
        return "index";
    }
}
