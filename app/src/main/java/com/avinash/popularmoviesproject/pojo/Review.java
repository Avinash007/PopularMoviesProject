package com.avinash.popularmoviesproject.pojo;

/**
 * Created by AVINASH on 09-05-2017.
 */

public class Review {

    private String author;
    private String content;

    public Review() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Review: " +"\n"+
                "author: " + author +"\n"+
                "content=" + content + "\n\n\n"
                ;
    }
}
