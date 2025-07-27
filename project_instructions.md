Final Capstone Project
This is it! The Capstone Project!
Project Journey
Welcome to your capstone project week! This is when you will typically learn the most. During this week you’ll have
fewer/no lectures and deliverables that are not related to your project. Instead, you’ll primarily be self-directed to
plan, build, and complete your full-stack capstone project.

Your Instructors will be there to help you along the way when you need it but you’ll be doing most of the planning and
the work. At the end of the project week, you’ll be expected to have a working project that you can show and tell.

Daily Standups
We will have daily standup meetings for the first 15 minutes during capstone project week.
This is where you will explain what you did yesterday, what you will do today and if you are stuck on something.
Content
Section Description
Project Overview and Setup Set up the development environment.
Backend Project Details and Requirements Detailed project requirements and features.
Backedn Project Tips Project tips and tricks.
Frontend Project Details and Requirements Detailed project requirements and features.
Frontend Project Tips Project tips and tricks.
Project Deliverables Detailed project deliverables.
Project Rubric Project rubric.
Beyond this Project
Section Description
How to level up How to level up your skills.

Final Capstone Project
Project Overview and Setup
You will work individually on this project.
❗️ As a reminder, General Assembly has a zero-plagiarism policy. Your project’s code must be substantially yours. Do not
copy code from similar projects or other sources. Copying code is a serious violation of General Assembly’s academic
integrity policy. Usage of Google, stackoverflow, AI tools and other resources is allowed for learning purposes and
reference, but the project must be 100% original.

⚠️ Remember, using AI-generated code defeats the purpose of learning. It’s much better to try, fail, and explain what
went wrong in your comments than to submit code you didn’t write yourself. Embrace the learning process!

Project Overview
Welcome to your final capstone project! This is the culmination of everything you’ve learned across HTML, CSS,
JavaScript, Java, SQL, Spring Boot, and React. In this capstone, you will design, architect, and build a complete
full-stack application from scratch.

This project is your opportunity to showcase your creativity and technical skills on a whole new level. Unlike previous
unit projects where topics and requirements were more defined, this capstone gives you complete freedom to decide what
you want to build. Think of a real-world problem to solve, a passion project, or an innovative application you’d like to
bring to life—the idea is entirely up to you. If you are unsure about your project idea or want to ensure its scope is
manageable, we highly encourage you to discuss it with your instructors for feedback and refinement.

You will be developing a full-stack application with the following core technologies:

Frontend: A dynamic, single-page application built with React.
Backend: A robust, RESTful API powered by Spring Boot and a PostgreSQL database.
A key requirement for this project is to maintain two separate GitHub repositories: one for your frontend code and one
for your backend. This mirrors professional development workflows and is a crucial part of the project structure.

While the conceptual vision for the project is yours, the following sections will provide a detailed breakdown of the
technical requirements, features, and deliverables to guide your development process. Let’s get started!

A Note on Project Ideas
While you have complete creative freedom, we encourage you to explore unique ideas that go beyond the projects we have
built together in class and in labs. We’ve already explored domains like healthcare management and student/course
registration systems.

The goal is to challenge yourself with a new problem. This is your chance to build something that reflects your personal
interests or solves a problem you’ve noticed. If your idea shares similarities with a past project, that’s perfectly
fine! Just try to add your own unique features, perspective, or a different approach to make it distinctly yours. The
most rewarding projects are often the ones that you are passionate about.

Getting Started
This section will guide you through the initial setup of your project environment. Success in this capstone project
begins with a strong foundation, so it’s crucial to follow these setup steps carefully. You will be establishing the
structure for both your frontend and backend applications, including setting up your version control system (Git) and
repositories on GitHub. A well-organized project is easier to manage, debug, and showcase.

Repository Setup and Workflow
Unlike previous projects, this capstone requires a professional-grade setup using two separate repositories—one for your
frontend application and one for your backend service. This separation of concerns is a standard practice in the
software development industry.

Two Repositories
You must create two distinct public repositories on GitHub:

A Frontend Repository: This will house your entire React application.
A Backend Repository: This will contain your complete Spring Boot application.
README Documentation
A professional project is a well-documented one. The README.md file in the root of each repository is the first thing
other developers (and potential employers) will see. Each README.md must be updated to include the following sections:

Application Name and Description: At the top of your README.md in both repositories, clearly state the name of your
application and write a concise description of its purpose and what it does.
ERD (Entity-Relationship Diagram): In your backend repository, you must include an image of your ERD. This diagram is
the blueprint for your database and is essential for understanding your application’s data structure. You can use tools
like Excalidraw, Lucidchart, Miro, or any other diagramming tool of your choice to create it.
API Documentation: In your backend repository’s README.md, you must provide clear documentation for your API endpoints.
This should list each endpoint, the HTTP method, and a brief description of what it does. For example:
Copy

### Students API

- `POST /students` - Creates a new student.
- `GET /students` - Retrieves a list of all students.
- `GET /students/{id}` - Retrieves a specific student by their ID.

### Courses API

- `POST /courses` - Creates a new course.
- `GET /courses` - Retrieves a list of all courses.
  Features: List the key features and functionalities of your application.
  Technologies Used: Specify the technologies for that particular repository.
  For the frontend repo, this would include React, TypeScript, React Router, etc.
  For the backend repo, this would include Java, Spring Boot, PostgreSQL, etc.
  Branching and Git Flow
  To maintain a clean and manageable codebase, you are required to use the Git Flow branching model for both
  repositories.

You should create a development branch in each repository. All of your work—every new feature, bug fix, and change—must
be done on this development branch. The main (or master) branch should always represent a stable, completed version of
your project.

When you have finished your project, you will create a Pull Request on GitHub to merge your development branch into your
main branch.

Committing Your Work
Commit your code frequently with clear, descriptive commit messages. Small, regular commits create a detailed history of
your work, making it easier to track changes, revert to previous versions if something goes wrong, and collaborate with
others. Pushing your commits to GitHub regularly also ensures your work is safely backed up.

(Bonus) Deployment
For an additional challenge, you can deploy both your frontend and backend applications to the web. This is an excellent
way to make your project accessible and to learn about the full software development lifecycle.

You will need to deploy each application to a separate hosting service, which means your frontend will have its own live
URL, and your backend will have its own live base URL. The main challenge will be configuring them to communicate with
each other (handling CORS).

A video tutorial will be provided to guide you through the deployment process for both a React and a Spring Boot
application.

Project Submission
To successfully submit your project, you must provide the following:

A link to your public frontend GitHub repository.
A link to your public backend GitHub repository.
(If you completed the bonus) A link to your live, deployed frontend application.
(If you completed the bonus) The base URL of your live, deployed backend API.

Final Capstone Project
Backend Project Details and Requirements
Backend Requirements (Spring Boot & PostgreSQL)
This section outlines the technical requirements for the backend portion of your capstone project. The backend will be a
robust RESTful API built with Spring Boot that serves as the data and logic foundation for your frontend application.
While you have creative freedom over the project’s features, the following technical specifications must be met to
ensure a high-quality, professional-grade application.

1. Database and Entity Design
   A well-designed database is the cornerstone of any application. You are required to design a relational database
   schema that logically represents your project’s domain.

Entity & Relationship Requirements: Your data model must include:
At least one one-to-one relationship.
At least two one-to-many relationships.
At least one many-to-many relationship.
User Entity: At least one of your entities must be a User or Customer or related entity, which will represent the users
of your application.
Entity-Relationship Diagram (ERD): You must create a clear and accurate ERD that visually represents your entities and
their relationships. This diagram is a mandatory part of your backend repository’s README.md file.
Database Constraints: Your database schema must use appropriate table and column constraints (e.g., NOT NULL, UNIQUE,
CHECK) to enforce your business rules at the database level.

2. API Architecture and Endpoints
   Your application must expose a well-structured RESTful API for your frontend to consume.

Layered Architecture: You must implement a layered architecture, clearly separating concerns into Controller, Service,
and Repository layers.
Data Transfer Objects (DTOs): You are required to use DTOs for all API requests and responses. This is crucial for
decoupling your API from your internal database structure and preventing common issues like circular references.
Authentication Endpoints: Your API must include endpoints for user authentication. A common approach is to create an
AuthController to handle these operations:
An endpoint for user registration (e.g., POST /auth/register).
An endpoint for user login (e.g., POST /auth/login).
CRUD Operations: You must implement full Create, Read, Update, and Delete (CRUD) operations for your other core
entities.

3. Code Quality and Best Practices
   Adherence to professional coding standards is a key evaluation criterion.

Clean Package Structure: Maintain a logical and organized package structure (e.g., com.yourapp.controller,
com.yourapp.service, com.yourapp.repository, com.yourapp.model, com.yourapp.dto).
Spring Data JPA: Utilize Spring Data JPA and its repository pattern for all database interactions.
Lombok: Use Lombok to reduce boilerplate code (e.g., getters, setters, constructors).
Software Engineering Principles: Your code should demonstrate an understanding of principles like DRY (Don’t Repeat
Yourself), SOC (Separation of Concerns), SRP (Single Responsibility Principle), and KISS (Keep It Simple, Stupid).

4. Validation and Error Handling
   A robust API must handle invalid data and errors gracefully.

Request Validation: You must use the Spring Validation framework (spring-boot-starter-validation) to validate all
incoming request DTOs against the business rules defined for your entities.
API Responses: Your API should return meaningful error messages and use appropriate HTTP status codes to indicate the
outcome of a request (e.g., 200 OK, 201 Created, 400 Bad Request, 404 Not Found).
Global Exception Handling: Implement a centralized exception handler using @RestControllerAdvice to manage errors
consistently across the entire application.
Custom Exceptions: Create and use custom exception classes (e.g., ResourceNotFoundException, BadRequestException) that
are thrown from the service layer and handled by your global exception handler.

5. Testing
   To ensure your application is reliable, you must write unit tests.

Testing Scope: Write comprehensive unit tests for the service and controller layers for at least one of your main
entities.
Mocking: Use a mocking framework like Mockito to isolate the layer you are testing (e.g., when testing the service
layer, mock the repository).
Coverage: Your tests should cover both “happy path” (successful) scenarios and “sad path” (error) scenarios.

6. ❗️ A Note on Data Integrity
   When implementing delete functionality, it is crucial to carefully consider the relationships between your entities
   to prevent unintended data loss.

Handling Associated Data: Deleting a primary entity (e.g., a User) should not automatically delete all of its associated
records (e.g., their Posts or Orders) unless that is the explicit, intended behavior for your application. You must
manage your database constraints (ON DELETE clauses) and service-layer logic to avoid accidentally deleting valuable
data.
Deleting Link Records: When deleting a record that serves to link two other entities (such as an entry in a join table
for a many-to-many relationship), ensure that the deletion only removes the association and does not delete the primary
entities themselves.
Optional Bonus Requirements
You can enhance your project and demonstrate advanced skills by implementing any of the following optional features.

Deployment (Highly Recommended): Deploy your Spring Boot application to a cloud service so it’s accessible via a public
URL. This is a critical skill for any full-stack developer. The deployment process will be covered in detail in the
provided video tutorial.
JWT Authentication (Highly Recommended): While your required AuthController handles the core registration and login
functionality, this bonus challenge involves enhancing it with a more secure, token-based system using JSON Web Tokens (
JWT). This means upgrading your login endpoint to generate a JWT upon a successful login, and then securing your other
API endpoints so they require a valid token for access. This topic will also be covered in the video tutorial.
Data Seeding: To make testing and demonstration easier, automatically populate your database with some initial sample
data when the application starts.
Aspect-Oriented Programming (AOP): Use AOP for cross-cutting concerns, such as logging method calls in your service and
controller layers. For an extra challenge, configure your application to write these logs to an external file instead of
just the console.
Custom JPQL Queries: Demonstrate your mastery of data retrieval by writing at least three custom, non-trivial queries
using the Java Persistence Query Language (JPQL) in your repositories.
Pagination: When fetching a list of records (e.g., all products or all posts), implement pagination to break the results
into manageable pages instead of returning everything at once.
Enums: Use Java Enums for entity fields that have a fixed set of possible values (e.g., user roles, order statuses,
category types). This makes your code more readable and type-safe.

Final Capstone Project
Backend Project Tips
🧠 Plan Before You Code
Before writing a single line of Java, take the time to plan. A few hours of planning can save you days of debugging.

Design Your ERD First: Sketch out your entities, their attributes, and how they relate to each other. This is your
database blueprint. Don’t start coding until you have a solid ERD.
Map Out Your API Endpoints: Think about the data your frontend will need. List the API endpoints required for each of
your core entities (e.g., GET /items, POST /items, GET /items/{id}). This will guide your controller design.
🚀 Getting Started Strategy

1. Start Simple, Build Incrementally

Begin with your core entities first. A great starting point is the required User entity, since many other parts of your
application will depend on it.
Focus on getting one complete entity working from end to end (Entity → Repository → Service → Controller → DTO) before
moving to the next one.
Write Tests As You Go: It is a best practice to write unit tests for your service and controller layers as soon as you
complete a feature. This helps catch bugs early and ensures your code remains reliable as you add more functionality.
Don’t save all your testing for the end of the project.
Build relationships incrementally. Once your User entity is working, add a second entity and establish its
relationship (e.g., a one-to-many). Test it thoroughly before adding the next one. Don’t try to implement all entity
relationships at once.

2. Create DTOs EARLY ⭐

This is crucial for beginners!
Create your Data Transfer Objects (DTOs) immediately after creating your entities.
DTOs prevent common circular reference issues (infinite loops) when your objects are converted to JSON.
DTOs also make your API responses cleaner, more predictable, and help you expose only the data you want.

3. A Note on spring.jpa.hibernate.ddl-auto

For development, using spring.jpa.hibernate.ddl-auto=update in your application.properties is very helpful as it
automatically updates your database schema.
Be aware that it’s not always perfect and is not recommended for production environments. If you face schema issues,
using validate can help you find mismatches between your entities and the database.
Testing Strategy
Test each endpoint with Postman (or a similar tool) as you build it. Don’t wait until the end.
Start with simple GET requests to make sure you can retrieve data before moving on to more complex POST and PUT
requests.
Organize Your Requests: Use Postman Collections to group your API requests for each entity (e.g., a “Users” collection,
a “Products” collection).
Use Environments: Set up a Postman Environment to store variables like your baseURL (e.g., http://localhost:8080). This
makes it easy to switch between local and deployed environments.
Common Pitfalls to Avoid
Don’t create all entities at once. You’ll likely get overwhelmed with relationship mapping errors.
Don’t skip DTOs. You will almost certainly spend hours debugging JSON serialization issues and infinite loops if you do.
Don’t ignore validation. Add @Valid to your controller methods and validation annotations (@NotBlank, @Size, etc.) to
your DTOs early on.
Don’t forget key annotations like @RestController, @RequestMapping, @Service, @Autowired, etc.
When to Ask for Help
It’s okay to ask for help! Don’t get stuck for too long. Reach out if:

You’re stuck on the same error for more than 30-60 minutes.
You can’t get the basic CRUD (Create, Read, Update, Delete) operations working for your first entity.
Your entity relationships aren’t mapping correctly in the database.
❗Read Error Messages Carefully!
Spring Boot and Java provide very descriptive error messages. They are your best debugging tool. The stack trace might
look intimidating, but it often tells you:

What went wrong (e.g., NullPointerException, PSQLException).
Where it went wrong (the exact class and line number). Read from the top of the error message, and look for lines that
mention your own code (com.yourapp...). —
🏆 Final Success Tips
Start early! Don’t underestimate the complexity of building a full application.
Test frequently. Test after every small change to catch bugs early.
Use Git and commit often. Commit your code whenever you have a working feature. This gives you safe points to return to.
Functionality first. Make it work, then make it better.
Remember: It’s better to have 80% of the features working perfectly than 100% of the features working poorly!

Final Capstone Project
Frontend Project Details and Requirements
Frontend Requirements (React & TypeScript)
This document outlines the requirements for the frontend portion of your capstone project. You will build a modern,
interactive, and responsive single-page application (SPA) using React and TypeScript. This application will serve as the
user-facing interface for your project, communicating with the backend API you designed to create a seamless full-stack
experience.

Core Principles & Architecture
React with TypeScript: Your application must be built using React. The use of TypeScript is required to ensure type
safety and improve code quality.
Strict TypeScript Usage: You must use TypeScript effectively throughout your application. This includes creating and
using interfaces or types for your component props, API response data, and complex state variables. Your functions
should also have explicit return types where appropriate.
API Communication: The frontend is responsible for all communication with your backend API. You will use the Fetch API
to make asynchronous requests for creating, reading, updating, and deleting data.
Styling: You must use CSS to style your application. Your application must be responsive and look great on all devices.
This is a strong requirement for this capstone project.
Semantic HTML: Your application must be built using semantic HTML5
elements (<nav>, <main>, <section>, <article>, <footer>, etc.). This improves the structure, accessibility, and SEO of
your application.
Mobile-First Responsive Design
A professional web application must look great on all devices. You are required to implement a mobile-first design
approach. This means you should write your CSS to style the mobile layout first, then use media queries to add or adjust
styles for larger screens.

Your application must be responsive across the following breakpoints:

Small Screens (Mobile): 768px and below
Medium Screens (Tablet): 769px to 959px
Large Screens (Desktop): 960px and above
You are required to use modern CSS layout techniques like Flexbox to create fluid and adaptable layouts. You will use
media queries to apply different styles at the breakpoints defined above.

The Landing Page
The first thing a user will see is your application’s landing page. This page is public and does not require a user to
be logged in. Its purpose is to introduce your application to potential users, explain what it does, and encourage them
to sign up or log in.

For inspiration on what makes a great landing page, you can review this resource: What Is a Landing Page?

Your landing page must be composed of several key sections:

1. Navigation Bar
   A persistent navigation bar at the top of the page. It must include:

Your application’s name or logo.
Conditional Buttons: The buttons displayed must change based on the user’s authentication status.
If Logged Out: The navbar must show a “Login” button and a “Sign Up” button.
If Logged In: The “Login” and “Sign Up” buttons must be hidden. Instead, a single button like “Go to Dashboard” or “Open
App” should be displayed, allowing the user to enter the main part of your application.

2. Hero Section
   This is the main, attention-grabbing section at the top of your landing page. It should clearly communicate your
   application’s purpose with:

A compelling headline.
A brief, one-or-two-sentence description of the problem your app solves or the value it provides.
A primary call-to-action (CTA) button (e.g., “Get Started” or “Sign Up Now”).

3. Features Section
   A section that highlights the key features and benefits of your application. Use a combination of icons, images, and
   short descriptions to make this section visually appealing and easy to understand.

4. Footer
   A standard footer at the bottom of the page that includes elements like a copyright notice, links to social media, or
   other relevant navigation links.

User Authentication: Login & Sign Up
Your application must provide secure and user-friendly forms for users to sign up and log in. These forms are the
gateway to your application’s core features.

Professional Design: This is a strong requirement. Your Login and Sign Up forms must be well-designed, with a
professional look and feel. Pay close attention to spacing, alignment, typography, and color. The user experience should
be intuitive and seamless.
Sign-Up Form:
This form should capture all the necessary information to create a new user account as defined by your backend API.
Upon submission, the form should make an API call to your backend’s user registration endpoint.
After a successful sign-up, the user should be automatically redirected to the Login page to sign in with their new
credentials.
Login Form:
This form will be used by existing users to access the application. It should collect the user’s credentials (e.g.,
email/username and password).
Error Handling:
You must handle errors gracefully. If the backend API returns an error during sign-up or login (e.g., “Email already
exists,” “Invalid password,” or any validation error), you must display a clear and helpful error message to the user.
These error messages should appear in an appropriate location, ideally directly below the input field that caused the
error.
Application State & Routing Logic
Once a user logs in, your application needs to “remember” them as they navigate between pages. This is achieved through
a combination of browser storage and routing logic.

1. Managing User Session with Local Storage
   To keep a user logged in, you will use the browser’s localStorage. You should already be familiar with this concept
   from your previous JavaScript project, and this is a great opportunity to apply that knowledge in a React
   application. localStorage allows you to store small amounts of data that persist even if the user closes the browser
   tab or refreshes the page.

Note: For the basic functionality of your application, you will store simple user information (like userId, username, or
email) in localStorage. If you choose to implement the JWT authentication bonus, you will still use localStorage - but
instead of only storing user details directly, you will store the JWT token that your backend provides.

On Successful Login: After a user successfully logs in, your application must save a key piece of information to
localStorage to identify them. This could be their userId, username, or email.
On Logout: When a user logs out, you must remove this information from localStorage.
This piece of data in localStorage is the key to your application’s routing logic. Its presence indicates an active
session, while its absence indicates the user is logged out.

2. Routing Based on Login Status
   Your application must handle navigation differently depending on whether the user is logged in. Here are the rules
   you must implement:

Rule #1: The Landing Page is for everyone.

Any user, whether logged in or not, should always be able to view the landing page.
Rule #2: Accessing pages that require a login.

Scenario: A user who is not logged in (has nothing in localStorage) tries to navigate to any page inside your main
application (e.g., /dashboard or /profile).
Required Action: Your application must check localStorage. If it finds no user information, it must immediately redirect
the user to the /login page. The user should never see the content of these pages.
Rule #3: Visiting the Login/Sign-up pages when already logged in.

Scenario: A user who is already logged in (has user information in localStorage) tries to navigate back to the /login or
/signup page.
Required Action: Your application should recognize they are already authenticated and redirect them to their main
application page (e.g., /dashboard). They should not see the login or sign-up forms again unless they log out first.
Application Navigation with React Router
To create a seamless single-page application (SPA) experience, you are required to use the react-router-dom library to
handle all client-side routing. This allows users to navigate between different views or “pages” of your application
without causing a full page refresh.

Your application must have a clear navigation structure, implemented in a component like a Navbar or a Sidebar. This
component will contain links that allow users to move between different parts of your application. It must also include
a “Sign Out” button that:

Clears the user’s session information from localStorage.
Navigates the user back to the public landing page or the login page.
You will implement two primary types of navigation:

Declarative Navigation (User-Driven):
This is for navigation that the user initiates by clicking a link.
You will use components like <Link> from react-router-dom within your navigation bar or on your landing page to create
clickable links (e.g., a link from / to /login).
Programmatic Navigation (Action-Driven):
This is for situations where your application needs to automatically redirect a user after an action is completed.
A key example is after a successful login. Once the user’s credentials have been verified, you must use the useNavigate
hook from react-router-dom to automatically navigate them from the login page to their main dashboard or home page.
The Main Application (After Login)
Once a user successfully logs in, they should be redirected to the main part of your application. This is where the core
functionality of your project lives. This area must be a rich, interactive experience where users can view and manage
the application’s data.

Regardless of the type of application you choose to build (e.g., e-commerce, project management, social media), you must
implement the following features:

1. The Main Dashboard / Home Page
   This is the central hub of your application. The first page a user sees after login should immediately present
   valuable information. At a minimum, this page must display a list of primary items from your database (e.g., a list
   of products, user posts, or to-do tasks).

Data Fetching: This page must fetch its data from your backend API when the component loads. You will use the useEffect
hook to trigger this data-fetching operation.
State Management: The fetched data must be stored in the component’s state using the useState hook. The list you display
will be rendered by mapping over this state variable.

2. Viewing Item Details (Dynamic Routing)
   Users must be able to view more details about a specific item from the list.

Navigation: Each item in the list on your main page should be a clickable link.
Dynamic Routes: Clicking an item should navigate the user to a new page that shows only the details for that single
item. You must implement this using dynamic routes in React Router (e.g., /items/:id).
Fetching Single-Item Data: On the detail page, you will use the ID from the URL parameter to make another API call to
fetch the data for just that one specific item.

3. Creating, Updating, and Deleting Items (Full CRUD)
   Your application must provide the functionality for users to manage the data.

Create: You must have a dedicated form (either on a separate page or in a modal) for users to create a new item.
Upon submission, this form will send the data to your backend’s POST endpoint.
After a successful creation, the UI must update automatically to show the newly created item in the main list without
requiring a page refresh. This is a key aspect of state management in React.
Update: You must provide a way for users to edit existing items. This typically involves a form pre-filled with the
item’s current data that sends a PUT or PATCH request to your backend.
Delete: Each item should have a delete button. Clicking this button should trigger a DELETE request to your backend and,
upon success, remove the item from the UI instantly.
User Profile Page
A logged-in user must be able to view and manage their own profile information.

Dedicated Page: You must create a dedicated, protected route (e.g., /profile) for this feature.
View Information: This page should fetch and display the current user’s information (e.g., name, email, Bio etc…).
Update Information: The page must include a form that allows the user to update their details. Submitting this form
should send an update request to your backend API and reflect the changes in the UI upon success.
Mandatory AI Feature Integration
To add a unique and modern feature to your application, you are required to integrate a live AI API. This will give you
hands-on experience with handling asynchronous third-party API calls within React.

Implementation: A great way to implement this is by adding a floating chat button in the bottom-right corner of your
application. When clicked, it could open a small chat window where the user can ask questions and get responses from the
AI. However, you are encouraged to be creative! The goal is to integrate this AI in a way that provides a meaningful and
useful feature for your specific application. For example, you could use it to generate product descriptions, create
workout routines, or suggest content.
API Details: You will use the free text generation API from Pollinations.ai.
Endpoint: https://text.pollinations.ai/prompt/{your_prompt_here}
How to Use:
To get a response, you will make a GET request to the endpoint, replacing {your_prompt_here} with the text input from
the user.
Important: This API returns a plain text response, not JSON. You will need to use the .text() method on the response
object (const data = await response.text();) to read the data correctly.
React Best Practices & Architecture
To ensure your application is maintainable, scalable, and professional, you are required to follow these architectural
best practices.

1. Project Structure & Styling
   A well-organized project is easier to navigate and debug.

Folder Structure: You must organize your components into two distinct folders:
src/pages: This folder will contain your top-level components that represent a full page or view (e.g., HomePage.tsx,
LoginPage.tsx).
src/components: This folder will contain smaller, reusable components that are used across multiple pages (e.g.,
Button.tsx, Navbar.tsx, ProductCard.tsx).
Styling with CSS Modules: To avoid styling conflicts and keep your CSS organized, you are required to use CSS Modules
for component-specific styles. Each component should have its own corresponding .module.css file. This approach ensures
that class names are scoped locally to the component you are styling.

2. State Management
   Properly managing state is one of the most important aspects of building a React application.

Component State: For state that is only used within a single component, use standard React hooks like useState.
Shared State (Lifting State Up): When a few components need to share or modify the same state, you should “lift that
state up” to their closest common parent component and pass it down as props.

3. Global State with Context API
   For state that needs to be accessed by many components across your application (like user authentication status or
   theme), passing props down through many layers (“prop drilling”) becomes inefficient. In these cases, you must use
   the Context API.

Theme Context (Required): You are required to implement a ThemeContext to manage a light and dark mode for your
application.
There must be a toggle switch, likely in your main navigation bar, that allows the user to switch between themes.
The entire application’s color scheme should change based on the selected theme.
Authentication Context (Optional but Recommended): While you can manage the user’s login state with localStorage and
simple prop passing, the Context API is a more robust solution for handling global authentication state. The AuthContext
will be responsible for reading from and writing to localStorage, providing a centralized way for all components to
access the current user’s authentication status. A video tutorial will be provided demonstrating how to create and use
an AuthContext to manage the user’s session throughout the application.
Loading and Error Handling
Your application will be constantly communicating with your backend API. These network requests are not instantaneous
and can sometimes fail. A professional application must provide clear feedback to the user during these states.

You are required to manage and display loading and error states for all asynchronous operations.

1. The State Transition Flow
   For every API request, your component should manage three potential states:

Loading: The request has been sent, and we are waiting for a response.
Success (Data): We have received the data successfully.
Error: The request failed for some reason.
Your component’s UI must reflect which of these states is currently active.

2. Displaying Loading States
   While data is being fetched, you must show the user a loading indicator. This lets the user know that something is
   happening in the background. Examples include:

Displaying a simple text message like “Loading…”
Showing a loading spinner or animation.
This loading indicator should be replaced by either the data or an error message once the request is complete.

3. Displaying Error Messages
   If an API call fails, you must inform the user.

No alert(): You are explicitly forbidden from using the browser’s alert() function to display errors.
User-Friendly Messages: You must display a clear, user-friendly error message directly within the UI. For example, if a
list of items fails to load, you could display a message like, “Sorry, we couldn’t load the items right now. Please try
again later.” This is much better than showing a technical error code. These messages should be integrated cleanly into
your component’s design.
Using a Modal for Errors (Recommended): For a more professional user experience, consider creating a reusable ErrorModal
component. This modal could be triggered to show more detailed error information without cluttering the main UI. A
demonstration of how to build and manage a global error modal will be included in the provided video tutorials.
Testing Your Application
Testing is a critical and mandatory part of this capstone project. A well-tested application is a reliable one.

Tooling: As discussed in class, you are required to use Vitest to write unit and integration tests for your React
application.
Comprehensive Coverage: You must write tests for the core pages and reusable components of your application. This should
include your most important pages (like the dashboard, detail pages, and forms) and any complex, reusable components.
Your tests should verify:
Correct Rendering: Ensure that components render correctly without crashing.
Content and Functionality: Check that the expected text, buttons, and other key elements are present and functional.
API Call Behavior: For components that fetch data, test the different states: that a loading indicator appears, that
data is displayed correctly upon a successful API call, and that an error message is shown if the call fails.
Passing Tests: All tests must be passing in your final submission. A project with failing tests is considered
incomplete.
Putting It All Together: The Core User Journey
To help you visualize the final product, here is a step-by-step walkthrough of the basic user flow that your application
must support. This is the minimum required functionality.

Step 1: The New User Arrives

A user first lands on your public Landing Page. They can see what your application is about and are presented with
options to “Sign Up” or “Log In”.
Important Note on Access: Before logging in, if this user tries to go directly to a page that requires authentication (
like /profile), your application must check localStorage. Since no user information (userId, username, etc.) is found,
it must automatically redirect them to the login page.
Step 2: Signing Up

The user clicks “Sign Up” and is taken to your authentication page, where they see the registration form.
They fill out the form and submit it. Your frontend sends this information to the backend.
Upon a successful sign-up, the user sees a success message and is then presented with the Login Form.
Step 3: Logging In

The user fills in their new credentials and clicks “Log In”. Your frontend sends this to the backend for verification.
On success, the user is automatically redirected from the login page into the main part of your application.
Step 4: Using the Application

The user is now on their main Dashboard, Home Page or whatever page you have defined as the main page where they can see
a list of items (e.g., products, posts, tasks).
They can Create a new item using a form. After creation, the main list updates instantly without a page refresh.
They can click on any single item to navigate to a Detailed View Page for that specific item.
From the detailed view or the main list, they can Update or Delete an item, with the changes reflected immediately in
the UI.
The user also has access to the unique AI Feature you integrated.
Step 5: Managing Their Profile

At any time, the user can navigate to their Profile Page. Here, they can view their current information and use a form
to update it.
Step 6: Logging Out

A “Sign Out” button is always accessible in the navigation.
When the user clicks “Sign Out”, their session is cleared, and they are redirected back to the public landing page.
Optional Bonus Requirements
Deployment (Highly Recommended): Deploy your React application to a static hosting service like Netlify or GitHub Pages.
This will give your project a live, public URL that you can share. The deployment process for a React application will
be covered in the provided video tutorials.
Client-Side JWT Handling: If you implement the JWT bonus on the backend, you must handle it on the frontend. This
involves:
Storing the token received from the login endpoint in localStorage.
Including the token in an Authorization: Bearer <token> header for every subsequent API request to protected backend
routes.
Implementing logic to clear the token from storage upon logout.
This is disccussed in the demo video.

Final Capstone Project
Frontend Project Tips
🧠 Plan Before You Code
A great user interface starts with a great plan. Before you start writing components, take time to think about the
structure and user flow of your application.

Sketch Your UI (Wireframe): You don’t need to be a designer. Use a tool like Excalidraw or even just a piece of paper to
draw simple boxes representing your pages and components. This helps you think about layout and what goes where before
you get lost in CSS.
Think in Components: Break down your UI sketches into a hierarchy of components. Which parts are pages (e.g., HomePage,
ProfilePage)? Which are reusable (e.g., Button, Card, Navbar)? How will they nest inside each other?
Define Your Types: Before fetching data, define the interface or type for your API data and component props in
TypeScript. This will save you from many common errors and make your code easier to work with.
🚀 Getting Started Strategy

1. Build Static UI First, Then Add Logic

Start by building the visual parts of your application with hard-coded, placeholder data. For example, create
a <ProductCard> component and make it look perfect with fake text and a placeholder image.
This approach allows you to focus on your HTML structure and CSS styling without worrying about state or API calls.

2. Build One Feature at a Time

Build your app incrementally. A good flow is:
Build the static Landing Page.
Implement the Login/Sign-Up forms and their basic functionality.
Build the static layout of your main Dashboard/Home Page.
Connect it to your backend API to fetch and display real data.

3. Use Mock Data Before Connecting to the Backend

Create a fake array of objects that looks exactly like the data your API will send. Use this mock data to build out your
lists, detail pages, and other UI elements. Once you are ready, you can simply swap out the mock data for a live API
call.
State Management Strategy
Start Local: Always start with local component state using useState. Don’t over-complicate things early.
Lift State Up When Necessary: If you find that two sibling components need access to the same state, move that state up
to their closest common parent component and pass it down as props.
Use Context API for Global State: If you are passing a prop through more than 2-3 levels of components (this is called
“prop drilling”), it’s a clear sign that you should use the Context API instead. This is perfect for things like Theme,
User Authentication Status, or anything that many components need to access.
Common Pitfalls to Avoid
Large Components: If a component file is getting very long (e.g., over 100-150 lines), it’s a sign that you should break
it down into smaller, more manageable components.
Forgetting useEffect Dependencies: Forgetting to add variables to the dependency array of a useEffect hook can lead to
infinite loops or stale data. Always include every variable from the outer scope that you use inside the effect.
Mutating State Directly: Never modify state directly (e.g., myArray.push(newItem)). This will not cause React to
re-render. Always use the setter function from useState and provide a new object or array (e.g.,
setMyArray([...myArray, newItem])).
Forgetting Keys in Lists: When you use .map() to render a list of components, always add a unique key prop to the
top-level element inside the map. This is crucial for React’s performance.
Testing Strategy (Vitest)
Test As You Go: Write tests for your components as you build them. It’s much easier than trying to add tests to a huge
application at the end.
What to Test:
Rendering: Does the component render without crashing?
Content: Does it display the correct text, props, or data?
User Interaction: Simulate user events like clicks and typing to ensure the component behaves as expected. Use the
@testing-library/user-event library for this.
Mocking API Calls: You don’t want your tests to make real network requests. Learn how to use Vitest’s mocking features (
vi.fn) to mock your fetch calls and test how your component behaves during loading, success, and error states.
❗Read Error Messages Carefully!
The browser’s developer console and your terminal are your best debugging tools. React provides very clear error
messages.

Look for errors like “keys are not unique,” “cannot read property of undefined,” or “Hooks can only be called inside the
body of a function component.” They often tell you exactly where the problem is.
🏆 Final Success Tips
Commit often. Use Git to save your progress frequently.
Remember: It’s better to have 80% of the features working perfectly than 100% of the features working poorly!

Final Capstone Project
Project Deliverables
Github Repositories
You are required to submit two separate GitHub repository links on Canvas: one for your frontend application and one for
your backend API.

Two Repository Links: One for the frontend and one for the backend.
README Files: Each repository must have its own comprehensive README.md file with the required information for that part
of the project.
The backend README.md file must include your Entity-Relationship Diagram (ERD).
Commit History: Both repositories must have a history of commits with clear, meaningful commit messages that reflect
your development process.
Deployment Links: If you have deployed your applications (which is highly recommended), include the live URLs for both
the frontend and backend in your submission.
GitHub Workflow Requirements (For Both Repositories)
The following workflow is required for both your frontend and backend repositories:

Create a pull request from a development branch to main (or master).
Ensure all your work is committed and pushed to your development branch first.
Add a detailed description to your pull request summarizing the features implemented.
After review, merge your development branch into main.
Project Presentation
Presentation Date: August 1st, 2025, at 9:00 AM Central Time.
Presentation Length: You will have 30 minutes to present your project.
Be prepared to answer questions from instructors and peers.
Do not include any code in your project that you do not understand.
Presentation Flow
Your presentation must follow this specific structure:

Introduction & ERD: Begin by introducing your application. Explain what problem it solves and what it does. Present your
backend’s Entity-Relationship Diagram (ERD) to explain your data model.
Live UI Demonstration: Use your live, deployed UI to walk through the entire user flow of your application. Demonstrate
all key features from the user’s perspective.
Frontend Code Deep Dive: After the demo, open your frontend codebase. Discuss the key architectural decisions,
components, state management strategies, and any libraries you used.
Backend Code Deep Dive: Finally, switch to your backend codebase. Explain your API structure, service layer logic,
entity relationships, and how you handled data persistence and security.
Important Notes
Check Requirements Thoroughly: Review the detailed requirements for both the frontend and backend.
Testing: Verify that all functionality works as expected and that all tests pass.
Code Quality: Your code should be well-organized, properly commented, and follow best practices.
Bonus Points: If you’ve implemented any optional bonus features, be sure to highlight these in your presentation and
your README files.
Good luck with your project submission! 🚀

Final Capstone Project
Project Rubric
This rubric will be used to evaluate your final capstone project. The project is worth a total of 100 points for core
requirements, with an additional 20 bonus points available. The grading is divided equally between the backend and
frontend portions of the application.

Part 1: Backend Application (60 points total)

1. Project Setup & Git Workflow (5 points)
   (2 points) Repository & README: Backend project is in its own GitHub repository with a comprehensive README.md that
   includes a clear ERD.
   (3 points) Git Best Practices: Consistent use of a development branch. Commits are frequent with meaningful messages.
   A final pull request is made from development to main.
2. Database & JPA Entities (15 points)
   (5 points) Entity Creation: A logical set of entities is created for the application’s domain, including a User
   entity.
   (8 points) Entity Relationships: The data model correctly implements at least one one-to-one, two one-to-many, and
   one many-to-many relationship.
   (2 points) Data Constraints: Appropriate annotations (@Column(nullable=false, unique=true), etc.) are used to enforce
   data integrity.
3. API Architecture & Services (15 points)
   (5 points) Layered Architecture: The application is clearly separated into Controller, Service, and Repository
   layers.
   (5 points) Service Layer Logic: Business logic is correctly implemented within the service layer, demonstrating a
   clear separation of concerns.
   (5 points) DTOs: Data Transfer Objects (DTOs) are used for all API requests and responses to decouple the API from
   the database schema.
4. API Functionality & Error Handling (10 points)
   (4 points) CRUD & Auth Endpoints: Full CRUD operations for core entities and authentication endpoints (/register,
   /login) are fully implemented and functional.
   (3 points) Request Validation: Spring Validation (@Valid) is used on DTOs to validate incoming requests.
   (3 points) Global Exception Handling: A centralized exception handler (@RestControllerAdvice) and custom exceptions
   are used to provide consistent, meaningful error responses.
5. Unit Testing (5 points)
   (5 points) Service & Controller Tests: Comprehensive unit tests are written for the service and controller layers for
   at least one core entity, using Mockito to mock dependencies and testing both happy and sad paths.
   Backend Bonus Requirements (+10 additional points)
   Deployment (+3 points): The Spring Boot API is deployed to a cloud service and is publicly accessible.
   JWT Authentication (+3 points): The API is secured using JSON Web Tokens.
   Data Seeding (+1 point): The database is seeded with sample data on startup.
   AOP Logging (+1 point): Aspect-Oriented Programming is used for logging or another cross-cutting concern.
   Custom JPQL/Pagination/Enums (+2 points): Demonstrates use of at least two of these: custom JPQL queries, pagination
   on an endpoint, or Enums for fixed-value fields.
   Part 2: Frontend Application (60 points total)
1. Project Setup & Git Workflow (5 points)
   (2 points) Repository & README: Frontend project is in its own GitHub repository with a comprehensive README.md.
   (3 points) Git Best Practices: Consistent use of a development branch. Commits are frequent with meaningful messages.
   A final pull request is made from development to main.
2. UI/UX and Responsive Design (10 points)
   (4 points) Professional & Polished UI: The application has a modern, professional, and aesthetically pleasing design.
   The UI is intuitive and user-friendly.
   (4 points) Mobile-First Responsive Design: The UI is fully responsive and looks great on mobile, tablet, and desktop
   screens, using modern CSS like Flexbox or Grid.
   (2 points) Semantic & Accessible HTML: The application uses semantic HTML5 elements correctly, and basic
   accessibility principles are followed.
3. Core Features & Functionality (20 points)
   (7 points) Landing Page & Auth Flow: All required landing page sections are present. The sign-up and login forms are
   functional, handle errors, and redirect correctly. Navbar shows conditional elements based on auth status.
   (8 points) Main Application (CRUD): Full CRUD functionality is implemented. The UI updates instantly on data changes
   without a page refresh.
   (5 points) Routing & Protected Routes: react-router-dom is used for all client-side navigation. Protected routes
   correctly redirect unauthenticated users to the login page. Authenticated users are redirected away from login/signup
   pages.
4. React Architecture & State Management (10 points)
   (4 points) Component Structure & CSS Modules: The project is well-organized into pages and components folders. CSS
   Modules are used for component-scoped styling.
   (3 points) State Management: Correctly uses useState for local state and demonstrates “lifting state up” for shared
   state.
   (3 points) Global State (Context API): The Context API is used for global state. A ThemeContext for a light/dark mode
   toggle is required.
5. API Communication & Testing (5 points)
   (2 points) AI Feature Integration: The Pollinations.ai text generation API is integrated into the application in a
   meaningful way.
   (3 points) Unit & Integration Testing: Vitest is used to write meaningful tests for all core pages and reusable
   components, including tests for rendering, user events, and async (loading/success/error) states. All tests pass.
   Frontend Bonus Requirements (+10 additional points)
   Deployment (+3 points): The React application is deployed to a static hosting service (e.g., Netlify, Vercel) and is
   publicly accessible.
   Client-Side JWT Handling (+3 points): The frontend correctly stores a JWT on login and sends it in the Authorization
   header for protected API requests.
   Advanced State Management (+2 points): A dedicated AuthContext is used to manage user authentication state globally.
   Advanced Error Handling (+2 points): A global, reusable ErrorModal component is implemented to display API errors to
   the user in a clean and consistent way.
   Final Score
   Backend Core: __ / 50
   Frontend Core: __ / 50
   Bonus Points: __ / 20
   Total: __ / 100+
   Grading Scale
   Exceeds Expectations: 90+ points
   Meets Expectations: 70-89 points
   Partially Meets Expectations: 50-69 points
   Does Not Meet Expectations: Below 50 points

-------- MVP Plan ------------

My idea: a full stack interview preparation app where users could paste in job descriptions and have AI generate mock
interview questions for the user to practise answering and then getting feedback from the AI.
For the first phase the MVP could be simply a functional prototype that can deliver the Core User Flow:
Before the app runs, data seeder should populate the database with some mock data (such as mock users, mock profiles,
mock job applications, mock interview questions, mock user responses (sample answers), mock AI feedback, etc)
Job seeker creates account, logs in and pastes a job description (text)
Pollinations.ai generates 3 relevant interview questions based on the job description:
The app should first try to use AI API by passing the job description as part of the prompt and asking AI to come up
with 3 interview questions that are relevant to this job and most likely come up on the actual interview. For now let’s
ask for 1 behavioral question, 1 technical question and 1 situational question. For example, a prompt could be like “For
the following job description, please come up with 3 mock interview questions (ideally 1 behavioral, 1 technical and 1
situational) assuming you are a career coach. {jobDescription}.”
If for some reason AI fails to respond or it is taking too long, the app should fall back the mock sample questions we
have pre-populated in the database at the beginning.
User practices typing responses under each question
There is a textarea under each question for the user to type in their answer.
There is also a submit button the user could use to submit their answer to AI.
[Bonus] a “Clear” button next to “Submit” to clear the textarea content.
Basic AI feedback display:
Once the user finishes typing in the textarea and hit “Submit”, the app takes the user response and puts that in another
prompt and delivers it to AI, asking it to evaluate this response with respect to the question and provide feedback as a
career coach. For example, a prompt could be like “For the interview question {InterviewQuestionText}, how would you
evaluate and provide feedback to the answer {userResponseText} assuming you are a career coach?”.
Again if AI fails to respond here, display the mock feedback in the database under each question and user response.
Question history and progress tracking:
There is a page for the user to view their previous job applications, the sample questions for each application, their
answers, AI feedback, etc.
Under each job posting, there could be some progress indicators showing things like “Mock Interview Completed”, “Haven’t
Practiced Yet”, “In Progress”, etc. These can just be mock data.
Simple profile management:
A profile page for each user to view (and possibly edit) their profile information.
At any point, any errors are handled gracefully with clear error messages displayed to the user.
Make sure the MVP meets all the requirements in project_instructions.txt.
