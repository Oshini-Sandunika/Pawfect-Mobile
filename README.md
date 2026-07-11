# Pawfect Mobile App

Pawfect Mobile is an Android application developed as the mobile version of the Pawfect pet adoption website. The app helps users find a suitable pet for their lifestyle, browse adoptable pets, submit adoption requests, and estimate pet ownership costs.

The project is developed using **Android Studio**, **Java**, and **XML**, integrated with **Firebase** for backend services.

---

## Project Overview

Pawfect Mobile provides three main features:

1. **Find Your Perfect Pet**  
   An AI-based pet recommendation quiz that suggests a suitable pet category and exact breed/type based on the user’s lifestyle.

2. **Browse & Adopt Pets**  
   Users can browse detailed pet profiles (fetching data dynamically from Firebase) and submit an adoption request form for their chosen pet.

3. **Pet Ownership Tools**  
   A set of calculators and converters that help users plan adoption and pet care costs.

The app follows a brown and cream Pawfect-themed mobile UI.

---

## Main Features

### Find Your Perfect Pet

This feature uses a one-question-at-a-time quiz with a progress bar. Users answer lifestyle-based questions, and the app predicts a suitable pet category using a TensorFlow Lite model.

The quiz considers:
- Living space
- Activity level
- Daily time availability
- Allergy concerns
- Children at home
- Monthly budget
- Grooming tolerance
- Noise tolerance
- Pet ownership experience
- Preferred pet size

Supported pet categories:
- Dog
- Cat
- Rabbit
- Bird
- Fish

The app first predicts the main pet category, then recommends one exact breed/type and displays a matching image.

Example:
```text
Recommended Category: Dog
Best Matching Pet: Shih Tzu
```

### Pet Profiles & Adoption Requests

- **Detailed Pet Profiles:** Displays the pet's name, age, breed, description, estimated monthly cost, and a high-quality image loaded dynamically via Firebase Storage and Glide.
- **Adoption Form:** A comprehensive form where users can submit their Name, Email, Phone, and Reason for Adoption. The application securely pushes these requests directly into the Firebase Realtime Database.

---

### TensorFlow Lite Recommendation Model

The app integrates a TensorFlow Lite model for pet category prediction.
Since a real questionnaire-response dataset was not available, a synthetic dataset was created for training using lifestyle suitability factors for five pet categories (Dog, Cat, Rabbit, Bird, Fish).

If the model file is unavailable, the app can continue using a rule-based fallback recommendation method.

Model file location:
```text
app/src/main/assets/pet_recommendation_model.tflite
```

---

## Pet Ownership Tools

The app includes three pet ownership tools.

### Adoption Cost Estimator
Estimates the initial cost of adopting a pet. Includes variables such as Pet category, Breed/type, Size, Age group, Adoption source, Vaccination, Starter accessories, Microchip, Vet checkup, Grooming, Spay/neuter, Training, and Insurance.

The result shows a cost breakdown and total estimated adoption cost in LKR.

### Monthly Pet Care Cost Calculator
Estimates the monthly and yearly cost of caring for a pet based on Food quality, Care level, Vet saving plan, Grooming, Toys, Training, Medicine, Cleaning supplies, and Emergency funds.

The result shows monthly and yearly estimated costs in LKR.

### Human Year Converter
Converts a pet’s age into approximate human years based on species and size.

Supported categories:
- Dog
- Cat
- Rabbit
- Bird
- Fish

---

## Technologies Used

- Android Studio
- Java
- XML
- TensorFlow Lite / LiteRT
- Firebase Realtime Database
- Firebase Storage
- Glide (Image Loading)
- Android CardView
- Local drawable assets

---

## Main Java Files

```text
HomeActivity.java
FindPerfectPetActivity.java
PetRecommendationClassifier.java
ToolsActivity.java
AdoptionCostActivity.java
MonthlyCareBudgetActivity.java
HumanYearConverterActivity.java
PetProfileActivity.java
AdoptionRequestActivity.java
Pet.java
AdoptionRequest.java
```

---

## Main Layout Files

```text
activity_home.xml
activity_find_perfect_pet.xml
activity_tools.xml
activity_adoption_cost.xml
activity_monthly_budget.xml
activity_human_year_converter.xml
activity_pet_profile.xml
activity_adoption_request.xml
```

---

## Assets

Main assets used in the app:
- Pawfect logo
- Transparent paw pattern background
- Recommended pet breed/type images
- TensorFlow Lite model file

Paw pattern background location:
```text
app/src/main/res/drawable-nodpi/paw_pattern.png
```

---

## How to Run

1. Open the project in Android Studio.
2. Wait for Gradle sync to finish.
3. Place your Firebase `google-services.json` file inside the `app/` directory.
4. Make sure the TensorFlow Lite model is placed in:
```text
app/src/main/assets/pet_recommendation_model.tflite
```
5. Make sure required images are added to the drawable folders.
6. Run the app on an emulator or Android device.

---

## Current Status

The app currently includes:
- Home screen
- AI pet recommendation quiz
- TensorFlow Lite model integration
- Exact pet breed/type recommendation
- Pet image display
- Pet Ownership Tools screen
- Adoption Cost Estimator
- Monthly Pet Care Cost Calculator
- Human Year Converter
- Pet Profiles (Dynamic from Firebase)
- Adoption Request submission
- Pawfect-themed mobile UI

---

## Future Enhancements

- Save user quiz history
- Improve the model using real user data
- Add user accounts and favourites
- Connect recommended pet directly to cost calculators
- Add robust Adoptable Pet Listing Screen with Filters

---

## Authors

**O. S. Mihindukulasuriya** | FC213037  
**R. M. V. S. Rathnayake** | FC213007
