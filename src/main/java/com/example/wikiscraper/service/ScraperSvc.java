package com.example.wikiscraper.service;

import com.example.wikiscraper.model.InfoSection;
import com.example.wikiscraper.model.WikiSection;
import org.jsoup.nodes.Document;

import java.io.IOException;

public interface ScraperSvc {
    Document scrape(String url) throws IOException;

    WikiSection getToc();

    InfoSection getContent(String subject);

    void processDoc(Document parse);
}
