# TranslaSign
This repository documents the process and components of the TranslaSign Application for Hand Sign Language Translation based on Hand Sign Detection.

## Documentation Structure
### 1. Model Documentation
#### Objective
Building a model for hand sign detection based on image recognition.

#### Steps Taken

1. *Data Collection:*
   - Sourced relevant datasets for model training.
   - Public datasets such as American Sign Language (ASL) datasets.

2. *Model Training:*
   - Data Splitting: Dividing the data into training, validation, and test sets.
   - Model Architecture Selection: Choosing and implementing the appropriate model architecture, such as CNN for feature extraction. We also do transfer learning method from mobilenet model that   seems fit with our classification scenarios.
   - Training and Evaluation: Training the model using the training data and evaluating its performance with the validation and test data. Also we do some local testing using webcam to provide more accuracy especially in real life applications.

3. *Model Creation:*
   - Saved the trained model as .h5 file.
   - Convert model from hdf-5 format into deployment fit format such as .tflite and json.

4. *Model Utilization:*
   - Fine-tuned normalization techniques for image preprocessing.

### 2. API Documentation
### 3. Mobile Documentation

## Team Member
All Team Member that contributed for the success of this project from discussing idea until part integration <br>
Team ID: C241-PS507 <br>
| Name                      | ID           | Learning Path      | University             | LinkedIn |
|---------------------------|--------------|--------------------|------------------------|----------|
| Timothy Adamentha Tarigan | M180D4KY1667 | Machine Learning   | Airlangga University   |<a href='https://www.linkedin.com/in/timothy-adamentha-tarigan-964326247'>LinkedIn</a>|
| Fahmi Mohamad Ramdan      | M011D4KY3350 | Machine Learning   | Padjadjaran University |<a href='https://www.linkedin.com/in/fahmimramdan'>LinkedIn</a>|
| Hanafi Husnipradja        | M011D4KY3266 | Machine Learning   | Padjadjaran University |<a href='https://www.linkedin.com/in/hanafi-husnipradja/'>LinkedIn</a>|
| Muhamad Azriel Mauladin   | A476D4KY4556 | Mobile Development | Widyatama University   |<a href='https://www.linkedin.com/in/muhamad-azriel-13680621b'>LinkedIn</a>|
| Tangana Vito Fortunata    | A170D4KY3418 | Mobile Development | STMIK LIKMI            |<a href='https://www.linkedin.com/in/tangana-vito-6720022ba'>LinkedIn</a>|
| Sultan Zhorgy Pratama Hrp | C012D4KY0975 | Cloud Computing    | Telkom University      |<a href='https://id.linkedin.com/in/sultan-zhorgy-pratama-hrp-8a4728285'>LinkedIn</a>|
| Fauzan Maulana            | C012D4KY0567 | Cloud Computing    | Telkom University      |<a href='http://linkedin.com/in/fauzan-maulana-4090902a2'>LinkedIn</a>|







