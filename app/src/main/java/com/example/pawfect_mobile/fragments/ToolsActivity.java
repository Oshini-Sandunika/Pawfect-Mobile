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

import com.example.pawfect_mobile.AdoptionCostActivity;
import com.example.pawfect_mobile.HumanYearConverterActivity;
import com.example.pawfect_mobile.MonthlyCareBudgetActivity;
import com.example.pawfect_mobile.R;

public class ToolsActivity extends Fragment {

    private Button btnAdoptionCost, btnMonthlyBudget, btnHumanYear;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tools, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnAdoptionCost = view.findViewById(R.id.btnAdoptionCost);
        btnMonthlyBudget = view.findViewById(R.id.btnMonthlyBudget);
        btnHumanYear = view.findViewById(R.id.btnHumanYear);

        // Remove Android default purple button tint
        btnAdoptionCost.setBackgroundTintList(null);
        btnMonthlyBudget.setBackgroundTintList(null);
        btnHumanYear.setBackgroundTintList(null);

        btnAdoptionCost.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdoptionCostActivity.class);
            startActivity(intent);
        });

        btnMonthlyBudget.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MonthlyCareBudgetActivity.class);
            startActivity(intent);
        });

        btnHumanYear.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HumanYearConverterActivity.class);
            startActivity(intent);
        });
    }
}