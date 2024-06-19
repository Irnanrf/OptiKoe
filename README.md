# Optikoe
Optikoe is a glasses frame marketplace application that allows users to detect facial shapes based on photos and try on selected frames in real-time using AR. This application made using Kotlin. 
We made this project for the 2023 Bangkit Project Capstone.

## UI Optikoe
Here is a sample image of the UI from the Optikoe app:

![image](https://github.com/Irnanrf/OptiKoe/assets/43727899/77316430-330c-4d7f-a2b9-f29e47545726)
![image](https://github.com/Irnanrf/OptiKoe/assets/43727899/44eb4ba2-100f-44f6-ba81-eeb81cc63354)
![image](https://github.com/Irnanrf/OptiKoe/assets/43727899/d40adb66-0a73-411e-b77c-4ae52b41c6d1)


## Features
- Detect face shape based on photos
- Try on eyeglass frames in real-time using AR

## Technologies Used
### Mobile Development
- **ARCore**: Used for Augmented Face Filter for Virtual Frame Try-On.
- **Retrofit**: Simplifies the process of making network requests and handling API responses.
- **Glide**: Used for image loading from URLs.
- **Firebase**: Used for authentication, image storage, and data storage.

### Cloud Computing:
- **Flask (Python)**: Flask framework for creating the API that uses the ML model to predict the uploaded images.
- **Cloud Storage**: Used for storing the ML models.


### Machine Learning: Face Shape Classification
- **Data Collection**: Data was collected from Kaggle. We gathered 5000 data points classified into 5 classes (1000 each): Round, Oval, Square, Oblong, Heart
- **Data Split**: The data was split into training and test sets with a ratio of 80:20.
- **Architecture**: Inception v3 Architecture + Flatten Layer and 2 additional dense layers 


## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/irnanrf/optikoe.git
