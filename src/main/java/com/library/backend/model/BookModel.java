package com.library.backend.model;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BookModel {

    @Id
    private String _id;
    private String title;
    private String author;
    private String isbnNo;
    private String genre;
    private String img;
    private Integer copies;
    private Date createdAt;
}
