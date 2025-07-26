package com.interviewcoach.repository;

import com.interviewcoach.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // findById(Long id) implicitly handles finding by user_id due to @MapsId
    // Might still want a custom query if we specifically need to find by User object:
    // Optional<Profile> findByUser(User user);
}
