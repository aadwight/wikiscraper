package com.example.wikiscraper.service.impl;

import com.example.wikiscraper.model.InfoSection;
import com.example.wikiscraper.model.WikiSection;
import com.example.wikiscraper.service.ScraperSvc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScraperSvcImpl implements ScraperSvc {

    @Value("${scraper.timeout:2000}")
    int timeout;

    @Value("${scraper.remote.url}")
    String sourceUrl;

    WikiSection tocSection;
    Map<String, InfoSection> infoSectionMap;

    @PostConstruct
    public void initialize() throws IOException {
        processDoc(scrape(sourceUrl));
    }

    @Override
    public Document scrape(String url) throws IOException {
        // TODO: update jsoup version and properly handle SSL/TLS errors
        return Jsoup.connect(url).timeout(timeout).validateTLSCertificates(false).get();
    }

    /**
     * Crawl the document and initialize the table of contents and content objects.
     *
     * @param doc
     */
    @Override
    public void processDoc(Document doc) {
        Element toc = doc.getElementById("toc");
        Elements titles = toc.getElementsByClass("toctext");
        Elements numbers = toc.getElementsByClass("tocnumber");

        tocSection = new WikiSection("root", "0");
        infoSectionMap = new HashMap<>();

        WikiSection parent = tocSection;

        // Create the toc and info entries for each section.
        for (int i = 0; i < titles.size(); i++) {
            String number = numbers.get(i).ownText();
            String title = titles.get(i).ownText().replaceAll(" ", "_");
            WikiSection section = new WikiSection(title, number);
            while (!number.startsWith(parent.getNumber()) && "0" != parent.getNumber()) {
                parent = parent.getParent();
            }
            section.setParent(parent);
            parent.addChild(section);
            parent = section;

            String text = doc.getElementById(title).parent().nextElementSibling().text();
            infoSectionMap.put(title, new InfoSection(title, text));
        }
    }

    public WikiSection getToc() {
        return tocSection;
    }

    @Override
    public InfoSection getContent(String subject) {
        return infoSectionMap.getOrDefault(subject, new InfoSection(subject, "No content found with this title."));
    }

}
