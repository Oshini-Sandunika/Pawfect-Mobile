package com.example.pawfect_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pawfect_mobile.R;
import com.example.pawfect_mobile.data.PetService;
import com.example.pawfect_mobile.data.dto.ShelterInquiryDTO;
import com.example.pawfect_mobile.data.models.Pet;
import com.example.pawfect_mobile.data.models.Shelter;

import java.util.Locale;

public class PetProfileFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pet_profile, container, false);
        initializeViews(view);

        if (getArguments() != null) {
            petId = getArguments().getString("PET_ID");
        }

        if (petId == null || petId.trim().isEmpty()) {
            Toast.makeText(
                    requireContext(),
                    "Pet ID was not provided",
                    Toast.LENGTH_SHORT
            ).show();

            requireActivity().getOnBackPressedDispatcher().onBackPressed();
            return view;
        }

        btnAdoptMe.setEnabled(false);
        loadPetData();
        btnAdoptMe.setOnClickListener(v -> openAdoptionRequest());

        return view;
    }

    private void initializeViews(View view) {
        petImageView = view.findViewById(R.id.petImageView);
        petNameTextView = view.findViewById(R.id.petNameTextView);
        petBreedTextView = view.findViewById(R.id.petBreedTextView);
        petAgeTextView = view.findViewById(R.id.petAgeTextView);
        petMonthlyCostTextView = view.findViewById(R.id.petMonthlyCostTextView);
        petAdoptionFeeTextView = view.findViewById(R.id.petAdoptionFeeTextView);
        petDescriptionTextView = view.findViewById(R.id.petDescriptionTextView);
        btnAdoptMe = view.findViewById(R.id.btnAdoptMe);
        shelterSection = view.findViewById(R.id.shelterSection);
        shelterLogoImageView = view.findViewById(R.id.shelterLogoImageView);
        shelterNameTextView = view.findViewById(R.id.shelterNameTextView);
        shelterAddressTextView = view.findViewById(R.id.shelterAddressTextView);
        shelterPhoneTextView = view.findViewById(R.id.shelterPhoneTextView);
        shelterEmailTextView = view.findViewById(R.id.shelterEmailTextView);
    }

    private void loadPetData() {
        PetService.INSTANCE.getPetByIdCB(petId, pet -> {
            requireActivity().runOnUiThread(() -> {
                if (pet == null) {
                    Toast.makeText(
                            requireContext(),
                            "Pet details were not found",
                            Toast.LENGTH_SHORT
                    ).show();

                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
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
                    requireContext(),
                    "Pet data is still loading",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Shelter s = currentPet.getShelter();
        ShelterInquiryDTO dto = new ShelterInquiryDTO(petId, s);
        AdoptionRequestDialog dialog = AdoptionRequestDialog.newInstance(dto);
        dialog.show(getChildFragmentManager(), "ShelterInquiryDialog");
    }

    private String getSafeText(String value, String fallback) {
        if (value == null || value.trim().isEmpty()) {
            return fallback;
        }

        return value.trim();
    }
}