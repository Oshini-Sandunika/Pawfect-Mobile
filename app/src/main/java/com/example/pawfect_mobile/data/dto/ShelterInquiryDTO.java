package com.example.pawfect_mobile.data.dto;

import com.example.pawfect_mobile.data.models.Shelter;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShelterInquiryDTO implements Serializable {
    private String petId;
    private Shelter shelter;
}
