package de.vanclausen.date4u.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  @Query( "SELECT p FROM Profile p WHERE p.lastseen > ?#{[0].atDay(1).atStartOfDay()}")
  List<Profile> findProfileLastSeenAfter( Year lastseen);
}
