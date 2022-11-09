package com.library.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "previousIds")
public class PreviousIdModel {
    @Id
    private String _id; //to make sure the same document gets updated with incremented id's
    private Integer userId;
    private Integer bookId;
}
