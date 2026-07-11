package com.example.pawfect_mobile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pawfect_mobile.FindPerfectPetActivity;
import com.example.pawfect_mobile.PetProfileActivity;
import com.example.pawfect_mobile.R;
import com.example.pawfect_mobile.ToolsActivity;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button btnTools = view.findViewById(R.id.btnTools);
        Button btnPetRecommendation = view.findViewById(R.id.btnPetRecommendation);
        Button btnAdoptPet = view.findViewById(R.id.btnAdoptPet);


        btnTools.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ToolsActivity.class);
            startActivity(intent);
        });

        btnAdoptPet.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetProfileActivity.class);
            startActivity(intent);
        });

        btnPetRecommendation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FindPerfectPetActivity.class);
            startActivity(intent);
        });
    }
}