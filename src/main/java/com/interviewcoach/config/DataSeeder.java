package com.interviewcoach.config;

import com.interviewcoach.enums.QuestionType;
import com.interviewcoach.model.*;
import com.interviewcoach.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository; // Keep concrete repository
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository; // Keep concrete repository
    @Autowired
    private JobApplicationQuestionRepository jobApplicationQuestionRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;
    @Autowired
    private AiFeedbackRepository aiFeedbackRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            System.out.println("Seeding initial data...");

            User user1 = User.builder()
                    .username("john_doe")
                    .email("john.doe@example.com")
                    .passwordHash(passwordEncoder.encode("password123"))
                    .role("JOB_SEEKER")
                    .build();
            user1 = userRepository.save(user1);

            Profile profile1 = new Profile(user1, "John", "Doe");
            profile1.setPhone("123-456-7890");
            profile1.setBio("Experienced software engineer with a passion for learning new technologies.");
            profile1.setResumeText("Skills: Java, Spring Boot, React, SQL, AWS. Projects: E-commerce platform, Social media app.");
            profileRepository.save(profile1);
            user1.setProfile(profile1);

            JobApplication jobApp1 = JobApplication.builder()
                    .title("Senior Java Developer")
                    .description("Exciting opportunity for a Senior Java Developer to join our team...")
                    .user(user1)
                    .applicationDate(OffsetDateTime.now())
                    .expectedInterviewDate(LocalDate.now().plusWeeks(2))
                    .status("IN_PROGRESS")
                    .build();
            jobApp1 = jobApplicationRepository.save(jobApp1);

            InterviewQuestion q1 = InterviewQuestion.builder()
                    .questionText("Tell me about a challenging project you worked on and how you handled it.")
                    .questionType(QuestionType.BEHAVIORAL)
                    .build();
            q1 = interviewQuestionRepository.save(q1);

            InterviewQuestion q2 = InterviewQuestion.builder()
                    .questionText("Explain the difference between HashMap and ConcurrentHashMap.")
                    .questionType(QuestionType.TECHNICAL)
                    .build();
            q2 = interviewQuestionRepository.save(q2);

            InterviewQuestion q3 = InterviewQuestion.builder()
                    .questionText("How would you handle a situation where a team member consistently misses deadlines?")
                    .questionType(QuestionType.SITUATIONAL)
                    .build();
            q3 = interviewQuestionRepository.save(q3);

            JobApplicationQuestion jaq1 = JobApplicationQuestion.builder().jobApplication(jobApp1).interviewQuestion(q1).build();
            JobApplicationQuestion jaq2 = JobApplicationQuestion.builder().jobApplication(jobApp1).interviewQuestion(q2).build();
            JobApplicationQuestion jaq3 = JobApplicationQuestion.builder().jobApplication(jobApp1).interviewQuestion(q3).build();
            jobApplicationQuestionRepository.save(jaq1);
            jobApplicationQuestionRepository.save(jaq2);
            jobApplicationQuestionRepository.save(jaq3);
            jobApp1.addJobApplicationQuestion(jaq1);
            jobApp1.addJobApplicationQuestion(jaq2);
            jobApp1.addJobApplicationQuestion(jaq3);
            jobApplicationRepository.save(jobApp1);

            UserResponse userResponse1 = UserResponse.builder()
                    .responseText("In a recent project, we faced a tight deadline. I broke down tasks, delegated effectively, and communicated constantly to ensure success.")
                    .interviewQuestion(q1)
                    .jobApplication(jobApp1)
                    .build();
            userResponse1 = userResponseRepository.save(userResponse1);

            AiFeedback aiFeedback1 = AiFeedback.builder()
                    .feedbackText("Good concise answer. Provide more specific details using the STAR method for stronger impact. Score: 7/10.")
                    .score(7)
                    .userResponse(userResponse1)
                    .build();
            aiFeedback1.setId(userResponse1.getId());
            aiFeedbackRepository.save(aiFeedback1);
            userResponse1.setAiFeedback(aiFeedback1);

            System.out.println("Data seeding complete!");
        } else {
            System.out.println("Database already contains data. Skipping seeding.");
        }
    }
}