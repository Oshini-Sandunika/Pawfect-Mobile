package com.example.pawfect_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pawfect_mobile.R;

import java.text.DecimalFormat;

public class HumanYearConverterFragment extends Fragment {

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
    private Spinner spinnerPetCategory, spinnerBreedType, spinnerPetSize;
    private EditText editPetAge;
    private TextView txtBreedLabel, txtSelectedPet, txtHumanAge;
    private TextView txtLifeStage, txtExplanation;
    private Button btnConvert, btnReset;
    private DecimalFormat decimalFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_human_year_converter, container, false);

        decimalFormat = new DecimalFormat("#.#");

        initializeViews(view);
        clearButtonTints();
        setupSpinners();
        setupListeners();

        updateBreedSpinner("Dog");
        updateDefaultResult();

        return view;
    }

    private void initializeViews(View view) {
        spinnerPetCategory = view.findViewById(R.id.spinnerPetCategory);
        spinnerBreedType = view.findViewById(R.id.spinnerBreedType);
        spinnerPetSize = view.findViewById(R.id.spinnerPetSize);

        editPetAge = view.findViewById(R.id.editPetAge);

        txtBreedLabel = view.findViewById(R.id.txtBreedLabel);
        txtSelectedPet = view.findViewById(R.id.txtSelectedPet);
        txtHumanAge = view.findViewById(R.id.txtHumanAge);
        txtLifeStage = view.findViewById(R.id.txtLifeStage);
        txtExplanation = view.findViewById(R.id.txtExplanation);

        btnConvert = view.findViewById(R.id.btnConvert);
        btnReset = view.findViewById(R.id.btnReset);
    }

    private void clearButtonTints() {
        btnConvert.setBackgroundTintList(null);
        btnReset.setBackgroundTintList(null);
    }

    private void setupSpinners() {
        setSpinnerAdapter(spinnerPetCategory, petCategories);
        setSpinnerAdapter(spinnerPetSize, petSizes);
    }

    private void setSpinnerAdapter(Spinner spinner, String[] data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                data
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupListeners() {
        spinnerPetCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = petCategories[position];
                updateBreedSpinner(selectedCategory);
                updateSizeAvailability(selectedCategory);
                updateDefaultResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConvert.setOnClickListener(view -> convertAge());

        btnReset.setOnClickListener(view -> resetConverter());
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

    private void updateSizeAvailability(String category) {
        boolean sizeNeeded = category.equals("Dog") || category.equals("Cat") || category.equals("Fish");
        spinnerPetSize.setEnabled(sizeNeeded);

        if (!sizeNeeded) {
            spinnerPetSize.setSelection(0);
        }
    }

    private void updateDefaultResult() {
        String category = getSelectedText(spinnerPetCategory);
        String breed = getSelectedText(spinnerBreedType);
        String size = getSelectedText(spinnerPetSize);

        txtSelectedPet.setText("Selected Pet: " + category + " - " + breed + " | " + size);
        txtHumanAge.setText("Human Age: -");
        txtLifeStage.setText("Life Stage: -");
        txtExplanation.setText("Enter your pet’s age and tap Convert. The result is an approximate project estimate.");
    }

    private void convertAge() {
        String ageInput = editPetAge.getText().toString().trim();

        if (ageInput.isEmpty()) {
            editPetAge.setError("Please enter pet age");
            editPetAge.requestFocus();
            return;
        }

        double petAge;

        try {
            petAge = Double.parseDouble(ageInput);
        } catch (NumberFormatException e) {
            editPetAge.setError("Enter a valid number");
            editPetAge.requestFocus();
            return;
        }

        if (petAge <= 0) {
            editPetAge.setError("Age must be greater than 0");
            editPetAge.requestFocus();
            return;
        }

        if (petAge > 100) {
            editPetAge.setError("Please enter a realistic pet age");
            editPetAge.requestFocus();
            return;
        }

        String category = getSelectedText(spinnerPetCategory);
        String breed = getSelectedText(spinnerBreedType);
        String size = getSelectedText(spinnerPetSize);

        double humanAge = calculateHumanAge(category, breed, size, petAge);
        String lifeStage = getLifeStage(category, petAge);
        String explanation = getExplanation(category, breed, size, petAge, humanAge);

        txtSelectedPet.setText("Selected Pet: " + category + " - " + breed + " | " + size);
        txtHumanAge.setText("Human Age: " + decimalFormat.format(humanAge) + " years");
        txtLifeStage.setText("Life Stage: " + lifeStage);
        txtExplanation.setText(explanation);

        Toast.makeText(requireContext(), "Age converted successfully", Toast.LENGTH_SHORT).show();
    }

    private double calculateHumanAge(String category, String breed, String size, double petAge) {
        if (category.equals("Dog")) {
            return calculateDogHumanAge(size, breed, petAge);
        } else if (category.equals("Cat")) {
            return calculateCatHumanAge(breed, petAge);
        } else if (category.equals("Rabbit")) {
            return calculateRabbitHumanAge(breed, petAge);
        } else if (category.equals("Bird")) {
            return calculateBirdHumanAge(breed, petAge);
        } else if (category.equals("Fish")) {
            return calculateFishHumanAge(breed, petAge);
        }

        return petAge * 7;
    }

    private double calculateDogHumanAge(String size, String breed, double age) {
        if (age <= 1) {
            return age * 15;
        }

        if (age <= 2) {
            return 15 + ((age - 1) * 9);
        }

        double extraYears = age - 2;
        double yearlyRate;

        if (size.equals("Small")) {
            yearlyRate = 4.0;
        } else if (size.equals("Medium")) {
            yearlyRate = 5.0;
        } else {
            yearlyRate = 6.0;
        }

        if (breed.equals("German Shepherd") || breed.equals("Golden Retriever") || breed.equals("Mixed Breed - Large")) {
            yearlyRate += 0.5;
        }

        if (breed.equals("Shih Tzu") || breed.equals("Poodle") || breed.equals("Mixed Breed - Small")) {
            yearlyRate -= 0.3;
        }

        return 24 + (extraYears * yearlyRate);
    }

    private double calculateCatHumanAge(String breed, double age) {
        if (age <= 1) {
            return age * 15;
        }

        if (age <= 2) {
            return 15 + ((age - 1) * 9);
        }

        double yearlyRate = 4.0;

        if (breed.equals("Maine Coon") || breed.equals("Persian") || breed.equals("Bengal")) {
            yearlyRate = 4.3;
        }

        return 24 + ((age - 2) * yearlyRate);
    }

    private double calculateRabbitHumanAge(String breed, double age) {
        if (age <= 1) {
            return age * 20;
        }

        if (age <= 5) {
            return 20 + ((age - 1) * 8);
        }

        double yearlyRate = 10;

        if (breed.equals("Netherland Dwarf") || breed.equals("Holland Lop")) {
            yearlyRate = 9;
        }

        if (breed.equals("Angora Rabbit")) {
            yearlyRate = 10.5;
        }

        return 52 + ((age - 5) * yearlyRate);
    }

    private double calculateBirdHumanAge(String breed, double age) {
        double multiplier;

        if (breed.equals("Parrot")) {
            multiplier = 2.5;
        } else if (breed.equals("Cockatiel") || breed.equals("Lovebird")) {
            multiplier = 4.0;
        } else if (breed.equals("Budgie") || breed.equals("Canary")) {
            multiplier = 5.0;
        } else {
            multiplier = 5.5;
        }

        return age * multiplier;
    }

    private double calculateFishHumanAge(String breed, double age) {
        double multiplier;

        if (breed.equals("Goldfish")) {
            multiplier = 4.0;
        } else if (breed.equals("Betta Fish")) {
            multiplier = 18.0;
        } else if (breed.equals("Cichlid")) {
            multiplier = 7.0;
        } else if (breed.equals("Tropical Community Fish")) {
            multiplier = 8.0;
        } else {
            multiplier = 10.0;
        }

        return age * multiplier;
    }

    private String getLifeStage(String category, double age) {
        if (category.equals("Dog") || category.equals("Cat")) {
            if (age < 1) return "Baby";
            if (age < 3) return "Young";
            if (age < 7) return "Adult";
            return "Senior";
        }

        if (category.equals("Rabbit")) {
            if (age < 0.5) return "Baby";
            if (age < 2) return "Young";
            if (age < 5) return "Adult";
            return "Senior";
        }

        if (category.equals("Bird")) {
            if (age < 1) return "Young";
            if (age < 6) return "Adult";
            return "Senior";
        }

        if (category.equals("Fish")) {
            if (age < 0.5) return "Young";
            if (age < 3) return "Adult";
            return "Senior";
        }

        return "Unknown";
    }

    private String getExplanation(String category, String breed, String size, double petAge, double humanAge) {
        return "Your " + category.toLowerCase() + " is approximately "
                + decimalFormat.format(humanAge)
                + " human years old. This estimate is based on pet category, breed/type, size, and age. "
                + "Actual aging can vary depending on genetics, care, diet, and health condition.";
    }

    private String getSelectedText(Spinner spinner) {
        if (spinner.getSelectedItem() == null) {
            return "";
        }

        return spinner.getSelectedItem().toString();
    }

    private void resetConverter() {
        spinnerPetCategory.setSelection(0);
        spinnerPetSize.setSelection(0);
        editPetAge.setText("");

        updateBreedSpinner("Dog");
        updateSizeAvailability("Dog");
        updateDefaultResult();
    }
}