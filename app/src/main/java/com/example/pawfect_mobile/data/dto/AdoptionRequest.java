package com.example.pawfect_mobile.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionRequest {
    private String requestId;
    private String petId;
    private String petName;
    private String applicantName;
    private String applicantEmail;
    private String applicantPhone;
    private String reason;
    private String status;
    private long timestamp;
}
