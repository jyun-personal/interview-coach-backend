package com.interviewcoach.config;

import com.interviewcoach.enums.QuestionType;
import com.interviewcoach.enums.UserRole;
import com.interviewcoach.model.*;
import com.interviewcoach.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;
    @Autowired
    private JobApplicationQuestionRepository jobApplicationQuestionRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;
    @Autowired
    private AiFeedbackRepository aiFeedbackRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Helper records for structured seeding data
    private record UserSeedData(String username, String email, String password, String firstName, String lastName,
                                String bio, String resumeText, UserRole role) {
    }

    private record JobAppSeedData(String title, String description, int weeksAhead, String status,
                                  List<QuestionResponseFeedbackSeedData> questionsAndResponses) {
    }

    private record QuestionResponseFeedbackSeedData(String questionText, QuestionType questionType,
                                                    String userResponseText, String aiFeedbackText,
                                                    int aiFeedbackScore) {
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("Seeding initial data...");

            // --- User 1: Tina Brown (Software Developer) ---
            UserSeedData tinaData = new UserSeedData(
                    "tinabrown", "tina.brown@example.com", "password123", "Tina", "Brown",
                    "Passionate Software Developer with expertise in React, Spring Boot, and cloud technologies. Eager to contribute to innovative projects.",
                    "Frontend: React, TypeScript, Redux. Backend: Java, Spring Boot, REST APIs. Database: PostgreSQL, MongoDB. Cloud: AWS (EC2, S3, RDS). Tools: Git, Docker, Jira.",
                    UserRole.JOB_SEEKER
            );
            List<JobAppSeedData> tinaJobApps = new ArrayList<>();
            tinaJobApps.add(new JobAppSeedData(
                    "React Developer",
                    """
                            About the Role:
                            We are looking for a talented React Developer to join our dynamic team. You will be responsible for developing and maintaining user-facing applications, ensuring high performance and responsiveness.
                            
                            Responsibilities:
                            *   Develop new user-facing features using React.js and TypeScript.
                            *   Build reusable components and front-end libraries for future use.
                            *   Optimize components for maximum performance across a vast array of web-capable devices and browsers.
                            *   Collaborate with backend developers and UX/UI designers.
                            
                            Requirements:
                            *   Proven experience with React.js, including Hooks and Context API.
                            *   Strong proficiency in JavaScript, HTML5, and CSS3.
                            *   Familiarity with RESTful APIs.
                            *   Experience with version control tools like Git.
                            *   Bachelor's degree in Computer Science or related field.
                            """,
                    2, "PENDING",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "What are the common types of variables in Java and what are their differences?",
                                    QuestionType.TECHNICAL,
                                    "In Java, common variable types include primitive types like int, float, double, boolean, char, byte, short, and long, which store simple values directly. Reference types, such as String, arrays, and custom objects, store references to objects in memory. The key difference is how they store data and how memory is allocated for them. Primitive types are stored on the stack, while reference types are stored on the heap with a reference on the stack.",
                                    """
                                            This is a solid foundational answer that clearly distinguishes between primitive and reference types. You've correctly identified several primitive types and highlighted their storage location. To enhance this, consider adding an example of each type's declaration and usage. Also, briefly touch upon wrapper classes and their role in bridging the gap between primitive and reference types, which demonstrates a more complete understanding. Overall, a good start, showing strong theoretical knowledge.
                                            """,
                                    8
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Describe a time you had to learn a new technology quickly. How did you approach it?",
                                    QuestionType.BEHAVIORAL,
                                    "Last year, I was assigned a project that required using GraphQL, which I had no prior experience with. I started by taking an online course to understand the fundamentals, then I built a small proof-of-concept application to get hands-on experience. I also actively participated in team discussions and asked senior developers for guidance. Within two weeks, I was contributing effectively to the GraphQL part of the project.",
                                    """
                                            Excellent use of the STAR method! You clearly outlined the Situation, Task, Action, and Result. This response demonstrates initiative, a structured learning approach, and effective collaboration. To make it even stronger, quantify the impact of your contribution or the complexity of the project, if possible. For instance, "contributing effectively" could become "successfully implemented the API, reducing data over-fetching by X%." This adds a layer of measurable achievement to your narrative.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "You discover a critical bug in production on Friday afternoon. The team is about to log off. What steps do you take?",
                                    QuestionType.SITUATIONAL,
                                    "I would immediately notify my team lead and relevant stakeholders about the critical bug. Then, I'd assess the impact and severity, try to identify the root cause quickly, and determine if a hotfix is feasible. If it requires immediate attention, I'd stay online and coordinate with available team members, prioritizing a fix over the weekend if necessary to minimize impact.",
                                    """
                                            This is a well-structured and thoughtful response to a high-pressure scenario. You correctly prioritize communication, assessment, and action. Your willingness to stay online and coordinate shows strong commitment and responsibility. For further improvement, you could explicitly mention checking logs for clues, having a rollback plan, or documenting the incident for a post-mortem, showcasing a more holistic approach to incident management. Your composure in such a scenario comes across well.
                                            """,
                                    8
                            )
                    )
            ));
            tinaJobApps.add(new JobAppSeedData(
                    "Full Stack Engineer",
                    """
                            Role Overview:
                            We're seeking a versatile Full Stack Engineer to work across our entire software stack, from user interfaces to backend services and databases. This role requires strong problem-solving skills and a passion for delivering high-quality software.
                            
                            Key Responsibilities:
                            *   Design, develop, and maintain web applications using modern frameworks.
                            *   Build robust and scalable RESTful APIs.
                            *   Collaborate on database design and optimization.
                            *   Participate in code reviews and contribute to architectural decisions.
                            
                            Qualifications:
                            *   Proficiency in Java, Spring Boot, React, and PostgreSQL.
                            *   Experience with cloud platforms like AWS or Azure.
                            *   Understanding of microservices architecture.
                            *   Strong communication and teamwork skills.
                            """,
                    1, "IN_PROGRESS",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "What is dependency injection and why is it important in Spring Boot?",
                                    QuestionType.TECHNICAL,
                                    "Dependency Injection (DI) is a design pattern where the dependencies of a class are provided to it externally rather than the class creating them itself. In Spring Boot, DI is crucial because it promotes loose coupling between components, making the application more modular, testable, and maintainable. It's facilitated by the Spring IoC container, which manages bean lifecycles and injects dependencies automatically using annotations like `@Autowired`.",
                                    """
                                            This is a very good explanation of Dependency Injection and its significance in Spring Boot. You clearly articulated the 'what' and the 'why', including key benefits like loose coupling and testability. To further refine this answer, you could briefly mention the different types of dependency injection (constructor, setter, field) and perhaps a small, conceptual example of how it looks in code. This would add practical depth to your theoretical understanding. Your answer is succinct yet informative.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Tell me about a time you had to adapt to a significant change in project requirements.",
                                    QuestionType.BEHAVIORAL,
                                    "In my previous role, mid-project, our client decided to pivot the core functionality due to market feedback. Initially, it was challenging because we had to discard significant work. I took the lead in re-evaluating the new requirements, facilitating brainstorming sessions with the team, and helping create a revised project plan. We managed to deliver the new feature set within the extended deadline, and the client was very pleased with our adaptability.",
                                    """
                                            This response effectively uses the STAR method to demonstrate your adaptability. You've clearly shown your proactive role in leading the team through a challenging pivot. To make this even more compelling, try to quantify the scale of the change or the impact of your successful adaptation. For instance, "significant work" could be quantified as "discarded two weeks of development effort." Mentioning the lessons learned from this experience would also add depth and reflection to your answer.
                                            """,
                                    8
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "How do you handle disagreements within your team regarding technical approaches?",
                                    QuestionType.SITUATIONAL,
                                    "When technical disagreements arise, my first step is to listen actively to understand all perspectives and the rationale behind each proposed solution. I encourage open discussion, focusing on the pros and cons of each approach in relation to project goals and constraints. If consensus isn't reached, I propose a time-boxed experiment or a small proof-of-concept. Ultimately, if a decision is needed and consensus remains elusive, I would defer to the technical lead or architect.",
                                    """
                                            This is a thoughtful and mature response that highlights strong collaborative and problem-solving skills. You emphasize understanding, open discussion, and practical solutions like PoCs, which are excellent approaches. Your willingness to defer to a higher authority when necessary shows good judgment and respect for team hierarchy. Perhaps you could add a brief real-world example where you applied these principles to illustrate your experience and reinforce your claims. This would ground your answer in practical application.
                                            """,
                                    9
                            )
                    )
            ));
            tinaJobApps.add(new JobAppSeedData(
                    "Backend Engineer",
                    """
                            About Us:
                            We're a fast-growing tech company seeking a skilled Backend Engineer to build and scale our core services. You will be responsible for designing robust APIs, ensuring data integrity, and optimizing system performance.
                            
                            Responsibilities:
                            *   Develop high-performance, fault-tolerant backend services in Java.
                            *   Design and manage database schemas for new and existing features.
                            *   Implement secure and efficient authentication and authorization mechanisms.
                            *   Participate in the full software development lifecycle, from design to deployment.
                            
                            Requirements:
                            *   Extensive experience with Java and Spring Boot.
                            *   Proficiency in designing and consuming RESTful APIs.
                            *   Strong knowledge of relational databases (e.g., PostgreSQL).
                            *   Experience with unit and integration testing.
                            *   Familiarity with message queues (Kafka, RabbitMQ) is a plus.
                            """,
                    3, "COMPLETED",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "Explain the ACID properties in database transactions.",
                                    QuestionType.TECHNICAL,
                                    "ACID stands for Atomicity, Consistency, Isolation, and Durability. Atomicity ensures that a transaction is treated as a single, indivisible unit; it either completes entirely or doesn't happen at all. Consistency guarantees that a transaction brings the database from one valid state to another. Isolation ensures that concurrent transactions execute independently without interfering with each other. Durability guarantees that once a transaction has been committed, it will remain permanently recorded, even in the event of system failures.",
                                    """
                                            This is a textbook-perfect definition of ACID properties, clearly explaining each component. Your understanding of fundamental database concepts is evident. To further impress, you could provide a quick, simple example of a transaction (e.g., a bank transfer) and briefly explain how each ACID property applies to it. This demonstrates not just memorization, but the ability to apply theoretical knowledge to real-world scenarios. Very well done on defining the core concepts precisely.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Tell me about a project where you had to debug a complex performance issue.",
                                    QuestionType.BEHAVIORAL,
                                    "In my last role, our main API endpoint started experiencing high latency after a recent deployment. Users were complaining about slow response times. I used profiling tools like Java Flight Recorder and VisualVM to identify bottlenecks in our code, specifically an inefficient database query and an N+1 select problem. I then optimized the query, implemented caching, and refactored the data retrieval logic. This reduced the average response time by 60%, significantly improving user experience.",
                                    """
                                            This is a strong response that uses the STAR method effectively to showcase your debugging and optimization skills. You identified the problem (high latency), the tools used (profiling), and specific actions taken (query optimization, caching). Most importantly, you quantified the result (60% reduction in response time), which is excellent. Perhaps you could add a brief reflection on what you learned or how you'd prevent similar issues in the future to round out the answer. Overall, a highly impactful response.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "How do you ensure the security of the APIs you build?",
                                    QuestionType.TECHNICAL,
                                    "I ensure API security by implementing strong authentication mechanisms like OAuth2 or JWT, and robust authorization controls based on roles or permissions. I always validate and sanitize all input to prevent injection attacks and use HTTPS for secure communication. Additionally, I apply rate limiting to prevent brute-force attacks and follow the principle of least privilege when designing user access. Regular security audits and using security frameworks like Spring Security are also key.",
                                    """
                                            This is a comprehensive answer covering multiple facets of API security. You've hit on key areas like authentication, authorization, input validation, encryption (HTTPS), and rate limiting. Your mention of the principle of least privilege and security frameworks demonstrates a mature understanding of secure development practices. To make it even more compelling, you might briefly explain one specific vulnerability (e.g., SQL Injection) and how one of your mentioned practices directly mitigates it. This adds practical depth to your theoretical knowledge.
                                            """,
                                    9
                            )
                    )
            ));
            seedUserWithJobApps(tinaData, tinaJobApps);

            // --- User 2: David Smith (Truck Driver) ---
            UserSeedData davidData = new UserSeedData(
                    "davidsmith", "david.smith@example.com", "password456", "David", "Smith",
                    "Experienced Commercial Truck Driver with a clean driving record and a commitment to safe and timely delivery. Proficient in various freight types and routes.",
                    "CDL Class A, HAZMAT endorsement. 10+ years regional and OTR experience. Familiar with ELD and DOT regulations. Strong communication and navigation skills.",
                    UserRole.JOB_SEEKER
            );
            List<JobAppSeedData> davidJobApps = new ArrayList<>();
            davidJobApps.add(new JobAppSeedData(
                    "Local Delivery Driver",
                    """
                            Job Summary:
                            Seeking a dedicated Local Delivery Driver to transport goods within a 150-mile radius. This role requires adherence to delivery schedules and safe operation of commercial vehicles.
                            
                            Duties & Responsibilities:
                            *   Safely operate commercial truck (e.g., box truck, straight truck).
                            *   Load and unload freight using appropriate equipment.
                            *   Maintain accurate delivery logs and vehicle inspection reports.
                            *   Communicate effectively with dispatch and customers.
                            
                            Requirements:
                            *   Valid Class A or B CDL with clean driving record.
                            *   Minimum 2 years commercial driving experience.
                            *   Ability to lift up to 50 lbs.
                            *   Knowledge of local delivery routes.
                            """,
                    1, "COMPLETED",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "How do you ensure your vehicle is safe and compliant with DOT regulations before starting a trip?",
                                    QuestionType.TECHNICAL,
                                    "Before every trip, I perform a thorough pre-trip inspection following DOT guidelines. This includes checking tires for proper inflation and damage, verifying all lights and signals are working, inspecting brakes, fluid levels, and emergency equipment. I also review my Electronic Logging Device (ELD) to ensure it's functioning correctly and my hours of service are compliant. Any issues found are immediately reported and resolved before departure.",
                                    """
                                            This is a very comprehensive and strong answer. You've covered key aspects of a pre-trip inspection and explicitly mentioned DOT compliance and ELD, which are critical for truck driving roles. Your systematic approach demonstrates professionalism and attention to detail. For added depth, you could briefly explain the importance of checking cargo securement as part of the pre-trip, especially for different types of freight. This would reinforce your commitment to safety beyond just vehicle mechanics.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Describe a time you had to deal with an unexpected delay on your route. How did you manage it?",
                                    QuestionType.BEHAVIORAL,
                                    "On a recent delivery, I encountered an unexpected road closure due to an accident, which significantly delayed my route. My first action was to immediately contact dispatch to inform them of the situation and get advice on alternative routes. While waiting for their guidance, I used my navigation system to identify possible detours and assessed the impact on my remaining deliveries. I updated affected customers with revised ETAs, and by maintaining clear communication, we managed to minimize disruption and fulfill all deliveries, albeit slightly later than planned.",
                                    """
                                            This is an excellent response showcasing your problem-solving and communication skills under pressure. You clearly demonstrated proactive steps (contacting dispatch, identifying detours) and, importantly, customer communication. This highlights professionalism and accountability. To elevate this further, you could briefly reflect on what you learned from that specific incident to prevent similar issues in the future, if applicable, or how you now incorporate contingency planning into your routine. This adds a layer of continuous improvement.
                                            """,
                                    8
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "What would you do if you realized you were behind schedule and risking a violation of HOS regulations?",
                                    QuestionType.SITUATIONAL,
                                    "If I found myself falling behind schedule to the point of risking a Hours of Service (HOS) violation, my immediate priority would be safety and compliance. I would first communicate with dispatch to explain the situation and discuss options. These might include finding a safe and legal place to pull over and take my mandatory break or rest, even if it means missing a delivery window. I would never compromise safety or violate HOS rules. The goal is to ensure compliance and coordinate with the team for a solution, even if it means adjusting delivery plans.",
                                    """
                                            This is a strong and responsible answer that correctly prioritizes safety and compliance with HOS regulations over meeting a potentially impossible deadline. Your immediate action of communicating with dispatch and exploring safe options demonstrates good judgment. It's crucial to convey that you understand the seriousness of HOS rules. You could briefly mention how you plan routes to proactively avoid such situations or utilize technology (ELD warnings) to stay compliant, which would show foresight. Overall, a very professional and safety-conscious response.
                                            """,
                                    9
                            )
                    )
            ));
            davidJobApps.add(new JobAppSeedData(
                    "Regional CDL-A Driver",
                    """
                            Position Summary:
                            Seeking a reliable CDL-A driver for regional routes, ensuring timely and safe transport of goods. This position offers consistent routes and competitive pay.
                            
                            Key Responsibilities:
                            *   Operate Class A commercial motor vehicle safely and efficiently.
                            *   Perform pre-trip and post-trip inspections.
                            *   Adhere to all DOT regulations, including Hours of Service.
                            *   Maintain communication with dispatch throughout assigned routes.
                            *   Secure loads properly to prevent shifting or damage.
                            
                            Requirements:
                            *   Valid Class A CDL.
                            *   Minimum 3 years regional driving experience.
                            *   Clean driving record.
                            *   Ability to perform basic vehicle maintenance checks.
                            *   Experience with various trailer types (dry van, reefer).
                            """,
                    3, "IN_PROGRESS",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "How do you handle aggressive drivers or road rage when you're on the road?",
                                    QuestionType.BEHAVIORAL,
                                    "Safety is my top priority. If I encounter an aggressive driver, I maintain calm and avoid engaging. I focus on defensive driving, increasing my following distance, and changing lanes if necessary to create space. I'd never escalate the situation. If the behavior is extreme or dangerous, I'd safely pull over and report it to law enforcement or dispatch, as distracted or aggressive driving is a serious hazard to everyone.",
                                    """
                                            This is an excellent answer that prioritizes safety and demonstrates a mature approach to handling challenging road situations. Your emphasis on defensive driving and avoiding engagement is exactly what an employer wants to hear. You also correctly mention the appropriate escalation (reporting to authorities), which shows responsibility. No real improvements needed here; this response is concise, professional, and highlights critical safety-first thinking. Very strong.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "What measures do you take to prevent cargo damage during transit?",
                                    QuestionType.TECHNICAL,
                                    "Preventing cargo damage starts with proper loading and securement. I always ensure the cargo is evenly distributed to maintain balance and avoid exceeding axle weight limits. I use appropriate securement devices like straps, chains, and dunnage, ensuring they are tightened correctly and checked periodically during transit. Understanding the type of freight (e.g., fragile vs. sturdy) helps me choose the best securement method. A thorough pre-trip inspection of the cargo's securement is also critical.",
                                    """
                                            This is a very practical and detailed answer. You covered key aspects from proper loading to securement techniques and in-transit checks. Your awareness of cargo distribution and using appropriate securement devices demonstrates a strong understanding of best practices. For a more comprehensive response, you could briefly touch upon environmental factors that might affect cargo (e.g., temperature for refrigerated goods) if applicable, but for general cargo, your answer is very solid. This shows a commitment to protecting assets.
                                            """,
                                    8
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Describe a time you received constructive criticism. How did you respond?",
                                    QuestionType.BEHAVIORAL,
                                    "In a previous role, I received feedback that I could improve my communication with dispatch regarding my estimated arrival times. Initially, I felt a bit defensive, but I quickly realized the value in the feedback. I made a conscious effort to provide more frequent updates, especially if there were unexpected delays. This improved our operational efficiency and reduced stress for dispatch and customers. I learned that feedback is a gift for growth.",
                                    """
                                            This is a very positive and insightful response. You effectively demonstrated your ability to receive feedback gracefully, learn from it, and implement changes for improvement. Your acknowledgment of initial defensiveness but quick pivot to understanding and action is mature. The result (improved operational efficiency and reduced stress) is a clear positive outcome. This answer perfectly showcases self-awareness and a growth mindset, which are highly valued qualities in any role. Well articulated.
                                            """,
                                    9
                            )
                    )
            ));
            davidJobApps.add(new JobAppSeedData(
                    "Long Haul Truck Driver (OTR)",
                    """
                            Opportunity Awaits:
                            Join our team as an Over-the-Road (OTR) Truck Driver, delivering freight across multiple states. This position offers excellent pay, benefits, and a chance to see the country.
                            
                            Core Responsibilities:
                            *   Safely transport goods over long distances.
                            *   Adhere to all federal and state driving regulations.
                            *   Perform routine vehicle maintenance checks and manage logbooks.
                            *   Represent the company professionally to clients and at truck stops.
                            
                            Requirements:
                            *   Class A CDL with 5+ years OTR experience.
                            *   Experience with electronic logging devices (ELDs).
                            *   Strong time management and organizational skills.
                            *   Ability to work independently for extended periods.
                            """,
                    4, "PENDING",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "How do you manage your sleep schedule and stay alert during long hauls?",
                                    QuestionType.BEHAVIORAL,
                                    "Maintaining a consistent sleep schedule is critical for long-haul driving safety. I prioritize getting at least 7-8 hours of quality sleep during my mandated rest periods. I plan my routes to ensure I can stop at safe and quiet rest areas. During my driving shifts, I rely on proper hydration, healthy snacks, and short, planned breaks to stay alert. I avoid caffeine dependence and listen to my body's signals, pulling over if I ever feel drowsy, as safety is paramount.",
                                    """
                                            This is a highly responsible and well-thought-out answer. You emphasized key safety practices like consistent sleep, planned breaks, and listening to your body, which is exactly what employers want to hear. Your commitment to avoiding drowsiness and prioritizing safety above all else comes across very strongly. For an even more robust answer, you could briefly mention proactive measures like using sleep apps or ensuring a dark, quiet environment during rest to maximize sleep quality. Overall, a very professional and safety-conscious response.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "What strategies do you use for efficient route planning and navigation, especially for new or unfamiliar areas?",
                                    QuestionType.TECHNICAL,
                                    "For route planning, I utilize specialized truck-specific GPS systems like Garmin Dezl, which factor in weight limits, bridge heights, and restricted routes. Before starting, I review the entire route on a larger map and cross-reference with any dispatch instructions or known road conditions. For unfamiliar areas, I'll often look at satellite views or street view online to anticipate tricky turns or delivery points. I also keep physical maps as a backup and allow extra time for unexpected deviations. This proactive approach minimizes surprises and ensures efficiency.",
                                    """
                                            This is a very practical and detailed answer that showcases your expertise in route planning. You correctly highlighted the use of truck-specific GPS, cross-referencing with dispatch, and even advanced techniques like reviewing satellite views. Your emphasis on proactivity and having backup plans (physical maps) demonstrates thoroughness and foresight. This answer perfectly conveys your ability to navigate complex logistics efficiently and safely. A strong and confident response.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Describe a time you had to deal with a difficult delivery situation (e.g., tight space, uncooperative recipient).",
                                    QuestionType.SITUATIONAL,
                                    "On one occasion, I arrived at a delivery site that was a very tight, crowded urban alley, much tighter than anticipated. The recipient was also uncooperative and insisted I block traffic. Instead of escalating, I calmly explained the safety regulations and the need for a clear lane. I then worked with him to identify a safer, albeit slightly less convenient, spot nearby where I could maneuver without blocking traffic or risking damage. By maintaining professionalism and focusing on a safe solution, I completed the delivery without incident, even though it took extra time.",
                                    """
                                            This is an excellent situational response that highlights your calm demeanor, problem-solving skills, and commitment to safety/compliance in challenging conditions. You effectively de-escalated a potentially difficult interaction and found a practical solution, which is highly valued in logistics. Your focus on professionalism and safety in the face of an uncooperative recipient is commendable. No specific improvements are needed; this response perfectly illustrates your ability to handle real-world complexities with grace.
                                            """,
                                    9
                            )
                    )
            ));
            seedUserWithJobApps(davidData, davidJobApps);

            // --- User 3: Lisa Jones (Registered Nurse) ---
            UserSeedData lisaData = new UserSeedData(
                    "lisajones", "lisa.jones@example.com", "password789", "Lisa", "Jones",
                    "Compassionate Registered Nurse with 5+ years of experience in critical care. Dedicated to patient advocacy and delivering high-quality, holistic care.",
                    "RN, BSN. Certifications: BLS, ACLS. Experience in ICU, ER, Telemetry. Proficient in EMR systems (Epic, Cerner). Strong IV skills, medication administration, patient assessment.",
                    UserRole.JOB_SEEKER
            );
            List<JobAppSeedData> lisaJobApps = new ArrayList<>();
            lisaJobApps.add(new JobAppSeedData(
                    "Hospital Registered Nurse - ICU",
                    """
                            Position Overview:
                            Seeking an experienced Registered Nurse for our Intensive Care Unit. This role provides critical care to patients with life-threatening conditions, requiring a high degree of skill and quick decision-making.
                            
                            Key Responsibilities:
                            *   Administer medications and treatments as prescribed.
                            *   Monitor vital signs and patient progress.
                            *   Collaborate with physicians and interdisciplinary teams.
                            *   Educate patients and their families on care plans.
                            
                            Qualifications:
                            *   Valid RN license in [State Name].
                            *   BSN degree preferred.
                            *   Minimum 2 years ICU experience.
                            *   BLS and ACLS certification.
                            *   Strong critical thinking and communication skills.
                            """,
                    1, "PENDING",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "Describe a time you had to deal with a difficult patient or family member. How did you resolve the situation?",
                                    QuestionType.BEHAVIORAL,
                                    "I once had a patient's family member who was very agitated and demanding, questioning every aspect of our care. I acknowledged their concerns, listened actively without interruption, and empathized with their stress. I then calmly explained the care plan, reinforced our commitment to the patient's well-being, and offered to bring in the charge nurse for further discussion if they still had doubts. By remaining professional and empathetic, I de-escalated the situation, and we were able to focus on providing optimal patient care.",
                                    """
                                            This is an excellent response that demonstrates strong communication, empathy, and de-escalation skills. You effectively outlined your approach using active listening and empathy, which are crucial in patient care. Your willingness to involve a supervisor when necessary also shows good judgment and teamwork. To enhance this, you could briefly mention how this experience has shaped your approach to patient advocacy or family interactions in general. Overall, a highly professional and compassionate response.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "How do you prioritize patient care when you have multiple patients with urgent needs?",
                                    QuestionType.SITUATIONAL,
                                    "Prioritization in critical care is constant. My approach involves a rapid assessment of all patients, identifying immediate life threats first (ABCs: Airway, Breathing, Circulation). I then consider pain management, new orders, and any patient requests. I use tools like my brain, my nursing judgment, and a prioritized mental checklist. Clear communication with my team, including delegating appropriate tasks to nursing assistants, is also essential. This allows me to address the most critical needs while ensuring all patients receive timely care.",
                                    """
                                            This is a very strong and practical answer that clearly outlines your prioritization strategy in a high-pressure environment. You correctly identified immediate life threats as the first priority and mentioned effective tools like mental checklists and delegation. This demonstrates excellent critical thinking and resource management skills. To make this answer even more impactful, you could briefly describe a specific instance where this prioritization strategy saved time or improved patient outcomes. This would ground your theoretical knowledge in practical experience.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "What are the common types of IV fluids used in clinical practice and their primary indications?",
                                    QuestionType.TECHNICAL,
                                    "Common IV fluids include crystalloids like Normal Saline (0.9% NaCl) and Lactated Ringer's, used for fluid resuscitation and electrolyte replacement. Colloids, such as Albumin, are used for plasma volume expansion in situations like hypovolemic shock. Dextrose solutions (e.g., D5W) provide hydration and calories. Each fluid type has specific indications based on a patient's fluid and electrolyte balance, medical condition, and prescribed treatment plan. Understanding osmolarity is key to proper selection.",
                                    """
                                            This is a good foundational answer providing a clear overview of common IV fluid types and their basic indications. You've correctly identified crystalloids, colloids, and dextrose solutions, which shows solid technical knowledge. To further impress, you could elaborate slightly on contraindications for certain fluids or briefly touch upon the concept of isotonic, hypotonic, and hypertonic solutions and their clinical implications. This would demonstrate a deeper physiological understanding relevant to safe medication administration.
                                            """,
                                    8
                            )
                    )
            ));
            lisaJobApps.add(new JobAppSeedData(
                    "Clinic Nurse - Family Practice",
                    """
                            About the Clinic:
                            Our busy family practice clinic is seeking a compassionate and efficient Clinic Nurse to provide comprehensive patient care in an outpatient setting.
                            
                            Responsibilities Include:
                            *   Patient intake, vital signs, and medical history documentation.
                            *   Assisting physicians with examinations and minor procedures.
                            *   Administering immunizations and other injections.
                            *   Patient education on health management and preventative care.
                            *   Managing electronic medical records (EMR).
                            
                            Candidate Requirements:
                            *   Active RN license.
                            *   Minimum 1 year experience in an outpatient or clinic setting.
                            *   Strong organizational and communication skills.
                            *   Familiarity with EMR systems.
                            """,
                    2, "IN_PROGRESS",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "How do you ensure accurate medication administration and prevent errors in a busy clinic setting?",
                                    QuestionType.TECHNICAL,
                                    "In a busy clinic, I strictly adhere to the 'Five Rights' of medication administration: right patient, right drug, right dose, right route, right time. I double-check physician orders, verify patient allergies, and perform a three-check system against the medication label, preparing it, and at the bedside/point of administration. I utilize barcode scanning with our EMR system for verification whenever possible. Patient education on their medications is also part of my routine to ensure understanding and adherence, minimizing potential issues.",
                                    """
                                            This is a strong answer that demonstrates a thorough understanding of medication safety protocols. You've correctly highlighted the "Five Rights" and the importance of double-checking and using technology like barcode scanning. Your mention of patient education adds a holistic dimension to your approach. For added depth, you could briefly discuss reporting medication errors (if one were to occur) as part of a continuous quality improvement process. Overall, a highly professional and safety-conscious response.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Tell me about a time you had to educate a patient on a complex medical condition or treatment plan.",
                                    QuestionType.BEHAVIORAL,
                                    "I once had a patient diagnosed with newly onset diabetes who was overwhelmed by the dietary changes and medication regimen. I broke down the information into manageable chunks, using simple language and visual aids. I encouraged questions, actively listened to their concerns, and tailored the education to their lifestyle. I also provided written materials and scheduled follow-up calls to reinforce learning and address new questions. This approach helped the patient feel empowered and confident in managing their condition.",
                                    """
                                            This is an excellent response that showcases your patient education skills. You effectively demonstrated your ability to simplify complex information, use various teaching methods, and ensure patient comprehension. Your emphasis on active listening, tailoring education, and providing follow-up support highlights a patient-centered approach. To make it even more impactful, you could mention a specific positive outcome from your educational intervention (e.g., improved adherence, better glucose control). This adds a quantifiable measure to your success.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "How do you handle a situation where a patient expresses dissatisfaction with their care?",
                                    QuestionType.SITUATIONAL,
                                    "If a patient expresses dissatisfaction, my first action is to listen attentively and empathetically, allowing them to fully voice their concerns without interruption. I apologize for their experience and validate their feelings. I then gather all necessary information to understand the root cause of their dissatisfaction. My goal is to find a resolution, whether it's by clarifying misunderstandings, offering immediate solutions, or escalating the concern to a supervisor if necessary. Follow-up is crucial to ensure their satisfaction.",
                                    """
                                            This is a very professional and compassionate response to a challenging situation. You correctly prioritized active listening, empathy, and seeking to understand the root cause of dissatisfaction. Your methodical approach to finding a resolution, including escalation when appropriate, demonstrates excellent customer service and problem-solving skills. The emphasis on follow-up is also a great touch, showcasing a commitment to patient satisfaction. This response highlights your ability to turn a negative experience into an opportunity for positive engagement.
                                            """,
                                    9
                            )
                    )
            ));
            lisaJobApps.add(new JobAppSeedData(
                    "Home Health Registered Nurse",
                    """
                            Role Description:
                            We are hiring a dedicated Home Health RN to provide skilled nursing care to patients in their homes. This role emphasizes independent judgment and comprehensive patient assessment.
                            
                            Key Responsibilities:
                            *   Conduct initial and ongoing patient assessments.
                            *   Develop and implement individualized care plans.
                            *   Perform wound care, medication management, and other nursing interventions.
                            *   Educate patients and caregivers on disease management and self-care techniques.
                            *   Accurately document all patient visits and care provided in EMR.
                            
                            Requirements:
                            *   Current RN license.
                            *   Minimum 1 year home health or related experience.
                            *   Strong assessment, communication, and organizational skills.
                            *   Valid driver's license and reliable transportation.
                            """,
                    3, "COMPLETED",
                    List.of(
                            new QuestionResponseFeedbackSeedData(
                                    "How do you maintain patient safety and privacy in a home health setting?",
                                    QuestionType.TECHNICAL,
                                    "Maintaining patient safety and privacy in home health requires vigilance. I always ensure the home environment is safe, checking for fall hazards, proper lighting, and clean spaces. For privacy, I strictly adhere to HIPAA regulations, discussing patient information only with authorized individuals and behind closed doors. All documentation is kept secure, usually via a secure electronic tablet. I also ensure hand hygiene protocols are followed rigorously to prevent infection transmission, and I always confirm patient identity before any procedure.",
                                    """
                                            This is a comprehensive answer covering both patient safety (environmental hazards, hygiene) and privacy (HIPAA, secure documentation). You've hit on key points relevant to the unique challenges of a home health setting. To further enhance this, you could briefly mention patient-specific safety measures, such as reviewing medication reconciliation with the patient or ensuring proper disposal of sharps. Overall, your response demonstrates a strong commitment to both legal and practical patient protection.
                                            """,
                                    9
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "Tell me about a time you had to make a critical independent decision in a patient's home.",
                                    QuestionType.BEHAVIORAL,
                                    "I was visiting a home health patient who suddenly developed acute shortness of breath and chest pain, which was not typical for their baseline. I immediately performed a rapid assessment, including vital signs and lung sounds, and quickly suspected a potential cardiac event. Without hesitation, I activated emergency medical services (911), administered oxygen, and positioned the patient for comfort while continuously monitoring them until paramedics arrived. My timely assessment and action stabilized the patient and ensured they received immediate hospital care.",
                                    """
                                            This is an excellent example demonstrating your critical thinking, rapid assessment, and decisive action in an emergency. You effectively used the STAR method to convey a high-stakes situation with a positive outcome. Your ability to act independently and appropriately escalate care is highly commendable. There's little room for improvement here; this response clearly highlights your competence and ability to handle critical situations effectively and professionally. Very strong.
                                            """,
                                    10
                            ),
                            new QuestionResponseFeedbackSeedData(
                                    "How do you manage your time and schedule effectively when you have multiple home visits in a day?",
                                    QuestionType.SITUATIONAL,
                                    "Effective time management is paramount in home health. I start my day by reviewing all patient charts, prioritizing visits based on acuity, new orders, and geographical location to optimize my route. I pre-pack all necessary supplies for each patient. During visits, I aim to be efficient but thorough, documenting concurrently on my tablet to save time. I also factor in buffer time for unexpected delays like traffic or extended patient education. Proactive communication with patients about my ETA helps manage expectations.",
                                    """
                                            This is a very well-structured and practical answer. You've clearly outlined a systematic approach to managing a busy home health schedule, from pre-planning and prioritization to efficient in-visit practices and communication. Your mention of buffering time for unexpected events demonstrates foresight. To make it even more comprehensive, you could briefly discuss how you handle unavoidable schedule changes (e.g., patient cancellations or emergencies) and communicate those impacts. Overall, a highly effective and organized response.
                                            """,
                                    9
                            )
                    )
            ));
            seedUserWithJobApps(lisaData, lisaJobApps);

            System.out.println("Data seeding complete!");
        } else {
            System.out.println("Database already contains data. Skipping seeding.");
        }
    }

    // --- Helper Methods ---

    private void seedUserWithJobApps(UserSeedData userData, List<JobAppSeedData> jobAppsData) {
        User newUser = User.builder()
                .username(userData.username())
                .email(userData.email())
                .passwordHash(passwordEncoder.encode(userData.password()))
                .role(userData.role())
                .build();
        newUser = userRepository.save(newUser);

        Profile newProfile = new Profile(newUser, userData.firstName(), userData.lastName());
        newProfile.setBio(userData.bio());
        newProfile.setResumeText(userData.resumeText());
        profileRepository.save(newProfile);
        newUser.setProfile(newProfile); // Link back

        for (JobAppSeedData jobAppInfo : jobAppsData) {
            JobApplication jobApp = JobApplication.builder()
                    .title(jobAppInfo.title())
                    .description(jobAppInfo.description())
                    .user(newUser)
                    .applicationDate(OffsetDateTime.now())
                    .expectedInterviewDate(LocalDate.now().plusWeeks(jobAppInfo.weeksAhead()))
                    .status(jobAppInfo.status())
                    .build();
            jobApp = jobApplicationRepository.save(jobApp);

            seedInterviewFlow(jobApp, jobAppInfo.questionsAndResponses());
        }
    }

    private void seedInterviewFlow(JobApplication jobApplication, List<QuestionResponseFeedbackSeedData> qrfData) {
        for (QuestionResponseFeedbackSeedData qrf : qrfData) {
            InterviewQuestion question = InterviewQuestion.builder()
                    .questionText(qrf.questionText())
                    .questionType(qrf.questionType())
                    .build();
            question = interviewQuestionRepository.save(question);

            JobApplicationQuestion jaq = JobApplicationQuestion.builder()
                    .jobApplication(jobApplication)
                    .interviewQuestion(question)
                    .build();
            jobApplicationQuestionRepository.save(jaq);
            jobApplication.addJobApplicationQuestion(jaq); // Link to job app's collection

            UserResponse userResponse = UserResponse.builder()
                    .responseText(qrf.userResponseText())
                    .interviewQuestion(question)
                    .jobApplication(jobApplication) // Link to job app
                    .build();
            userResponse = userResponseRepository.save(userResponse);

            AiFeedback aiFeedback = AiFeedback.builder()
                    .feedbackText(qrf.aiFeedbackText())
                    .score(qrf.aiFeedbackScore())
                    .userResponse(userResponse)
                    .build();
            aiFeedback.setId(userResponse.getId()); // Crucial for @MapsId
            aiFeedbackRepository.save(aiFeedback);
        }
        jobApplicationRepository.save(jobApplication); // Ensure JobApplication's questions collection is updated
    }
}