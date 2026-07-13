# 🐾 Pawfect – Smart Pet Adoption and Care Mobile Application

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green" />
  <img src="https://img.shields.io/badge/Language-Java%20%7C%20Kotlin-blue" />
  <img src="https://img.shields.io/badge/Database-Firebase-orange" />
  <img src="https://img.shields.io/badge/AI-TensorFlow%20Lite-red" />
</p>

## 📖 Overview

**Pawfect** is an AI-powered Android mobile application designed to simplify the pet adoption process while promoting responsible pet ownership.

The application connects pet adopters with registered animal shelters, provides intelligent pet recommendations based on users' lifestyles, and includes several pet care tools to assist users before and after adoption.

---

# ✨ Features

## 👤 User Features

- Secure User Registration & Login
- AI-Powered Pet Recommendation
- Browse Available Pets
- Search Pets by
  - Name
  - Breed
  - Species
- View Detailed Pet Profiles
- Submit Adoption Inquiries
- Human Age Calculator
- Monthly Pet Care Cost Calculator
- Adoption Cost Estimator

---

## 🏠 Shelter Features

Shelters have a dedicated dashboard where they can:

- Add New Pets
- Upload Pet Images
- Manage Shelter Details
- View Adoption Inquiries
- Contact Applicants via Phone or Email
- Manage Adoption Requests

---

# 🤖 AI-Powered Pet Recommendation

Pawfect includes an intelligent **Pet Recommendation System** developed using **TensorFlow Lite (LiteRT).**

Users answer a lifestyle questionnaire including:

- Living Space
- Activity Level
- Available Time
- Budget
- Grooming Preference
- Allergies
- Previous Pet Ownership Experience

Based on these responses, the AI predicts the most suitable pet category and recommends an appropriate breed together with a confidence score.

---

# 🛠️ Technologies Used

## Mobile Development

- Android Studio
- Java
- Kotlin
- Jetpack Compose

## Backend

- Firebase Authentication
- Cloud Firestore
- Firebase Storage

## AI

- TensorFlow Lite (LiteRT)

---

# 📱 Application Modules

### Authentication

- Login
- Registration
- Forgot Password

### AI Recommendation

- Lifestyle Questionnaire
- AI Prediction
- Breed Recommendation

### Pet Management

- Browse Pets
- Search Pets
- Pet Profile

### Adoption

- Adoption Inquiry

### Shelter Management

- Shelter Dashboard
- Add Pets
- Manage Shelter Details
- Inquiry Management

### Pet Care Tools

- Human Age Calculator
- Monthly Care Cost Calculator
- Adoption Cost Estimator

---

# 📂 Project Structure

```
Pawfect-Mobile
│
├── app
│
├── data
│   ├── models
│   ├── dto
│   ├── AuthService
│   ├── PetService
│   ├── ShelterService
│   ├── StorageService
│   └── UserService
│
├── ui
│   ├── screens
│   ├── components
│   ├── layouts
│   └── navigation
│
└── tensorflow
```

---

# 🔥 Firebase Services Used

- Firebase Authentication
- Cloud Firestore
- Firebase Storage

---

# 📸 Screens

- Login
- Registration
- Home
- AI Recommendation
- Search
- Pet Profile
- Adoption Inquiry
- Shelter Dashboard
- Add Pet
- Shelter Details
- Inquiry Management
- Human Age Calculator
- Monthly Cost Calculator
- Adoption Cost Estimator

---

# 🚀 Getting Started

## Prerequisites

Install:

- Android Studio (Latest Version)
- Java JDK 17+
- Android SDK
- Git

---

# Clone the Repository

```bash
git clone https://github.com/Oshini-Sandunika/Pawfect-Mobile.git
```

Go to the project folder

```bash
cd Pawfect-Mobile
```

Open the project using **Android Studio**.

---

# Firebase Setup

This project **does not include** the `google-services.json` file for security reasons.

Create your own Firebase project.

Enable:

- Firebase Authentication
- Cloud Firestore
- Firebase Storage

Download the **google-services.json** file and place it inside:

```
app/google-services.json
```

---

# Configure Firebase

Create the following Firestore collections:

```
users
pets
shelters
inquiries
```

Configure Firebase Storage for:

```
pet_images/
shelter/
```

---

# Run the Application

Sync Gradle.

Build the project.

Run on:

- Android Emulator

or

- Physical Android Device

---

# 🤝 Contributing

1. Fork the repository

2. Clone your fork

```bash
git clone https://github.com/YOUR_USERNAME/Pawfect-Mobile.git
```

3. Create a branch

```bash
git checkout -b feature-name
```

4. Commit your changes

```bash
git add .

git commit -m "Added new feature"
```

5. Push

```bash
git push origin feature-name
```

6. Open a Pull Request

---

# 📹 Demonstration Video

https://youtu.be/zLWejjjApfg

> **Best viewed in full-screen mode.**

---

# 📂 GitHub Repository

https://github.com/Oshini-Sandunika/Pawfect-Mobile

---

# 👨‍💻 Team Members

## Group 25

| Name | Index No | Registration No |
|-------|-----------|-----------------|
| R. M. V. S. Rathnayake | FC213007 | 110619 |
| J. I. Y. D. Jayasundara | FC213035 | 110662 |
| O. S. Mihindukulasuriya | FC213037 | 110871 |

---

# 📄 License

This project was developed for academic purposes as part of the **CIS4212 – Mobile Computing** module.

---

# ❤️ Thank You

> **Choosing a pet is more than finding one you love—it's about finding the one that's right for you.**

## 🐾 Pawfect

**Making pet adoption smarter, simpler, and more responsible.**
