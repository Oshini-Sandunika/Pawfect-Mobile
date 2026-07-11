package com.example.pawfect_mobile;

import java.io.Serializable;

public class Pet implements Serializable {

    private String name;
    private String species;
    private String breed;
    private String age;
    private String description;
    private String imageUrl;
    private double monthlyCost;

    public Pet() {
        // Required by Firebase
    }

    public Pet(
            String name,
            String species,
            String breed,
            String age,
            String description,
            String imageUrl,
            double monthlyCost
    ) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.imageUrl = imageUrl;
        this.monthlyCost = monthlyCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }
}