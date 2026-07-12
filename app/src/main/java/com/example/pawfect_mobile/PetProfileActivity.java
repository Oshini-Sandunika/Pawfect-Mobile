package com.example.pawfect_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.pawfect_mobile.data.PetService;
import com.example.pawfect_mobile.data.dto.ShelterInquiryDTO;
import com.example.pawfect_mobile.data.models.Pet;
import com.example.pawfect_mobile.data.models.Shelter;

import java.util.Locale;

public class PetProfileActivity extends FragmentActivity {

    private ImageView petImageView;
    private TextView petNameTextView;
    private TextView petBreedTextView;
    private TextView petAgeTextView;
    private TextView petMonthlyCostTextView;
    private TextView petAdoptionFeeTextView;
    private TextView petDescriptionTextView;
    private Button btnAdoptMe;
    private LinearLayout shelterSection;
    private ImageView shelterLogoImageView;
    private TextView shelterNameTextView;
    private TextView shelterAddressTextView;
    private TextView shelterPhoneTextView;
    private TextView shelterEmailTextView;


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
        petAdoptionFeeTextView = findViewById(R.id.petAdoptionFeeTextView);
        petDescriptionTextView = findViewById(R.id.petDescriptionTextView);
        btnAdoptMe = findViewById(R.id.btnAdoptMe);
        shelterSection = findViewById(R.id.shelterSection);
        shelterLogoImageView = findViewById(R.id.shelterLogoImageView);
        shelterNameTextView = findViewById(R.id.shelterNameTextView);
        shelterAddressTextView = findViewById(R.id.shelterAddressTextView);
        shelterPhoneTextView = findViewById(R.id.shelterPhoneTextView);
        shelterEmailTextView = findViewById(R.id.shelterEmailTextView);
    }

    private void loadPetData() {
        PetService.INSTANCE.getPetByIdCB(petId, pet -> {
            runOnUiThread(() -> {
                if (pet == null) {
                    Toast.makeText(
                            PetProfileActivity.this,
                            "Pet details were not found",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                    return;
                }

                currentPet = pet;
                updateUI();
                btnAdoptMe.setEnabled(true);
            });
            return kotlin.Unit.INSTANCE;
        });
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
        petAdoptionFeeTextView.setText(currentPet.getAdoptionFee() == 0 ? "Free" : String.format("Rs. %.2f", currentPet.getAdoptionFee()));

        String imageUrl = currentPet.getImageUrl();


        if (currentPet.getShelter() != null) {
            shelterSection.setVisibility(View.VISIBLE);

            Shelter s = currentPet.getShelter();
            shelterNameTextView.setText(getSafeText(s.getName(), "Unknown Shelter"));
            shelterAddressTextView.setText(getSafeText(s.getAddress(), "Unknown Address"));
            shelterPhoneTextView.setText("Phone: " + getSafeText(s.getPhone(), "N/A"));
            shelterEmailTextView.setText("Email: " + getSafeText(s.getEmail(), "N/A"));

            String logoUrl = s.getLogo();
            if (!logoUrl.trim().isEmpty()) {
                Glide.with(this)
                        .load(logoUrl)
                        .placeholder(android.R.color.darker_gray)
                        .error(android.R.color.darker_gray)
                        .into(shelterLogoImageView);
            } else {
                shelterLogoImageView.setImageResource(android.R.color.darker_gray);
            }
        } else {
            shelterSection.setVisibility(View.GONE);
        }

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
        if (currentPet == null || currentPet.getShelter() == null) {
            Toast.makeText(
                    this,
                    "Pet data is still loading",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Shelter s = currentPet.getShelter();
        ShelterInquiryDTO dto = new ShelterInquiryDTO(petId, s);
        AdoptionRequestDialog dialog = AdoptionRequestDialog.newInstance(dto);
        dialog.show(getSupportFragmentManager(), "ShelterInquiryDialog");
    }

    private String getSafeText(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }

        return value.trim();
    }
}