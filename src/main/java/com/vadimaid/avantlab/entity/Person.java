package com.vadimaid.avantlab.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Person {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

}
