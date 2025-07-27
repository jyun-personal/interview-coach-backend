package com.interviewcoach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String lastName;

    @Size(max = 20) // Phone numbers can vary, keeping a reasonable size
    @Column(length = 20)
    private String phone;

    @Size(max = 500)
    @Column(length = 500)
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String resumeText; // Added resumeText field

    // Removed: title, gender, street, city, state, country for MVP simplicity and ERD alignment

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    // Constructor to link User and Profile for initial creation in AuthService
    public Profile(User user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
