package com.example.pawfect_mobile.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.pawfect_mobile.PetRecommendationClassifier;
import com.example.pawfect_mobile.R;

import java.text.DecimalFormat;

public class FindPerfectPetFragment extends Fragment {

    private final float[] answers = new float[10];
    private final int[] selectedIndexes = new int[10];
    private TextView btnBack, txtQuizTitle, txtQuizSubtitle, txtProgress, txtQuestion;
    private TextView txtPrediction, txtConfidence, txtModelStatus, txtReason;
    private TextView txtExactPetName, txtExactPetCategory, txtExactPetDesc;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private Button btnNext, btnPrevious, btnStartAgain;
    private ImageView imgExactPet;
    private ProgressBar progressBar;
    private CardView questionCard, resultCard;
    private PetRecommendationClassifier classifier;
    private DecimalFormat decimalFormat;
    private int currentQuestionIndex = 0;
    private int selectedOptionIndex = -1;
    private Question[] questions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_find_perfect_pet, container, false);

        classifier = new PetRecommendationClassifier(requireContext());
        decimalFormat = new DecimalFormat("#.#");

        initializeViews(view);
        initializeQuestions();
        setupListeners();
        clearButtonTints();
        resetStoredAnswers();
        showQuestion();

        return view;
    }

    private void initializeViews(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        txtQuizTitle = view.findViewById(R.id.txtQuizTitle);
        txtQuizSubtitle = view.findViewById(R.id.txtQuizSubtitle);
        txtProgress = view.findViewById(R.id.txtProgress);
        txtQuestion = view.findViewById(R.id.txtQuestion);

        btnOption1 = view.findViewById(R.id.btnOption1);
        btnOption2 = view.findViewById(R.id.btnOption2);
        btnOption3 = view.findViewById(R.id.btnOption3);
        btnOption4 = view.findViewById(R.id.btnOption4);

        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnStartAgain = view.findViewById(R.id.btnStartAgain);

        progressBar = view.findViewById(R.id.progressBar);

        questionCard = view.findViewById(R.id.questionCard);
        resultCard = view.findViewById(R.id.resultCard);

        txtPrediction = view.findViewById(R.id.txtPrediction);
        txtConfidence = view.findViewById(R.id.txtConfidence);
        txtModelStatus = view.findViewById(R.id.txtModelStatus);
        txtReason = view.findViewById(R.id.txtReason);

        imgExactPet = view.findViewById(R.id.imgExactPet);
        txtExactPetName = view.findViewById(R.id.txtExactPetName);
        txtExactPetCategory = view.findViewById(R.id.txtExactPetCategory);
        txtExactPetDesc = view.findViewById(R.id.txtExactPetDesc);
    }

    private void clearButtonTints() {
        Button[] buttons = {
                btnOption1,
                btnOption2,
                btnOption3,
                btnOption4,
                btnNext,
                btnPrevious,
                btnStartAgain
        };

        for (Button button : buttons) {
            if (button != null) {
                button.setBackgroundTintList(null);
            }
        }
    }

    private void initializeQuestions() {
        questions = new Question[]{
                new Question(
                        "How much living space do you have?",
                        new String[]{
                                "Apartment",
                                "Small house",
                                "Large house / garden"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "How active is your daily lifestyle?",
                        new String[]{
                                "Mostly relaxed / indoors",
                                "Lightly active",
                                "Moderately active",
                                "Very active"
                        },
                        new float[]{0f, 0.3f, 0.6f, 1f}
                ),
                new Question(
                        "How much time can you spend on pet care daily?",
                        new String[]{
                                "Less than 30 minutes",
                                "30 minutes to 1 hour",
                                "1 to 2 hours",
                                "More than 2 hours"
                        },
                        new float[]{0f, 0.35f, 0.7f, 1f}
                ),
                new Question(
                        "Do you or your family members have pet allergies?",
                        new String[]{
                                "No allergies",
                                "Mild allergies",
                                "Yes, allergies are a concern"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "Are there children at home?",
                        new String[]{
                                "No",
                                "Yes"
                        },
                        new float[]{0f, 1f}
                ),
                new Question(
                        "What is your monthly pet care budget?",
                        new String[]{
                                "Low budget",
                                "Medium budget",
                                "High budget"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "How much grooming effort are you comfortable with?",
                        new String[]{
                                "Low grooming",
                                "Medium grooming",
                                "High grooming"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "How much pet noise can you tolerate?",
                        new String[]{
                                "Low noise only",
                                "Medium noise is okay",
                                "High noise is okay"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "What is your pet ownership experience?",
                        new String[]{
                                "Beginner",
                                "Some experience",
                                "Experienced"
                        },
                        new float[]{0f, 0.5f, 1f}
                ),
                new Question(
                        "What pet size do you prefer?",
                        new String[]{
                                "Small",
                                "Medium",
                                "Large",
                                "No preference"
                        },
                        new float[]{0f, 0.5f, 1f, 0.5f}
                )
        };
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        btnOption1.setOnClickListener(v -> selectOption(0));
        btnOption2.setOnClickListener(v -> selectOption(1));
        btnOption3.setOnClickListener(v -> selectOption(2));
        btnOption4.setOnClickListener(v -> selectOption(3));

        btnPrevious.setOnClickListener(v -> goToPreviousQuestion());

        btnNext.setOnClickListener(v -> {
            if (selectedOptionIndex == -1) {
                Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            saveCurrentAnswer();

            if (currentQuestionIndex == questions.length - 1) {
                runPrediction();
            } else {
                currentQuestionIndex++;
                showQuestion();
            }
        });

        btnStartAgain.setOnClickListener(v -> resetQuiz());
    }

    private void showQuestion() {
        Question question = questions[currentQuestionIndex];

        txtQuizTitle.setText("Find Your Perfect Pet");
        txtQuizSubtitle.setText("Answer one question at a time");

        txtProgress.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.length);
        progressBar.setMax(questions.length);
        progressBar.setProgress(currentQuestionIndex + 1);

        txtQuestion.setText(question.questionText);

        Button[] optionButtons = {
                btnOption1,
                btnOption2,
                btnOption3,
                btnOption4
        };

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBackgroundTintList(null);

            if (i < question.options.length) {
                optionButtons[i].setVisibility(View.VISIBLE);
                optionButtons[i].setText(question.options[i]);
            } else {
                optionButtons[i].setVisibility(View.GONE);
            }
        }

        selectedOptionIndex = selectedIndexes[currentQuestionIndex];

        updateOptionButtonStyles();

        if (currentQuestionIndex == 0) {
            btnPrevious.setVisibility(View.INVISIBLE);
        } else {
            btnPrevious.setVisibility(View.VISIBLE);
        }

        if (currentQuestionIndex == questions.length - 1) {
            btnNext.setText("Show Match");
        } else {
            btnNext.setText("Next");
        }
    }

    private void selectOption(int optionIndex) {
        selectedOptionIndex = optionIndex;
        selectedIndexes[currentQuestionIndex] = optionIndex;
        updateOptionButtonStyles();
    }

    private void updateOptionButtonStyles() {
        Button[] optionButtons = {
                btnOption1,
                btnOption2,
                btnOption3,
                btnOption4
        };

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setBackgroundTintList(null);

            if (i == selectedOptionIndex) {
                optionButtons[i].setBackgroundResource(R.drawable.quiz_option_button_selected);
                optionButtons[i].setBackgroundTintList(null);
                optionButtons[i].setTextColor(Color.WHITE);
            } else {
                optionButtons[i].setBackgroundResource(R.drawable.quiz_option_button);
                optionButtons[i].setBackgroundTintList(null);
                optionButtons[i].setTextColor(Color.parseColor("#7B624C"));
            }
        }
    }

    private void saveCurrentAnswer() {
        Question question = questions[currentQuestionIndex];

        if (selectedOptionIndex >= 0 && selectedOptionIndex < question.values.length) {
            answers[currentQuestionIndex] = question.values[selectedOptionIndex];
        }
    }

    private void goToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            saveCurrentAnswer();
            currentQuestionIndex--;
            showQuestion();
        }
    }

    private void runPrediction() {
        saveCurrentAnswer();

        PetRecommendationClassifier.PredictionResult result = classifier.predict(answers);

        String predictedCategory = result.predictedCategory;
        String confidenceText = decimalFormat.format(result.confidence) + "%";

        ExactPet exactPet = recommendExactPet(predictedCategory);

        txtPrediction.setText("Recommended Category: " + predictedCategory);
        txtConfidence.setText("Category Confidence: " + confidenceText);

        if (result.usedTfliteModel) {
            txtModelStatus.setText("Model Status: TensorFlow Lite / LiteRT model used");
        } else {
            txtModelStatus.setText("Model Status: Rule-based fallback used");
        }

        txtReason.setText(getRecommendationReason(predictedCategory, exactPet.name));
        showExactPet(exactPet);

        questionCard.setVisibility(View.GONE);
        resultCard.setVisibility(View.VISIBLE);

        txtQuizTitle.setText("Your Perfect Match!");
        txtQuizSubtitle.setText("Based on your lifestyle answers");
    }

    private ExactPet recommendExactPet(String category) {
        float livingSpace = answers[0];
        float activity = answers[1];
        float time = answers[2];
        float allergies = answers[3];
        float children = answers[4];
        float budget = answers[5];
        float grooming = answers[6];
        float noise = answers[7];
        float experience = answers[8];
        float preferredSize = answers[9];

        if (category.equals("Dog")) {
            if (livingSpace == 0f || preferredSize == 0f) {
                return new ExactPet(
                        "Shih Tzu",
                        "Dog",
                        "Small companion dog suitable for apartment living and owners who prefer a smaller pet.",
                        "dog_shih_tzu"
                );
            }

            if (activity >= 0.7f && time >= 0.7f && budget >= 0.5f) {
                return new ExactPet(
                        "Labrador Retriever",
                        "Dog",
                        "Friendly, active, loyal, and suitable for families or owners with time for exercise.",
                        "dog_labrador"
                );
            }

            if (activity >= 0.4f || preferredSize == 0.5f) {
                return new ExactPet(
                        "Beagle",
                        "Dog",
                        "Playful, curious, medium-sized, and suitable for moderately active owners.",
                        "dog_beagle"
                );
            }

            return new ExactPet(
                    "Mixed Breed Dog",
                    "Dog",
                    "Adaptable, loving, and commonly available for adoption.",
                    "dog_mixed"
            );
        }

        if (category.equals("Cat")) {
            if (grooming >= 0.8f && budget >= 0.5f) {
                return new ExactPet(
                        "Persian",
                        "Cat",
                        "Calm indoor cat suitable for owners who can manage regular grooming.",
                        "cat_persian"
                );
            }

            if (noise >= 0.5f && time >= 0.5f) {
                return new ExactPet(
                        "Siamese",
                        "Cat",
                        "Social, intelligent, vocal, and suitable for owners who want interaction.",
                        "cat_siamese"
                );
            }

            if (grooming == 0f || experience == 0f) {
                return new ExactPet(
                        "Domestic Short Hair",
                        "Cat",
                        "Low-maintenance, adaptable, and suitable for many homes.",
                        "cat_domestic_short_hair"
                );
            }

            return new ExactPet(
                    "Mixed Breed Cat",
                    "Cat",
                    "Adaptable, independent, and commonly available for adoption.",
                    "cat_mixed"
            );
        }

        if (category.equals("Rabbit")) {
            if (livingSpace == 0f || preferredSize == 0f) {
                return new ExactPet(
                        "Netherland Dwarf",
                        "Rabbit",
                        "Tiny rabbit suitable for smaller spaces and calm indoor homes.",
                        "rabbit_netherland_dwarf"
                );
            }

            if (grooming >= 0.8f) {
                return new ExactPet(
                        "Lionhead Rabbit",
                        "Rabbit",
                        "Friendly rabbit with a fluffy coat that needs grooming.",
                        "rabbit_lionhead"
                );
            }

            if (children == 1f || experience == 0f) {
                return new ExactPet(
                        "Holland Lop",
                        "Rabbit",
                        "Small, gentle, friendly, and suitable for calm family homes.",
                        "rabbit_holland_lop"
                );
            }

            return new ExactPet(
                    "Rex Rabbit",
                    "Rabbit",
                    "Calm, soft-coated, and good as a companion rabbit.",
                    "rabbit_rex"
            );
        }

        if (category.equals("Bird")) {
            if (experience == 0f && noise == 0f) {
                return new ExactPet(
                        "Canary",
                        "Bird",
                        "Good for owners who enjoy birdsong and prefer a smaller bird.",
                        "bird_canary"
                );
            }

            if (experience == 0f) {
                return new ExactPet(
                        "Budgie",
                        "Bird",
                        "Small, social, colorful, and beginner-friendly.",
                        "bird_budgie"
                );
            }

            if (time >= 0.7f && noise >= 0.5f) {
                return new ExactPet(
                        "Cockatiel",
                        "Bird",
                        "Affectionate and interactive companion bird suitable for daily interaction.",
                        "bird_cockatiel"
                );
            }

            return new ExactPet(
                    "Lovebird",
                    "Bird",
                    "Colorful and social bird suitable for owners who can give attention.",
                    "bird_lovebird"
            );
        }

        if (category.equals("Fish")) {
            if (time == 0f || allergies >= 0.5f) {
                return new ExactPet(
                        "Betta Fish",
                        "Fish",
                        "Colorful and suitable for beginners who prefer a quiet, allergy-friendly pet.",
                        "fish_betta"
                );
            }

            if (budget >= 0.8f && preferredSize == 1f) {
                return new ExactPet(
                        "Goldfish",
                        "Fish",
                        "Popular pet fish, but needs proper tank space and filtration.",
                        "fish_goldfish"
                );
            }

            if (experience == 0f) {
                return new ExactPet(
                        "Guppy",
                        "Fish",
                        "Small, colorful, active, and easy to care for.",
                        "fish_guppy"
                );
            }

            return new ExactPet(
                    "Molly Fish",
                    "Fish",
                    "Peaceful community fish suitable for beginner aquariums.",
                    "fish_molly"
            );
        }

        return new ExactPet(
                "Pet",
                category,
                "No exact pet match found.",
                "pawfect_logo"
        );
    }

    private void showExactPet(ExactPet pet) {
        txtExactPetName.setText(pet.name);
        txtExactPetCategory.setText("Category: " + pet.category);
        txtExactPetDesc.setText(pet.description);

        int imageResId = getResources().getIdentifier(
                pet.imageName,
                "drawable",
                requireContext().getPackageName()
        );

        if (imageResId == 0) {
            imageResId = R.drawable.pawfect_logo;
        }

        imgExactPet.setImageResource(imageResId);
    }

    private String getRecommendationReason(String category, String exactPetName) {
        return "The model recommended " + category
                + ". Based on your selected lifestyle answers, the best exact match is "
                + exactPetName + ".";
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        selectedOptionIndex = -1;
        resetStoredAnswers();

        questionCard.setVisibility(View.VISIBLE);
        resultCard.setVisibility(View.GONE);

        showQuestion();
    }

    private void resetStoredAnswers() {
        for (int i = 0; i < selectedIndexes.length; i++) {
            selectedIndexes[i] = -1;
            answers[i] = 0f;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (classifier != null) {
            classifier.close();
        }
    }

    private static class Question {
        String questionText;
        String[] options;
        float[] values;

        Question(String questionText, String[] options, float[] values) {
            this.questionText = questionText;
            this.options = options;
            this.values = values;
        }
    }

    private static class ExactPet {
        String name;
        String category;
        String description;
        String imageName;

        ExactPet(String name, String category, String description, String imageName) {
            this.name = name;
            this.category = category;
            this.description = description;
            this.imageName = imageName;
        }
    }
}