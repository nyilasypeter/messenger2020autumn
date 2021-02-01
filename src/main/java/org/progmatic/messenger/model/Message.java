package org.progmatic.messenger.model;

import org.progmatic.messenger.helpers.DateHelper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Entity
public class Message extends BaseEntity {

    private Boolean isDeleted = false;

    @ManyToOne
    private User author;

    @NotBlank
    @Size(min = 2, max = 250)
    private String text;


    public Message() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
