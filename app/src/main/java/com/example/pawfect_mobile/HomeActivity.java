package com.example.pawfect_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnTools, btnPetRecommendation, btnAdoptPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTools = findViewById(R.id.btnTools);
        btnPetRecommendation = findViewById(R.id.btnPetRecommendation);
        btnAdoptPet = findViewById(R.id.btnAdoptPet);

        // Remove Android default purple tint
        btnTools.setBackgroundTintList(null);
        btnPetRecommendation.setBackgroundTintList(null);
        btnAdoptPet.setBackgroundTintList(null);

        btnTools.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ToolsActivity.class);
            startActivity(intent);
        });

        btnAdoptPet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PetProfileActivity.class);
            startActivity(intent);
        });

        btnPetRecommendation.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FindPerfectPetActivity.class);
            startActivity(intent);
        });
    }
}