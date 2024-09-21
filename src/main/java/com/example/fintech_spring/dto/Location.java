package com.example.fintech_spring.dto;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location implements Serializable {

    private String slug;
    private String name;

}
