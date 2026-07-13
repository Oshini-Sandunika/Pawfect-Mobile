package com.example.pawfect_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pawfect_mobile.R;

import java.text.NumberFormat;
import java.util.Locale;

public class MonthlyCareBudgetFragment extends Fragment {

    private final String[] petCategories = {
            "Dog", "Cat", "Rabbit", "Bird", "Fish"
    };
    private final String[] dogBreeds = {
            "Labrador Retriever",
            "Golden Retriever",
            "German Shepherd",
            "Poodle",
            "Shih Tzu",
            "Beagle",
            "Mixed Breed - Small",
            "Mixed Breed - Medium",
            "Mixed Breed - Large"
    };
    private final String[] catBreeds = {
            "Domestic Short Hair",
            "Persian",
            "Siamese",
            "Maine Coon",
            "Ragdoll",
            "Bengal",
            "Mixed Breed Cat"
    };
    private final String[] rabbitTypes = {
            "Netherland Dwarf",
            "Holland Lop",
            "Lionhead",
            "Rex Rabbit",
            "Angora Rabbit",
            "Mixed Rabbit"
    };
    private final String[] birdTypes = {
            "Budgie",
            "Cockatiel",
            "Lovebird",
            "Canary",
            "Parrot",
            "Finch"
    };
    private final String[] fishTypes = {
            "Goldfish",
            "Betta Fish",
            "Guppy",
            "Molly",
            "Cichlid",
            "Tropical Community Fish"
    };
    private final String[] petSizes = {
            "Small", "Medium", "Large"
    };
    private final String[] ageGroups = {
            "Baby / Puppy / Kitten",
            "Young",
            "Adult",
            "Senior"
    };
    private final String[] foodQualities = {
            "Basic",
            "Standard",
            "Premium",
            "Special Diet"
    };
    private final String[] careLevels = {
            "Budget Care",
            "Normal Care",
            "Comfort Care",
            "Premium Care"
    };
    private final String[] vetPlans = {
            "Basic Vet Saving",
            "Standard Vet Saving",
            "Preventive Care Plan",
            "Senior / Medical Plan"
    };
    private Spinner spinnerPetCategory, spinnerBreedType, spinnerPetSize;
    private Spinner spinnerAgeGroup, spinnerFoodQuality, spinnerCareLevel, spinnerVetPlan;
    private TextView txtBreedLabel, txtSelectedPet, txtFoodCost, txtRoutineCost;
    private TextView txtVetCost, txtAddonCost, txtMonthlyTotal, txtYearlyTotal;
    private CheckBox checkGrooming, checkInsurance, checkToys, checkTraining;
    private CheckBox checkMedicine, checkCleaning, checkWalking, checkEmergency;
    private Button btnCalculate, btnReset;
    private NumberFormat numberFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_monthly_budget, container, false);

        numberFormat = NumberFormat.getNumberInstance(Locale.US);

        initializeViews(view);
        clearButtonTints();
        setupSpinners();
        setupListeners();

        updateBreedSpinner("Dog");
        updateOptionAvailability("Dog");
        calculateMonthlyCost();

        return view;
    }

    private void initializeViews(View view) {
        spinnerPetCategory = view.findViewById(R.id.spinnerPetCategory);
        spinnerBreedType = view.findViewById(R.id.spinnerBreedType);
        spinnerPetSize = view.findViewById(R.id.spinnerPetSize);
        spinnerAgeGroup = view.findViewById(R.id.spinnerAgeGroup);
        spinnerFoodQuality = view.findViewById(R.id.spinnerFoodQuality);
        spinnerCareLevel = view.findViewById(R.id.spinnerCareLevel);
        spinnerVetPlan = view.findViewById(R.id.spinnerVetPlan);

        txtBreedLabel = view.findViewById(R.id.txtBreedLabel);
        txtSelectedPet = view.findViewById(R.id.txtSelectedPet);
        txtFoodCost = view.findViewById(R.id.txtFoodCost);
        txtRoutineCost = view.findViewById(R.id.txtRoutineCost);
        txtVetCost = view.findViewById(R.id.txtVetCost);
        txtAddonCost = view.findViewById(R.id.txtAddonCost);
        txtMonthlyTotal = view.findViewById(R.id.txtMonthlyTotal);
        txtYearlyTotal = view.findViewById(R.id.txtYearlyTotal);

        checkGrooming = view.findViewById(R.id.checkGrooming);
        checkInsurance = view.findViewById(R.id.checkInsurance);
        checkToys = view.findViewById(R.id.checkToys);
        checkTraining = view.findViewById(R.id.checkTraining);
        checkMedicine = view.findViewById(R.id.checkMedicine);
        checkCleaning = view.findViewById(R.id.checkCleaning);
        checkWalking = view.findViewById(R.id.checkWalking);
        checkEmergency = view.findViewById(R.id.checkEmergency);

        btnCalculate = view.findViewById(R.id.btnCalculate);
        btnReset = view.findViewById(R.id.btnReset);
    }

    private void clearButtonTints() {
        btnCalculate.setBackgroundTintList(null);
        btnReset.setBackgroundTintList(null);
    }

    private void setupSpinners() {
        setSpinnerAdapter(spinnerPetCategory, petCategories);
        setSpinnerAdapter(spinnerPetSize, petSizes);
        setSpinnerAdapter(spinnerAgeGroup, ageGroups);
        setSpinnerAdapter(spinnerFoodQuality, foodQualities);
        setSpinnerAdapter(spinnerCareLevel, careLevels);
        setSpinnerAdapter(spinnerVetPlan, vetPlans);
    }

    private void setSpinnerAdapter(Spinner spinner, String[] data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.custom_spinner_item,
                data
        );

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupListeners() {
        spinnerPetCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = petCategories[position];
                updateBreedSpinner(selectedCategory);
                updateOptionAvailability(selectedCategory);
                calculateMonthlyCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        AdapterView.OnItemSelectedListener commonSpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateMonthlyCost();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerBreedType.setOnItemSelectedListener(commonSpinnerListener);
        spinnerPetSize.setOnItemSelectedListener(commonSpinnerListener);
        spinnerAgeGroup.setOnItemSelectedListener(commonSpinnerListener);
        spinnerFoodQuality.setOnItemSelectedListener(commonSpinnerListener);
        spinnerCareLevel.setOnItemSelectedListener(commonSpinnerListener);
        spinnerVetPlan.setOnItemSelectedListener(commonSpinnerListener);

        CompoundButton.OnCheckedChangeListener checkListener = (buttonView, isChecked) -> calculateMonthlyCost();

        checkGrooming.setOnCheckedChangeListener(checkListener);
        checkInsurance.setOnCheckedChangeListener(checkListener);
        checkToys.setOnCheckedChangeListener(checkListener);
        checkTraining.setOnCheckedChangeListener(checkListener);
        checkMedicine.setOnCheckedChangeListener(checkListener);
        checkCleaning.setOnCheckedChangeListener(checkListener);
        checkWalking.setOnCheckedChangeListener(checkListener);
        checkEmergency.setOnCheckedChangeListener(checkListener);

        btnCalculate.setOnClickListener(view -> calculateMonthlyCost());

        btnReset.setOnClickListener(view -> resetCalculator());
    }

    private void updateBreedSpinner(String category) {
        if (category.equals("Dog")) {
            txtBreedLabel.setText("Dog Breed");
            setSpinnerAdapter(spinnerBreedType, dogBreeds);
        } else if (category.equals("Cat")) {
            txtBreedLabel.setText("Cat Breed");
            setSpinnerAdapter(spinnerBreedType, catBreeds);
        } else if (category.equals("Rabbit")) {
            txtBreedLabel.setText("Rabbit Type");
            setSpinnerAdapter(spinnerBreedType, rabbitTypes);
        } else if (category.equals("Bird")) {
            txtBreedLabel.setText("Bird Type");
            setSpinnerAdapter(spinnerBreedType, birdTypes);
        } else if (category.equals("Fish")) {
            txtBreedLabel.setText("Fish Type");
            setSpinnerAdapter(spinnerBreedType, fishTypes);
        }
    }

    private void updateOptionAvailability(String category) {
        boolean isDog = category.equals("Dog");
        boolean isFish = category.equals("Fish");
        boolean isBird = category.equals("Bird");

        checkTraining.setEnabled(isDog);
        checkWalking.setEnabled(isDog);
        checkGrooming.setEnabled(!isFish);

        if (!isDog) {
            checkTraining.setChecked(false);
            checkWalking.setChecked(false);
        }

        if (isFish) {
            checkGrooming.setChecked(false);
        }

        if (category.equals("Dog")) {
            checkCleaning.setText("Waste bags and cleaning supplies");
            checkWalking.setText("Dog walking / daycare");
        } else if (category.equals("Cat")) {
            checkCleaning.setText("Litter and cleaning supplies");
            checkWalking.setText("Dog walking / daycare");
        } else if (category.equals("Rabbit")) {
            checkCleaning.setText("Bedding, hay, and cleaning supplies");
            checkWalking.setText("Dog walking / daycare");
        } else if (category.equals("Bird")) {
            checkCleaning.setText("Cage lining and cleaning supplies");
            checkWalking.setText("Dog walking / daycare");
        } else if (category.equals("Fish")) {
            checkCleaning.setText("Tank maintenance supplies");
            checkWalking.setText("Dog walking / daycare");
        }

        if (isBird || isFish) {
            checkInsurance.setText("Care protection saving");
        } else {
            checkInsurance.setText("Pet insurance");
        }
    }

    private void calculateMonthlyCost() {
        String category = getSelectedText(spinnerPetCategory);
        String breed = getSelectedText(spinnerBreedType);
        String size = getSelectedText(spinnerPetSize);
        String age = getSelectedText(spinnerAgeGroup);
        String foodQuality = getSelectedText(spinnerFoodQuality);
        String careLevel = getSelectedText(spinnerCareLevel);
        String vetPlan = getSelectedText(spinnerVetPlan);

        int foodCost = getFoodCost(category, breed, size, foodQuality);
        int routineCost = getRoutineCareCost(category, size, careLevel);
        int vetCost = getVetSavingCost(category, age, vetPlan);
        int addonCost = getAddonCost(category, breed, size);

        int monthlyTotal = foodCost + routineCost + vetCost + addonCost;
        int yearlyTotal = monthlyTotal * 12;

        txtSelectedPet.setText("Selected Pet: " + category + " - " + breed + " | " + size + " | " + age);
        txtFoodCost.setText("Food cost: " + formatLKR(foodCost));
        txtRoutineCost.setText("Routine care: " + formatLKR(routineCost));
        txtVetCost.setText("Vet saving: " + formatLKR(vetCost));
        txtAddonCost.setText("Optional add-ons: " + formatLKR(addonCost));
        txtMonthlyTotal.setText("Estimated Monthly Cost: " + formatLKR(monthlyTotal));
        txtYearlyTotal.setText("Estimated Yearly Cost: " + formatLKR(yearlyTotal));
    }

    private String getSelectedText(Spinner spinner) {
        if (spinner.getSelectedItem() == null) {
            return "";
        }
        return spinner.getSelectedItem().toString();
    }

    private String formatLKR(int amount) {
        return "LKR " + numberFormat.format(amount);
    }

    private int getFoodCost(String category, String breed, String size, String foodQuality) {
        int base = 0;

        if (category.equals("Dog")) {
            if (size.equals("Small")) {
                base = 8000;
            } else if (size.equals("Medium")) {
                base = 13000;
            } else {
                base = 20000;
            }

            if (breed.equals("Golden Retriever") || breed.equals("German Shepherd") || breed.equals("Mixed Breed - Large")) {
                base += 3000;
            } else if (breed.equals("Poodle") || breed.equals("Shih Tzu")) {
                base += 1500;
            }

        } else if (category.equals("Cat")) {
            if (size.equals("Small")) {
                base = 7000;
            } else if (size.equals("Medium")) {
                base = 9500;
            } else {
                base = 12000;
            }

            if (breed.equals("Persian") || breed.equals("Maine Coon") || breed.equals("Bengal")) {
                base += 2000;
            }

        } else if (category.equals("Rabbit")) {
            base = 6000;

            if (breed.equals("Angora Rabbit") || breed.equals("Lionhead")) {
                base += 1500;
            }

        } else if (category.equals("Bird")) {
            if (breed.equals("Parrot")) {
                base = 9000;
            } else if (breed.equals("Cockatiel") || breed.equals("Lovebird")) {
                base = 5500;
            } else {
                base = 3500;
            }

        } else if (category.equals("Fish")) {
            if (breed.equals("Cichlid") || breed.equals("Tropical Community Fish")) {
                base = 5000;
            } else {
                base = 2500;
            }
        }

        double multiplier = 1.0;

        if (foodQuality.equals("Basic")) {
            multiplier = 0.85;
        } else if (foodQuality.equals("Standard")) {
            multiplier = 1.0;
        } else if (foodQuality.equals("Premium")) {
            multiplier = 1.35;
        } else if (foodQuality.equals("Special Diet")) {
            multiplier = 1.6;
        }

        return roundToNearestHundred((int) (base * multiplier));
    }

    private int getRoutineCareCost(String category, String size, String careLevel) {
        int base = 0;

        if (category.equals("Dog")) {
            if (size.equals("Small")) {
                base = 2500;
            } else if (size.equals("Medium")) {
                base = 3500;
            } else {
                base = 5000;
            }
        } else if (category.equals("Cat")) {
            base = 2500;
        } else if (category.equals("Rabbit")) {
            base = 2000;
        } else if (category.equals("Bird")) {
            base = 1500;
        } else if (category.equals("Fish")) {
            base = 3000;
        }

        double multiplier = 1.0;

        if (careLevel.equals("Budget Care")) {
            multiplier = 0.75;
        } else if (careLevel.equals("Normal Care")) {
            multiplier = 1.0;
        } else if (careLevel.equals("Comfort Care")) {
            multiplier = 1.3;
        } else if (careLevel.equals("Premium Care")) {
            multiplier = 1.6;
        }

        return roundToNearestHundred((int) (base * multiplier));
    }

    private int getVetSavingCost(String category, String age, String vetPlan) {
        int base = 0;

        if (category.equals("Dog")) {
            base = 2500;
        } else if (category.equals("Cat")) {
            base = 2200;
        } else if (category.equals("Rabbit")) {
            base = 1800;
        } else if (category.equals("Bird")) {
            base = 1500;
        } else if (category.equals("Fish")) {
            base = 1000;
        }

        if (vetPlan.equals("Standard Vet Saving")) {
            base += 1500;
        } else if (vetPlan.equals("Preventive Care Plan")) {
            base += 3000;
        } else if (vetPlan.equals("Senior / Medical Plan")) {
            base += 5000;
        }

        if (age.equals("Baby / Puppy / Kitten")) {
            base += 1200;
        } else if (age.equals("Senior")) {
            base += 2500;
        }

        return roundToNearestHundred(base);
    }

    private int getAddonCost(String category, String breed, String size) {
        int total = 0;

        if (checkGrooming.isChecked()) {
            total += getGroomingCost(category, breed, size);
        }

        if (checkInsurance.isChecked()) {
            total += getInsuranceCost(category);
        }

        if (checkToys.isChecked()) {
            total += getToysCost(category, size);
        }

        if (checkTraining.isChecked()) {
            total += getTrainingCost(category);
        }

        if (checkMedicine.isChecked()) {
            total += getMedicineCost(category);
        }

        if (checkCleaning.isChecked()) {
            total += getCleaningCost(category, size);
        }

        if (checkWalking.isChecked()) {
            total += getWalkingCost(category, size);
        }

        if (checkEmergency.isChecked()) {
            total += getEmergencySavingCost(category, size);
        }

        return total;
    }

    private int getGroomingCost(String category, String breed, String size) {
        if (category.equals("Dog")) {
            int base;

            if (size.equals("Small")) {
                base = 4000;
            } else if (size.equals("Medium")) {
                base = 6000;
            } else {
                base = 8500;
            }

            if (breed.equals("Poodle") || breed.equals("Shih Tzu") || breed.equals("Golden Retriever")) {
                base += 2500;
            }

            return base;
        }

        if (category.equals("Cat")) {
            if (breed.equals("Persian") || breed.equals("Maine Coon") || breed.equals("Ragdoll")) {
                return 7000;
            }
            return 3500;
        }

        if (category.equals("Rabbit")) {
            if (breed.equals("Angora Rabbit")) {
                return 3500;
            }
            return 2000;
        }

        if (category.equals("Bird")) {
            return 1000;
        }

        return 0;
    }

    private int getInsuranceCost(String category) {
        if (category.equals("Dog")) {
            return 3500;
        } else if (category.equals("Cat")) {
            return 3000;
        } else if (category.equals("Rabbit")) {
            return 2000;
        } else if (category.equals("Bird")) {
            return 1500;
        } else if (category.equals("Fish")) {
            return 1000;
        }

        return 0;
    }

    private int getToysCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Small")) {
                return 1800;
            } else if (size.equals("Medium")) {
                return 2500;
            } else {
                return 3500;
            }
        }

        if (category.equals("Cat")) {
            return 1800;
        }

        if (category.equals("Rabbit")) {
            return 1200;
        }

        if (category.equals("Bird")) {
            return 1000;
        }

        if (category.equals("Fish")) {
            return 800;
        }

        return 0;
    }

    private int getTrainingCost(String category) {
        if (category.equals("Dog")) {
            return 6000;
        }

        return 0;
    }

    private int getMedicineCost(String category) {
        if (category.equals("Dog")) {
            return 2500;
        } else if (category.equals("Cat")) {
            return 2200;
        } else if (category.equals("Rabbit")) {
            return 1800;
        } else if (category.equals("Bird")) {
            return 1500;
        } else if (category.equals("Fish")) {
            return 1000;
        }

        return 0;
    }

    private int getCleaningCost(String category, String size) {
        if (category.equals("Dog")) {
            return 1200;
        }

        if (category.equals("Cat")) {
            return 3500;
        }

        if (category.equals("Rabbit")) {
            return 3000;
        }

        if (category.equals("Bird")) {
            return 1800;
        }

        if (category.equals("Fish")) {
            if (size.equals("Large")) {
                return 3500;
            }
            return 2500;
        }

        return 0;
    }

    private int getWalkingCost(String category, String size) {
        if (!category.equals("Dog")) {
            return 0;
        }

        if (size.equals("Small")) {
            return 5000;
        } else if (size.equals("Medium")) {
            return 7000;
        } else {
            return 9000;
        }
    }

    private int getEmergencySavingCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Large")) {
                return 5000;
            }
            return 3500;
        }

        if (category.equals("Cat")) {
            return 3000;
        }

        if (category.equals("Rabbit")) {
            return 2200;
        }

        if (category.equals("Bird")) {
            return 1800;
        }

        if (category.equals("Fish")) {
            return 1500;
        }

        return 0;
    }

    private int roundToNearestHundred(int value) {
        return Math.round(value / 100.0f) * 100;
    }

    private void resetCalculator() {
        spinnerPetCategory.setSelection(0);
        spinnerPetSize.setSelection(0);
        spinnerAgeGroup.setSelection(0);
        spinnerFoodQuality.setSelection(0);
        spinnerCareLevel.setSelection(0);
        spinnerVetPlan.setSelection(0);

        checkGrooming.setChecked(false);
        checkInsurance.setChecked(false);
        checkToys.setChecked(false);
        checkTraining.setChecked(false);
        checkMedicine.setChecked(false);
        checkCleaning.setChecked(false);
        checkWalking.setChecked(false);
        checkEmergency.setChecked(false);

        updateBreedSpinner("Dog");
        updateOptionAvailability("Dog");
        calculateMonthlyCost();
    }
}