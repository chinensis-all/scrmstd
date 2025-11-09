package com.mayanshe.scrmstd.bossapi.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final Environment env;

    public HomeController(@Lazy Environment env) {
        this.env = env;
    }

    @GetMapping("/")
    public String index() {
        return String.format("%s [ V%s ]", env.getProperty("app.name"), env.getProperty("app.version"));
    }

}
