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

    //    private String title;

    @Size(max = 50)
    @Column(length = 50)
    private String bio;

    //    private String gender;
    //    private String image; // Maybe generate mock user profile image with pollination AI here to meet the project requirement? Or use random avatar API such as https://randomuser.me/api/portraits/men/12.jpg

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

//    private String zip;

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

    //private String resumeText; // Manually pasted resume content
}
