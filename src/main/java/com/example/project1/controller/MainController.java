package com.example.project1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController
{
    @GetMapping("/")
    public String index()
    {
        return "Welcome to the Home Page!";
    }

    @GetMapping("/settings")
    public String getSettings()
    {
        return "Welcome to the Settings Page!";
    }

    @GetMapping("/profile")
    public String getProfile()
    {
        return "Welcome to the Profile Page!";
    }
}