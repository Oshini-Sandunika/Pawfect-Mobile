package com.example.pawfect_mobile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdoptionRequestActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPhone, etReason;
    private Button btnSubmitRequest;

    private DatabaseReference mDatabase;
    private String petId, petName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(Bundle.valueOf(1));
        setContentView(R.layout.activity_adoption_request);

        petId = getIntent().getStringExtra("PET_ID");
        petName = getIntent().getStringExtra("PET_NAME");

        if (petId == null) petId = "unknown";
        if (petName == null) petName = "Unknown Pet";

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etReason = findViewById(R.id.etReason);
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);

        mDatabase = FirebaseDatabase.getInstance().getReference("AdoptionRequests");

        btnSubmitRequest.setOnClickListener(v -> submitRequest());
    }

    private void submitRequest() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String reason = etReason.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Valid email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone is required");
            etPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(reason)) {
            etReason.setError("Reason is required");
            etReason.requestFocus();
            return;
        }

        String requestId = mDatabase.push().getKey();
        if (requestId != null) {
            AdoptionRequest request = new AdoptionRequest(
                    requestId,
                    petId,
                    petName,
                    name,
                    email,
                    phone,
                    reason,
                    "Pending",
                    System.currentTimeMillis()
            );

            mDatabase.child(requestId).setValue(request)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdoptionRequestActivity.this, "Request submitted successfully!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AdoptionRequestActivity.this, "Failed to submit. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
