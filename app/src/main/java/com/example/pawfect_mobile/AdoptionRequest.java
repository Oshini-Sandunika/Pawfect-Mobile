package com.example.pawfect_mobile;

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

    public AdoptionRequest() {
        // Default constructor for Firebase
    }

    public AdoptionRequest(String requestId, String petId, String petName, String applicantName, String applicantEmail, String applicantPhone, String reason, String status, long timestamp) {
        this.requestId = requestId;
        this.petId = petId;
        this.petName = petName;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.applicantPhone = applicantPhone;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getPetId() { return petId; }
    public void setPetId(String petId) { this.petId = petId; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getApplicantPhone() { return applicantPhone; }
    public void setApplicantPhone(String applicantPhone) { this.applicantPhone = applicantPhone; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
