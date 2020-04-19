package com.example.wikiscraper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WikiSection {

    String subject;
    List<WikiSection> children = new ArrayList<>();

    @JsonIgnore
    WikiSection parent;

    @JsonIgnore
    String number;

    public WikiSection(String subject, String number) {
        this.subject = subject;
        this.number = number;
    }

    public boolean addChild(WikiSection child) {
        return children.add(child);
    }

    public List<WikiSection> getChildren() {
        return new ArrayList<>(children);
    }

    public boolean addChildren(List<WikiSection> children) {
        return children.addAll(children);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public WikiSection getParent() {
        return parent;
    }

    public void setParent(WikiSection parent) {
        this.parent = parent;
    }
}
