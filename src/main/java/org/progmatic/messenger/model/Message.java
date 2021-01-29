package org.progmatic.messenger.model;

import org.progmatic.messenger.helpers.DateHelper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Message {

    private static int nextId = 0;

    private Boolean isDeleted = false;

    private Integer id;

    private String author;

    @NotBlank
    @Size(min = 2, max = 250)
    private String text;

    @DateTimeFormat(pattern = DateHelper.DATE_TIME_FORMAT)
    private LocalDateTime creationTime;

    public Message(String author, String text, LocalDateTime creationTime) {
        this.id = nextId++;
        this.author = author;
        this.text = text;
        this.creationTime = creationTime;
    }

    public Message() {
        this.id = nextId++;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
