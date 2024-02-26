# Java Spring Boot Quiz Application

## Introduction
This project is a web-based quiz application developed using Java Spring Boot. It offers a platform for users to participate in quizzes, select categories, and view their results. The application leverages RESTful APIs for managing questions and answers, and utilizes Thymeleaf templates for the frontend interface.

## Technologies Used

This project is built using a variety of technologies, each serving a specific purpose within the application:

1. **Java (with Spring Boot Framework):**
    - **Backend Development:** Java is used as the primary programming language for backend development. Spring Boot simplifies the setup and development of new Spring applications.
    - **RESTful API Implementation:** Java, in combination with Spring Boot, is used to create RESTful services that handle quiz data (questions and answers).

2. **HTML, CSS, and Thymeleaf:**
    - **Frontend Development:** HTML is used to structure the web pages of the quiz application. CSS is utilized for styling, and Thymeleaf templates integrate the frontend with the backend services.
    - **User Interface:** These technologies are used to design and implement the user interface, including the quiz layout, category selection, and results display.

3. **JavaScript:**
    - **Client-Side Scripting:** JavaScript is used for enhancing the user interface and interactivity of the web pages. It helps in creating a dynamic and responsive user experience.

4. **MySQL:**
    - **Database Management:** MySQL is used as the database system to store and manage the quiz questions and answers.

5. **Maven:**
    - **Dependency Management and Build Tool:** Maven is used for managing project dependencies and streamlining the build process.

6. **IntelliJ IDEA:**
    - **Integrated Development Environment (IDE):** IntelliJ IDEA is recommended for developing this application, providing powerful coding assistance and ergonomic design.

7. **DataGrip (Optional):**
    - **Database Tool:** DataGrip is suggested as an optional tool for managing the MySQL database more efficiently.

## Prerequisites
- Java JDK 17
- MySQL 8.0
- Maven (for dependency management and building the project)
- IntelliJ IDEA (optional, for IDE-based setup)
- DataGrip (optional, for database management)

## Setup and Installation

### Clone the Repository
```shell
git clone [repository URL]
cd [project folder]
```

### Database Setup
- Create a MySQL database for the project.
- Execute `questions_schema.sql` and `answers_schema.sql` to create the necessary tables.
- Import `questions_data.sql` and `answers_data.sql` to populate the database with initial data.
- Optional: Use a database tool like DataGrip for easier database management.

### Configure Application Properties
- Open `application.properties`.
- Update the database URL, username, and password as per your MySQL setup.

### Build and Run the Application
- Command Line:
  ```shell
  mvn clean install
  mvn spring-boot:run
  ```
- IntelliJ IDEA:
    - Open the project in IntelliJ IDEA.
    - Wait for IntelliJ to import and download dependencies.
    - Run 'QuizGameApplication'.

## Running the Application
After successfully building and running the application:
1. Open a web browser.
2. Navigate to `http://localhost:8080/start`.
3. Interact with the quiz application, which guides you through the quiz process.

## Navigating the Application
The application guides you through the quiz process automatically:
1. **Start Page**: Begin your quiz journey here.
2. **Category Selection**: Choose a category for your quiz.
3. **Quiz Questions**: Answer questions from the chosen category.
4. **Results Page**: View your quiz results at the end.

## How to Use

Here's a quick walkthrough of quiz application:

### Starting the Quiz
![StartingQuizAndLose](path/gif.gif)
*Selecting a quiz category and starting the quiz.*

### Win Condition
![WinCondition](path/gif.gif)
*Answering questions and winning.*

### Timeout Condition
![TimeoutCondition](path/gif.gif)
*Timout screen.*

### Scoreboard
![Scoreboard](path/gif.gif)
*Scoreboard screen.*

## Project Structure
- **Model Classes**: `Question.java`, `Answer.java`.
- **Repository Interfaces**: `QuestionRepository.java`, `AnswerRepository.java`.
- **Service Classes**: `RandomQuestionService.java`.
- **Controller Classes**: `QuestionController.java`.
- **HTML Templates**: Thymeleaf templates (`categories.html`, `questions.html`, etc.).
- **Configuration Files**: `application.properties`, `pom.xml`.

## Features
- RESTful API for managing quiz questions and answers.
- Seamless user interface for quiz participation.
- Automatic navigation through quiz stages.
- User Authentication and Authorization.
- Score Tracking and Leaderboards.

## Future Enhancements

- Advanced Quiz Settings (time limits, difficulty levels).
- Gamification Elements (badges, rewards).
- Mobile Responsiveness.
- Additional Database Tool Integration.
- Unit Test.

## Contributing
Contributions are welcome. Please follow these guidelines:
- Fork and create a new branch for your feature.
- Write clean, commented, and tested code.
- Submit a pull request with a detailed description.

## License
[MIT](https://choosealicense.com/licenses/mit/)
