package com.example.fintech_spring.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category implements Serializable {

    private int id;

    private String slug;

    private String name;
}
