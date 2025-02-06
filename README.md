# A backend microservice that simulates a simple user register/login using Spring Boot and DynamoDB

## Description

The service implements the following **RESTful endpoints**:

- **POST /auth/register**
    - Registers a new user.
    - Input: A JSON body containing the following fields:
        - `email` (string): Email of the user (this is also the username).
        - `password` (string): Password of the user.
        - `age` (integer): Age of the user.
        - `gender` (string): Gender of the user.
    - Response: A success message upon successful registration.

- **POST /auth/login**
    - Logs in a registered user.
    - Input: A JSON body containing the following fields:
        - `email` (string): Email of the user (this is also the username).
        - `password` (string): Password of the user.
    - Response:
        - `user_id` (string): The user initiating the assessment.
