package com.example.pawfect_mobile.data.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet implements Serializable {
    private String name;
    private String species;
    private String breed;
    private String age;
    private String description;
    private String imageUrl;
    private double monthlyCost;
}