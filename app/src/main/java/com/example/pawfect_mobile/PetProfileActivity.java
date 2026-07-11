package com.example.pawfect_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class PetProfileActivity extends AppCompatActivity {

    private ImageView petImageView;
    private TextView petNameTextView;
    private TextView petBreedTextView;
    private TextView petAgeTextView;
    private TextView petMonthlyCostTextView;
    private TextView petDescriptionTextView;
    private Button btnAdoptMe;

    private DatabaseReference petReference;

    private Pet currentPet;

    // This is the Firebase child key
    private String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        initializeViews();

        petId = getIntent().getStringExtra("PET_ID");

        if (petId == null || petId.trim().isEmpty()) {
            Toast.makeText(
                    this,
                    "Pet ID was not provided",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        petReference = FirebaseDatabase
                .getInstance()
                .getReference("Pets")
                .child(petId);

        btnAdoptMe.setEnabled(false);

        loadPetData();

        btnAdoptMe.setOnClickListener(v -> openAdoptionRequest());
    }

    private void initializeViews() {
        petImageView = findViewById(R.id.petImageView);
        petNameTextView = findViewById(R.id.petNameTextView);
        petBreedTextView = findViewById(R.id.petBreedTextView);
        petAgeTextView = findViewById(R.id.petAgeTextView);
        petMonthlyCostTextView = findViewById(R.id.petMonthlyCostTextView);
        petDescriptionTextView = findViewById(R.id.petDescriptionTextView);
        btnAdoptMe = findViewById(R.id.btnAdoptMe);
    }

    private void loadPetData() {
        petReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            Toast.makeText(
                                    PetProfileActivity.this,
                                    "Pet details were not found",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();
                            return;
                        }

                        currentPet = snapshot.getValue(Pet.class);

                        if (currentPet == null) {
                            Toast.makeText(
                                    PetProfileActivity.this,
                                    "Unable to read pet details",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();
                            return;
                        }

                        updateUI();
                        btnAdoptMe.setEnabled(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(
                                PetProfileActivity.this,
                                "Failed to load pet: " + error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
    }

    private void updateUI() {
        String name = getSafeText(
                currentPet.getName(),
                "Unknown Pet"
        );

        String breed = getSafeText(
                currentPet.getBreed(),
                "Unknown breed"
        );

        String species = getSafeText(
                currentPet.getSpecies(),
                "Unknown species"
        );

        String age = getSafeText(
                currentPet.getAge(),
                "Age not available"
        );

        String description = getSafeText(
                currentPet.getDescription(),
                "No description available"
        );

        petNameTextView.setText(name);
        petBreedTextView.setText(breed + " • " + species);
        petAgeTextView.setText(age);
        petDescriptionTextView.setText(description);

        String monthlyCost = String.format(
                Locale.getDefault(),
                "Rs. %.2f/month",
                currentPet.getMonthlyCost()
        );

        petMonthlyCostTextView.setText(monthlyCost);

        String imageUrl = currentPet.getImageUrl();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
                    .into(petImageView);
        } else {
            petImageView.setImageResource(
                    android.R.color.darker_gray
            );
        }
    }

    private void openAdoptionRequest() {
        if (currentPet == null) {
            Toast.makeText(
                    this,
                    "Pet data is still loading",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Intent intent = new Intent(
                PetProfileActivity.this,
                AdoptionRequestActivity.class
        );

        // Use the Firebase child key
        intent.putExtra("PET_ID", petId);

        intent.putExtra(
                "PET_NAME",
                getSafeText(currentPet.getName(), "Unknown Pet")
        );

        startActivity(intent);
    }

    private String getSafeText(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }

        return value.trim();
    }
}