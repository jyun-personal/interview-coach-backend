package com.interviewcoach.model;

import com.interviewcoach.enums.Gender;
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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String firstName;

    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String lastName;

    @Size(max = 50)
    @Column(length = 50)
    private String title;  // Current job title. E.g. Data Engineer. Optional

    @Size(max = 50)
    @Column(length = 50)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Gender gender;  // Optional gender field
    // This will be used to generate mock user profile images with https://randomuser.me/api/portraits/{gender}/12.jpg, etc

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String street;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String city;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String state;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String country;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String resumeText; // Manually pasted resume content. Optional
}
