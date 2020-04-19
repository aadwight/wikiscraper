package com.example.wikiscraper.service;

import com.example.wikiscraper.model.InfoSection;
import com.example.wikiscraper.model.WikiSection;
import com.example.wikiscraper.service.impl.ScraperSvcImpl;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ScraperSvcTest {

    ScraperSvc svc;
    String testFile = "src/test/resources/wiki_text.html";

    @Before
    public void setUp() throws IOException {
        svc = new ScraperSvcImpl();
//        svc.scrape("https://en.wikipedia.org/wiki/Heidenheim_an_der_Brenz");
        svc.processDoc(Jsoup.parse(new File(testFile), "UTF-8"));
    }

    @Test
    public void testToc(){
        WikiSection toc = svc.getToc();
        Assert.assertNotNull(toc);
    }

    @Test
    public void testContent(){
        String expected = "There is evidence that human life existed within the city limits of Heidenheim as far back as 8,000 years ago. However, a permanent settlement was not established until approximately 1300 BC. Extensive ruins remain of settlements dating, predominantly, to the period from 1200 to 800 BC.";
        InfoSection actual = svc.getContent("History");
        Assert.assertEquals("Content did not match expected", expected, actual.getContent());
    }

    @Test
    public void testContentMising(){
        String expected = "No content found with this title.";
        InfoSection actual = svc.getContent("Kittens");
        Assert.assertEquals("Content did not match expected", expected, actual.getContent());
    }
}
