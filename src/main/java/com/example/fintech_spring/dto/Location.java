package com.example.fintech_spring.dto;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location implements Serializable {
    private UUID uuid;
    private String slug;
    private String name;

}
