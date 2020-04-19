package com.example.wikiscraper.controller;

import com.example.wikiscraper.model.InfoSection;
import com.example.wikiscraper.model.WikiSection;
import com.example.wikiscraper.service.ScraperSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    ScraperSvc scraperSvc;

    @GetMapping({"/", ""})
    public String hello(){
        return "api controller";  // todo: add usage instructions or link to documentation
    }

    @GetMapping("/toc")
    public WikiSection getToc() {
        return scraperSvc.getToc();
    }

    @GetMapping("/info/{subject}")
    public InfoSection getInfo(@PathVariable String subject){
        return scraperSvc.getContent(subject);
    }
}
