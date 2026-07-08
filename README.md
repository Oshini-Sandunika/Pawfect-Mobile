# Pawfect Mobile App

Pawfect Mobile is an Android application developed as the mobile version of the Pawfect pet adoption website. The app focuses on helping users plan for pet ownership through mobile-friendly tools such as cost estimators, monthly care budget calculators, and pet age conversion.

This project is developed using **Android Studio** with **Java** and **XML**.

---

## Project Overview

Pawfect is a pet adoption support platform designed to help users find and prepare for their perfect pet. The mobile version extends the existing website by adding interactive pet ownership tools and preparing the foundation for future AI-based pet recommendation features.

---

## Main Features

### 1. Pet Ownership Tools Screen

The tools screen provides access to different pet ownership calculators in a mobile-friendly layout.

Available tools:

- Adoption Cost Estimator
- Monthly Pet Care Cost Calculator
- Human Year Converter

---

### 2. Adoption Cost Estimator

The Adoption Cost Estimator helps users estimate the initial cost of adopting a pet.

Features include:

- Select pet category
- Select breed or pet type
- Select pet size
- Select age group
- Select adoption source
- Add optional initial care services
- View total estimated adoption cost
- View cost breakdown

Supported pet categories:

- Dog
- Cat
- Rabbit
- Bird
- Fish

Example cost factors:

- Adoption fee
- Vaccination
- Starter accessories
- Microchip or identification
- First vet checkup
- Grooming
- Spay or neuter estimate
- Training or settling support
- Insurance estimate

---

### 3. Monthly Pet Care Cost Calculator

The Monthly Pet Care Cost Calculator helps users estimate the ongoing monthly and yearly cost of caring for a pet.

Features include:

- Select pet category
- Select breed or pet type
- Select pet size
- Select age group
- Select food quality
- Select care level
- Select vet saving plan
- Add optional monthly care services
- View monthly cost
- View yearly cost
- View cost breakdown

Example monthly cost factors:

- Food cost
- Routine care
- Vet saving
- Grooming
- Pet insurance
- Toys and treats
- Training
- Medicine or supplements
- Cleaning supplies
- Emergency fund saving

---

### 4. Human Year Converter

The Human Year Converter converts a pet’s age into approximate human years.

Features include:

- Select pet category
- Select breed or pet type
- Select pet size
- Enter pet age
- Convert pet age into approximate human years
- Display pet life stage
- Show explanation of the result

Supported pet categories:

- Dog
- Cat
- Rabbit
- Bird
- Fish

---

## Future Enhancement

The mobile app is designed to later include an AI-powered pet recommendation system.

Planned AI feature:

### Find Your Perfect Pet

This feature will include:

- A pet preference questionnaire
- TensorFlow Lite model integration
- Pet category prediction
- Display of matching pets based on prediction
- Connection between predicted pet category and cost calculators

Example flow:

```text
User answers pet quiz
↓
TensorFlow Lite model predicts suitable pet category
↓
App displays matching pets
↓
User opens cost calculator for the recommended pet
