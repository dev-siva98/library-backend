package com.library.backend.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ordes")
public class Order {

    @Id
    private String id;
    private String userId;
    private String bookId;
    private Date createdAt;
    private Date returnedDate;
}
