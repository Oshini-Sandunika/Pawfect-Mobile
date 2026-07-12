package com.example.pawfect_mobile;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.pawfect_mobile.data.InquiryService;
import com.example.pawfect_mobile.data.dto.ShelterInquiryDTO;
import com.example.pawfect_mobile.data.models.Shelter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AdoptionRequestDialog extends DialogFragment {

    private static final String ARG_INQUIRY_DTO = "inquiry_dto";

    private ShelterInquiryDTO dto;

    public static AdoptionRequestDialog newInstance(ShelterInquiryDTO dto) {
        AdoptionRequestDialog fragment = new AdoptionRequestDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_INQUIRY_DTO, dto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dto = getArguments().getSerializable(ARG_INQUIRY_DTO, ShelterInquiryDTO.class);
            if (dto != null && (dto.getShelter().getLocation().isEmpty())) {
                dto.getShelter().setLocation(dto.getShelter().getAddress());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_shelter_inquiry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvName = view.findViewById(R.id.tvShelterName);
        ImageView ivLogo = view.findViewById(R.id.ivShelterLogo);
        TextView tvDescription = view.findViewById(R.id.tvShelterDescription);
        TextView tvAddress = view.findViewById(R.id.tvShelterAddress);
        TextView tvPhone = view.findViewById(R.id.tvShelterPhone);
        TextView tvEmail = view.findViewById(R.id.tvShelterEmail);
        Button btnLocation = view.findViewById(R.id.btnLocation);
        TextInputEditText etInquiry = view.findViewById(R.id.etInquiryMessage);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        if (dto == null) {
            return;
        }

        Shelter shelter = dto.getShelter();
        tvName.setText(shelter.getName());
        tvDescription.setText(shelter.getDescription());

        if (!shelter.getLogo().isEmpty()) {
            Glide.with(this)
                    .load(shelter.getLogo())
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
                    .into(ivLogo);
        } else {
            ivLogo.setImageResource(android.R.color.darker_gray);
        }

        tvAddress.setText(shelter.getAddress());

        String phone = shelter.getPhone();
        tvPhone.setText("Phone: " + phone);
        tvPhone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        String email = shelter.getEmail();
        tvEmail.setText("Email: " + email);
        tvEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            startActivity(intent);
        });

        btnLocation.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(shelter.getLocation()));
            startActivity(new Intent(Intent.ACTION_VIEW, gmmIntentUri));
        });

        btnCancel.setOnClickListener(v -> dismiss());

        btnSubmit.setOnClickListener(v -> {
            String msg = Objects.requireNonNull(etInquiry.getText()).toString().trim();
            if (msg.isEmpty()) {
                etInquiry.setError("Inquiry message is required");
                return;
            }
            if (dto == null) {
                dismiss();
                return;
            }

            btnSubmit.setEnabled(false);
            InquiryService.INSTANCE.submitInquiry(dto.getPetId(), shelter.getId(), msg, success -> {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(getContext(), "Inquiry submitted!", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Failed to submit inquiry.", Toast.LENGTH_SHORT).show();
                            btnSubmit.setEnabled(true);
                        }
                    });
                }
                return kotlin.Unit.INSTANCE;
            });
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
