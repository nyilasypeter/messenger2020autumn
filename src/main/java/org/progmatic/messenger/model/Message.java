package org.progmatic.messenger.model;

import java.time.LocalDateTime;

public class Message {

    private String author;
    private String text;
    private LocalDateTime creationTime;

    public Message(String author, String text, LocalDateTime creationTime) {
        this.author = author;
        this.text = text;
        this.creationTime = creationTime;
    }

    public Message() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
