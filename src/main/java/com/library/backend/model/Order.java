package com.library.backend.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Order {

    private String bookId;
    private Date createdAt;
}
