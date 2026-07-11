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

public class PetProfileActivity extends AppCompatActivity {

    private ImageView petImageView;
    private TextView petNameTextView, petBreedTextView, petAgeTextView, petMonthlyCostTextView, petDescriptionTextView;
    private Button btnAdoptMe;

    private DatabaseReference mDatabase;
    private Pet currentPet;
    private String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(Bundle.valueOf(1));
        setContentView(R.layout.activity_pet_profile);

        // Initialize Views
        petImageView = findViewById(R.id.petImageView);
        petNameTextView = findViewById(R.id.petNameTextView);
        petBreedTextView = findViewById(R.id.petBreedTextView);
        petAgeTextView = findViewById(R.id.petAgeTextView);
        petMonthlyCostTextView = findViewById(R.id.petMonthlyCostTextView);
        petDescriptionTextView = findViewById(R.id.petDescriptionTextView);
        btnAdoptMe = findViewById(R.id.btnAdoptMe);

        // Get Pet ID from Intent (if not passed, use a mock ID for testing)
        petId = getIntent().getStringExtra("PET_ID");
        if (petId == null || petId.isEmpty()) {
            petId = "mock_pet_1"; // Used for testing if not navigated from a list
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("Pets").child(petId);
        loadPetData();

        btnAdoptMe.setOnClickListener(v -> {
            if (currentPet != null) {
                Intent intent = new Intent(PetProfileActivity.this, AdoptionRequestActivity.class);
                intent.putExtra("PET_ID", currentPet.getId());
                intent.putExtra("PET_NAME", currentPet.getName());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Pet data is still loading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPetData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentPet = snapshot.getValue(Pet.class);
                    if (currentPet != null) {
                        updateUI();
                    }
                } else {
                    // Create dummy pet if it doesn't exist just for demonstration purposes
                    createDummyPet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PetProfileActivity.this, "Failed to load pet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        petNameTextView.setText(currentPet.getName());
        petBreedTextView.setText(currentPet.getBreed() + " • " + currentPet.getSpecies());
        petAgeTextView.setText(currentPet.getAge());
        petMonthlyCostTextView.setText("$" + String.format("%.2f", currentPet.getMonthlyCost()) + "/mo");
        petDescriptionTextView.setText(currentPet.getDescription());

        if (currentPet.getImageUrl() != null && !currentPet.getImageUrl().isEmpty()) {
            Glide.with(this)
                 .load(currentPet.getImageUrl())
                 .placeholder(android.R.color.darker_gray)
                 .into(petImageView);
        }
    }

    private void createDummyPet() {
        currentPet = new Pet(
            petId,
            "Bella",
            "Dog",
            "Golden Retriever",
            "2 yrs",
            "Bella is a very friendly and playful Golden Retriever. She loves outdoor activities and is great with kids. She is fully vaccinated and looking for a loving forever home.",
            "https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&q=80&w=800",
            45.50
        );
        mDatabase.setValue(currentPet);
        updateUI();
    }
}
