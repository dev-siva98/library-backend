package com.library.backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    private String userRole;
    private String dateOfBirth;
    private List<Order> orderedBooks = new ArrayList<>();
    private Date createdAt;
}
