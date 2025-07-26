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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    private Long id; // Matches User.id for shared primary key

    @OneToOne
    @MapsId // Maps the primary key of the owning entity (Profile) to the primary key of the associated entity (User)
    @JoinColumn(name = "user_id", nullable = false) // user_id column will be the PK and FK
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
        this.id = user.getId(); // Set ID from the User
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
