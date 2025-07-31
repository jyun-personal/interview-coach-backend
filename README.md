# Interview Coach - Backend

## Application Name and Description

**Interview Coach** is a comprehensive full-stack interview preparation application that helps job seekers excel in
their job
search. Users can paste job descriptions, receive AI-generated mock interview questions tailored to specific roles,
practice their
responses, and get AI-powered feedback from a virtual career coach to improve their interview performance.

## Entity-Relationship-Diagram

![ERD](./ERD.png)

## Render.com Deployment

https://interview-coach-backend-slvr.onrender.com

![render log](./screenshots/render/14-render-log.png)

## Features

- **User Authentication**: Secure signup/login with password hashing using BCrypt
- **Profile Management**: Users can create and update their professional profiles
- **Job Application Tracking**: Full CRUD operations for managing job applications
- **AI-Powered Question Generation**: Integration with Pollinations.ai to generate role-specific interview questions
- **Intelligent Feedback System**: AI analyzes user responses and provides constructive feedback with scoring
- **Fallback Mechanisms**: Pre-seeded questions ensure functionality even when AI services are unavailable
- **Data Seeding**: Realistic test data with 3 complete user personas
- **Robust Error Handling**: Global exception handler with custom exceptions
- **Logging**: AOP-based logging for all API calls (console logs + external log files)
- **CORS Configuration**: Secure cross-origin resource sharing setup

## Technologies Used

- **Java 17**: Core programming language
- **Spring Boot 3.2.3**: Framework for building the REST API
- **Spring Data JPA**: ORM for database operations
- **PostgreSQL**: Primary database for production
- **Spring Security**: Password encryption and security configurations
- **Spring Validation**: Request validation
- **Spring AOP**: Aspect-oriented programming for logging
- **Lombok**: Reduces boilerplate code
- **ModelMapper**: DTO-Entity mapping
- **Maven**: Build and dependency management
- **JUnit & Mockito**: Unit testing framework
- **Logback**: Logging framework

## Bonus Requirements Met

### ✅ Deployment

- Both frontend and backend are deployed (Netlify + Render)

### ✅ Data Seeding

- Implemented comprehensive data seeding in `DataSeeder.java`

### ✅ Aspect-Oriented Programming - AOP

- Implemented `LoggingAspect.java` using Spring AOP
- Logs all controller and service method calls with execution time
- Configured external file logging via Logback (`application.log` and daily rolling files)
- Captures method entry, exit, and performance metrics

### ✅ Enums

- Implemented multiple enums for type-safe fields:
    - `UserRole` enum for user roles (JOBSEEKER, EMPLOYER)
    - `Gender` enum for profile gender field
    - `QuestionType` enum for interview question types (BEHAVIORAL, TECHNICAL, SITUATIONAL, CASE_STUDY)
- Used throughout entities and DTOs for better code quality

### ✅ Pagination

- Implemented pagination for job applications endpoint
- `GET /api/job-applications` returns paginated results
- Supports page number, size, and sorting parameters
- Returns proper pagination metadata in response

### ✅ Testing

- Comprehensive unit tests for service and controller layers
- `JobApplicationServiceTest` and `JobApplicationControllerTest` with Mockito
- Tests both happy and sad paths with proper mocking

## List of all endpoints tested in Postman:

### Authentication Endpoints (Render.com Deployment)

1. POST /api/auth/signup - User registration
   ![auth signup](./screenshots/render/01-render-auth-signup.png)

2. POST /api/auth/login - User login
   ![auth login](./screenshots/render/02-render-auth-login.png)

3. GET /api/users/{userId}/profile - Get user profile
   ![get profile](./screenshots/render/03-profile-get-profile.png)

4. PUT /api/users/{userId}/profile - Update user profile
   ![update profile](./screenshots/render/04-render-update-profile.png)

5. POST /api/job-applications - Create job application
   ![create job app](./screenshots/render/05-render-add-job-application.png)

6. GET /api/job-applications - Get all job applications for user (user ID provided via X-User-ID in header)
   ![get all job apps for user](./screenshots/render/06-render-get-all-job-application-for-user.png)

7. GET /api/job-applications/{jobAppId} - Get specific job application by its UUID and X-User-ID through header (each
   user can only view their own job applications)
   ![get job app by UUID](./screenshots/render/07-render-get-job-application-by-uuid.png)

8. PUT /api/job-applications/{jobAppId} - Update job application with UUID and X-User-ID
   ![update job application](./screenshots/render/08-render-update-job-application.png)

9. DELETE /api/job-applications/{jobAppId} - Delete job application (X-User-ID provided in header to ensure each user
   can only delete their own job applications)
   ![delete job application](./screenshots/render/09-render-delete-job-application.png)

10. POST /api/job-applications/{jobAppId}/questions/generate - Use the power of AI to generate practice interview
    questions
    ![generate questions](./screenshots/render/10-render-generate-questions.png)

11. GET /api/job-applications/{jobAppId}/questions - Get questions for a specific job application that belongs to the
    current user (X-User-ID)
    ![get question for job](./screenshots/render/11-render-get-questions-for-job.png)

12. PATCH /api/job-applications/{jobAppId}/questions/{questionId}/respond - User submits their response to an interview
    question in a specific job application and get AI feedback (X-User-ID to verify user identity)
    ![respond feedback](./screenshots/render/12-render-response-aifeedback.png)

13. GET /actuator/health - Actuator Health
    ![actuator health](./screenshots/render/13-render-actuator-health.png)

### Authentication Endpoints (Localhost)

1. POST /api/auth/signup - User registration
   ![user signup](./screenshots/postman/01-user-signup.png)
   ![password hashed](./screenshots/postman/01-user-signup-password-hashed.png)

2. POST /api/auth/login - User login
   ![user login successful](./screenshots/postman/02-user-login-successful.png)
   ![user login wrong password](./screenshots/postman/02-user-login-wrong-password.png)

### Profile Endpoints

3. POST /api/users/{userId}/profile - Create profile (won't work since auto-created. User will have to use PUT to update
   their profile instead)
   ![create profile](./screenshots/postman/04-create-user-profile.png)

4. GET /api/users/{userId}/profile - Get user profile
   ![get user profile 01](./screenshots/postman/03-get-user-profile.png)
   ![get user profile 02](./screenshots/postman/03-get-user-profile-02.png)
   ![get user profile invalid id](./screenshots/postman/03-get-user-profile-invalid-id.png)

5. PUT /api/users/{userId}/profile - Update user profile
   ![profile updated](./screenshots/postman/05-update-profile.png)
   ![profile update with invalid ID](./screenshots/postman/05-update-profile-invalidId.png)

### Job Application Endpoints

6. POST /api/job-applications - Create job application
   ![xuserID id is in header](./screenshots/postman/06-create-job-application-XUserID.png)
   ![create job application](./screenshots/postman/06-create-job-application-successful.png)

7. GET /api/job-applications - Get all job applications for user (user ID provided via X-User-ID in header)
   ![get all job for user](./screenshots/postman/07-get-all-job-applications-for-user.png)
   ![get all jobs with invalid user id](./screenshots/postman/07-get-all-job-applications-for-user-invalid-id.png)
   ![all jobs for the user shown in pagination](./screenshots/postman/07-get-all-job-applications-for-user-pageable-pagination-data.png)

8. GET /api/job-applications/{jobAppId} - Get specific job application by its UUID and X-User-ID through header (each
   user can only view their own job applications)
   ![get a specific job apps for a user](./screenshots/postman/08-get-job-application-with-UUID-successful.png)
   ![trying to get job app of another user](./screenshots/postman/08-get-job-application-with-UUID-failed-due-to-XUserID-mismatch.png)

9. PUT /api/job-applications/{jobAppId} - Update job application with UUID and X-User-ID
   ![update job application](./screenshots/postman/09-update-job-application-with-UUID.png)
   ![trying to update someone else's job application](./screenshots/postman/09-update-job-application-with-UUID-invalid-XUserID.png)

10. DELETE /api/job-applications/{jobAppId} - Delete job application (X-User-ID provided in header to ensure each user
    can only delete their own job applications)
    ![delete job application successful](./screenshots/postman/10-delete-job-application-with-UUID-XUserID.png)
    ![trying to delete someone else's job application](/screenshots/postman/10-delete-job-application-with-UUID-invalid-XUserID.png)

### Interview Question Endpoints

11. POST /api/job-applications/{jobAppId}/questions/generate - Use the power of AI to generate practice interview
    questions
    ![generated 3 interview question with AI for the current job app](./screenshots/postman/11-generate-interview-questions-successful.png)
    ![of course the user can only generate questions for their own job applications](./screenshots/postman/11-generate-interview-questions-invalid-XUserId.png)

12. GET /api/job-applications/{jobAppId}/questions - Get questions for a specific job application that belongs to the
    current user (X-User-ID)
    ![get all questions for job application](./screenshots/postman/12-get-all-questions-for-job-application.png)
    ![of course each user can only view their own questions](./screenshots/postman/12-get-all-questions-for-job-application-invalid-XUserID.png)

13. PATCH /api/job-applications/{jobAppId}/questions/{questionId}/respond - User submits their response to an interview
    question in a specific job application and get AI feedback (X-User-ID to verify user identity)
    ![user submits reponse and get AI feedback and a score](./screenshots/postman/13-user-submit-response-and-get-ai-feedback-successful.png)
    ![of course each user can only respond to their own questions in their own job applications](./screenshots/postman/13-user-submit-response-and-get-ai-feedback-invalid-XUserID.png)

*Note*: Remember to include the X-User-ID header in all requests (except auth endpoints) for user authorization.

## Test Results

### JobApplication Controller Tests

![ja controller test](./screenshots/tests/Test%20Results%20-%20JobApplicationControllerTest.png)

### JobApplication Service Tests

![ja service test](./screenshots/tests/Test%20Results%20-%20JobApplicationServiceTest.png)

## AOP - Logging in console and in external file

![AOP console log](./screenshots/bonus/01-AOP-Console-Log.png)
![AOP log file](./screenshots/bonus/01-AOP-Log-File.png)

## Spring Actuator Health (Localhost)

![actuator health](./screenshots/postman/14-actuator-health.png)

## Spring Actuator Health (Render.com deployment)

![render actuator health](./screenshots/render/13-render-actuator-health.png)