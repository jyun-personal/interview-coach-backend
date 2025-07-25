package com.interviewcoach.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequestDto {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    //    private String title;

    @Size(max = 50)
    private String bio;

    //    private String gender;
    //    private String image; // Maybe generate mock user profile image with pollination AI here to meet the project requirement? Or use random avatar API such as https://randomuser.me/api/portraits/men/12.jpg

    @NotBlank
    @Size(max = 50)
    private String street;

    @NotBlank
    @Size(max = 50)
    private String city;

    @NotBlank
    @Size(max = 50)
    private String state;

//    private String zip;

    @NotBlank
    @Size(max = 50)
    private String country;
}
