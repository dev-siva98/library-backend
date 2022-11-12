package com.library.backend.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbnNo;
    private String genre;
    private String img;
    private Integer totalCopies;
    private Integer copiesAvailable;
    private Date createdAt;
}
