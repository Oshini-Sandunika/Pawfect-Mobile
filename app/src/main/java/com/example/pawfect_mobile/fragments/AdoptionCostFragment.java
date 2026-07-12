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

public class AdoptionCostFragment extends Fragment {

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
            "Young Adult",
            "Adult",
            "Senior"
    };
    private final String[] adoptionSources = {
            "Shelter / Rescue",
            "Breeder",
            "Street Rescue",
            "Gift / Already Owned"
    };
    private Spinner spinnerPetCategory, spinnerBreedType, spinnerPetSize, spinnerAgeGroup, spinnerAdoptionSource;
    private TextView txtBreedLabel, txtSelectedPet, txtAdoptionFee, txtCareCost, txtAdjustment, txtTotalCost;
    private CheckBox checkVaccination, checkAccessories, checkMicrochip, checkVet,
            checkGrooming, checkSpayNeuter, checkTraining, checkInsurance;
    private Button btnCalculate, btnReset;
    private NumberFormat numberFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_adoption_cost, container, false);

        numberFormat = NumberFormat.getNumberInstance(Locale.US);

        initializeViews(view);
        clearButtonTints();
        setupSpinners();
        setupListeners();

        updateBreedSpinner("Dog");
        calculateEstimate();

        return view;
    }

    private void initializeViews(View view) {
        spinnerPetCategory = view.findViewById(R.id.spinnerPetCategory);
        spinnerBreedType = view.findViewById(R.id.spinnerBreedType);
        spinnerPetSize = view.findViewById(R.id.spinnerPetSize);
        spinnerAgeGroup = view.findViewById(R.id.spinnerAgeGroup);
        spinnerAdoptionSource = view.findViewById(R.id.spinnerAdoptionSource);

        txtBreedLabel = view.findViewById(R.id.txtBreedLabel);
        txtSelectedPet = view.findViewById(R.id.txtSelectedPet);
        txtAdoptionFee = view.findViewById(R.id.txtAdoptionFee);
        txtCareCost = view.findViewById(R.id.txtCareCost);
        txtAdjustment = view.findViewById(R.id.txtAdjustment);
        txtTotalCost = view.findViewById(R.id.txtTotalCost);

        checkVaccination = view.findViewById(R.id.checkVaccination);
        checkAccessories = view.findViewById(R.id.checkAccessories);
        checkMicrochip = view.findViewById(R.id.checkMicrochip);
        checkVet = view.findViewById(R.id.checkVet);
        checkGrooming = view.findViewById(R.id.checkGrooming);
        checkSpayNeuter = view.findViewById(R.id.checkSpayNeuter);
        checkTraining = view.findViewById(R.id.checkTraining);
        checkInsurance = view.findViewById(R.id.checkInsurance);

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
        setSpinnerAdapter(spinnerAdoptionSource, adoptionSources);
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
                updateCareOptionAvailability(selectedCategory);
                calculateEstimate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        AdapterView.OnItemSelectedListener commonSpinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateEstimate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerBreedType.setOnItemSelectedListener(commonSpinnerListener);
        spinnerPetSize.setOnItemSelectedListener(commonSpinnerListener);
        spinnerAgeGroup.setOnItemSelectedListener(commonSpinnerListener);
        spinnerAdoptionSource.setOnItemSelectedListener(commonSpinnerListener);

        CompoundButton.OnCheckedChangeListener checkListener = (buttonView, isChecked) -> calculateEstimate();

        checkVaccination.setOnCheckedChangeListener(checkListener);
        checkAccessories.setOnCheckedChangeListener(checkListener);
        checkMicrochip.setOnCheckedChangeListener(checkListener);
        checkVet.setOnCheckedChangeListener(checkListener);
        checkGrooming.setOnCheckedChangeListener(checkListener);
        checkSpayNeuter.setOnCheckedChangeListener(checkListener);
        checkTraining.setOnCheckedChangeListener(checkListener);
        checkInsurance.setOnCheckedChangeListener(checkListener);

        btnCalculate.setOnClickListener(view -> calculateEstimate());

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

    private void updateCareOptionAvailability(String category) {
        boolean isFish = category.equals("Fish");
        boolean isBird = category.equals("Bird");
        boolean isDog = category.equals("Dog");

        checkMicrochip.setEnabled(!isFish && !isBird);
        checkSpayNeuter.setEnabled(!isFish && !isBird);
        checkGrooming.setEnabled(!isFish);
        checkTraining.setEnabled(isDog);

        if (isFish || isBird) {
            checkMicrochip.setChecked(false);
            checkSpayNeuter.setChecked(false);
        }

        if (isFish) {
            checkGrooming.setChecked(false);
        }

        if (!isDog) {
            checkTraining.setChecked(false);
        }
    }

    private void calculateEstimate() {
        String category = getSelectedText(spinnerPetCategory);
        String breed = getSelectedText(spinnerBreedType);
        String size = getSelectedText(spinnerPetSize);
        String age = getSelectedText(spinnerAgeGroup);
        String source = getSelectedText(spinnerAdoptionSource);

        int adoptionFee = getAdoptionFee(category, source);
        int careCost = 0;

        if (checkVaccination.isChecked()) {
            careCost += getVaccinationCost(category, size);
        }

        if (checkAccessories.isChecked()) {
            careCost += getAccessoriesCost(category, size);
        }

        if (checkMicrochip.isChecked()) {
            careCost += getMicrochipCost(category);
        }

        if (checkVet.isChecked()) {
            careCost += getVetCheckCost(category, size);
        }

        if (checkGrooming.isChecked()) {
            careCost += getGroomingCost(category, breed, size);
        }

        if (checkSpayNeuter.isChecked()) {
            careCost += getSpayNeuterCost(category, size);
        }

        if (checkTraining.isChecked()) {
            careCost += getTrainingCost(category);
        }

        if (checkInsurance.isChecked()) {
            careCost += getInsuranceCost(category);
        }

        int subtotal = adoptionFee + careCost;

        int breedAdjustment = getBreedAdjustment(category, breed, subtotal);
        int ageAdjustment = getAgeAdjustment(age, careCost);

        int totalAdjustment = breedAdjustment + ageAdjustment;
        int total = subtotal + totalAdjustment;

        txtSelectedPet.setText("Selected Pet: " + category + " - " + breed + " | " + size + " | " + age);
        txtAdoptionFee.setText("Adoption fee: " + formatLKR(adoptionFee));
        txtCareCost.setText("Initial care options: " + formatLKR(careCost));
        txtAdjustment.setText("Breed / age adjustment: " + formatLKR(totalAdjustment));
        txtTotalCost.setText("Total Estimated Cost: " + formatLKR(total));
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

    private int getAdoptionFee(String category, String source) {
        if (source.equals("Street Rescue") || source.equals("Gift / Already Owned")) {
            return 0;
        }

        if (source.equals("Breeder")) {
            if (category.equals("Dog")) return 50000;
            if (category.equals("Cat")) return 40000;
            if (category.equals("Rabbit")) return 18000;
            if (category.equals("Bird")) return 15000;
            if (category.equals("Fish")) return 5000;
        }

        if (category.equals("Dog")) return 15000;
        if (category.equals("Cat")) return 10000;
        if (category.equals("Rabbit")) return 6000;
        if (category.equals("Bird")) return 4000;
        if (category.equals("Fish")) return 2000;

        return 0;
    }

    private int getVaccinationCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Small")) return 12000;
            if (size.equals("Medium")) return 15000;
            return 18000;
        }

        if (category.equals("Cat")) return 12000;
        if (category.equals("Rabbit")) return 6000;
        if (category.equals("Bird")) return 3000;
        if (category.equals("Fish")) return 0;

        return 0;
    }

    private int getAccessoriesCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Small")) return 18000;
            if (size.equals("Medium")) return 25000;
            return 35000;
        }

        if (category.equals("Cat")) {
            if (size.equals("Small")) return 16000;
            if (size.equals("Medium")) return 20000;
            return 24000;
        }

        if (category.equals("Rabbit")) return 12000;
        if (category.equals("Bird")) return 15000;
        if (category.equals("Fish")) return 25000;

        return 0;
    }

    private int getMicrochipCost(String category) {
        if (category.equals("Dog")) return 6500;
        if (category.equals("Cat")) return 6500;
        if (category.equals("Rabbit")) return 5000;
        return 0;
    }

    private int getVetCheckCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Small")) return 5000;
            if (size.equals("Medium")) return 7000;
            return 9000;
        }

        if (category.equals("Cat")) return 5000;
        if (category.equals("Rabbit")) return 4000;
        if (category.equals("Bird")) return 3500;
        if (category.equals("Fish")) return 1500;

        return 0;
    }

    private int getGroomingCost(String category, String breed, String size) {
        if (category.equals("Dog")) {
            int base;

            if (size.equals("Small")) {
                base = 5000;
            } else if (size.equals("Medium")) {
                base = 7000;
            } else {
                base = 10000;
            }

            if (breed.equals("Poodle") || breed.equals("Shih Tzu") || breed.equals("Golden Retriever")) {
                base += 2500;
            }

            return base;
        }

        if (category.equals("Cat")) {
            if (breed.equals("Persian") || breed.equals("Maine Coon") || breed.equals("Ragdoll")) {
                return 9000;
            }
            return 5000;
        }

        if (category.equals("Rabbit")) {
            if (breed.equals("Angora Rabbit")) {
                return 5000;
            }
            return 3000;
        }

        if (category.equals("Bird")) return 1000;

        return 0;
    }

    private int getSpayNeuterCost(String category, String size) {
        if (category.equals("Dog")) {
            if (size.equals("Small")) return 18000;
            if (size.equals("Medium")) return 25000;
            return 35000;
        }

        if (category.equals("Cat")) return 15000;
        if (category.equals("Rabbit")) return 12000;

        return 0;
    }

    private int getTrainingCost(String category) {
        if (category.equals("Dog")) return 15000;
        return 0;
    }

    private int getInsuranceCost(String category) {
        if (category.equals("Dog")) return 5000;
        if (category.equals("Cat")) return 4000;
        if (category.equals("Rabbit")) return 2500;
        if (category.equals("Bird")) return 2000;
        return 0;
    }

    private int getBreedAdjustment(String category, String breed, int subtotal) {
        double rate = 0.0;

        if (category.equals("Dog")) {
            if (breed.equals("German Shepherd") || breed.equals("Golden Retriever")) {
                rate = 0.10;
            } else if (breed.equals("Poodle") || breed.equals("Shih Tzu")) {
                rate = 0.08;
            } else if (breed.equals("Mixed Breed - Large")) {
                rate = 0.07;
            }
        }

        if (category.equals("Cat")) {
            if (breed.equals("Persian") || breed.equals("Maine Coon") || breed.equals("Bengal")) {
                rate = 0.10;
            } else if (breed.equals("Ragdoll")) {
                rate = 0.08;
            }
        }

        if (category.equals("Rabbit")) {
            if (breed.equals("Angora Rabbit") || breed.equals("Lionhead")) {
                rate = 0.07;
            }
        }

        if (category.equals("Bird")) {
            if (breed.equals("Parrot")) {
                rate = 0.12;
            } else if (breed.equals("Cockatiel") || breed.equals("Lovebird")) {
                rate = 0.06;
            }
        }

        if (category.equals("Fish")) {
            if (breed.equals("Cichlid") || breed.equals("Tropical Community Fish")) {
                rate = 0.08;
            }
        }

        return roundToNearestHundred((int) (subtotal * rate));
    }

    private int getAgeAdjustment(String age, int careCost) {
        double rate = 0.0;

        if (age.equals("Baby / Puppy / Kitten")) {
            rate = 0.10;
        } else if (age.equals("Senior")) {
            rate = 0.15;
        }

        return roundToNearestHundred((int) (careCost * rate));
    }

    private int roundToNearestHundred(int value) {
        return Math.round(value / 100.0f) * 100;
    }

    private void resetCalculator() {
        spinnerPetCategory.setSelection(0);
        spinnerPetSize.setSelection(0);
        spinnerAgeGroup.setSelection(0);
        spinnerAdoptionSource.setSelection(0);

        checkVaccination.setChecked(false);
        checkAccessories.setChecked(false);
        checkMicrochip.setChecked(false);
        checkVet.setChecked(false);
        checkGrooming.setChecked(false);
        checkSpayNeuter.setChecked(false);
        checkTraining.setChecked(false);
        checkInsurance.setChecked(false);

        updateBreedSpinner("Dog");
        calculateEstimate();
    }
}