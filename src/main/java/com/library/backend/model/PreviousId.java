package com.library.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "previousIds")
public class PreviousId {
    private String type;
    private Integer previousId;
}
