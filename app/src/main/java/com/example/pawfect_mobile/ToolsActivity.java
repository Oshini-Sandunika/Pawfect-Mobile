package com.example.pawfect_mobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class ToolsActivity extends AppCompatActivity {

    Button btnAdoptionCost, btnMonthlyBudget, btnHumanYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        btnAdoptionCost = findViewById(R.id.btnAdoptionCost);
        btnMonthlyBudget = findViewById(R.id.btnMonthlyBudget);
        btnHumanYear = findViewById(R.id.btnHumanYear);

        btnAdoptionCost.setOnClickListener(v -> {
            Intent intent = new Intent(ToolsActivity.this, com.example.pawfect_mobile.AdoptionCostActivity.class);
            startActivity(intent);
        });

        btnMonthlyBudget.setOnClickListener(v -> {
            Intent intent = new Intent(ToolsActivity.this, MonthlyCareBudgetActivity.class);
            startActivity(intent);
        });

        btnHumanYear.setOnClickListener(v -> {
            Intent intent = new Intent(ToolsActivity.this, HumanYearConverterActivity.class);
            startActivity(intent);
        });
    }
}