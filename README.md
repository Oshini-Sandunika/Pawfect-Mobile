# Pawfect Mobile App

Pawfect Mobile is an Android application developed as the mobile version of the Pawfect pet adoption website. The app helps users find a suitable pet for their lifestyle and plan pet ownership costs through mobile-friendly tools.

This project is developed using **Android Studio**, **Java**, and **XML**.

---

## Project Overview

Pawfect is a pet adoption support platform designed to help users find and prepare for their perfect pet. The mobile version extends the existing Pawfect website by adding an AI-based pet recommendation feature, pet ownership calculators, and a pet age converter.

The application is designed with a brown and cream theme to match the Pawfect website UI.

---

## Main Features

### 1. Home Screen

The home screen provides access to the main parts of the app:

- Find Your Perfect Pet
- Pet Ownership Tools

Users can navigate easily from the home screen to the AI recommendation quiz or the pet care tools.

---

### 2. Find Your Perfect Pet

The Find Your Perfect Pet feature recommends a suitable pet based on the user’s lifestyle preferences.

This feature uses a one-question-at-a-time quiz interface with a progress bar. Users answer lifestyle-based questions, and the app predicts the most suitable pet category using a TensorFlow Lite model.

Features include:

- One-question-at-a-time questionnaire
- Progress bar
- Lifestyle-based answer options
- TensorFlow Lite pet category prediction
- Exact breed or pet type recommendation
- Recommended pet image display
- Explanation of the recommendation
- Start again option

The questionnaire considers factors such as:

- Living space
- Activity level
- Daily time availability
- Allergy concerns
- Children at home
- Monthly pet care budget
- Grooming tolerance
- Noise tolerance
- Pet ownership experience
- Preferred pet size

Supported predicted pet categories:

- Dog
- Cat
- Rabbit
- Bird
- Fish

The app first predicts the main pet category and then selects one exact suitable pet breed or type based on the user’s answers.

Example output:

```text
Recommended Category: Dog
Best Matching Pet: Shih Tzu
