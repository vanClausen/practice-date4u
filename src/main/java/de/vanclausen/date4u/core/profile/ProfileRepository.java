package de.vanclausen.date4u.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, QuerydslPredicateExecutor<Profile> {

//  @Query( "SELECT p FROM Profile p WHERE p.lastseen > ?#{[0].atDay(1).atStartOfDay()}")
  List<Profile> findByLastseenGreaterThan( LocalDateTime lastseen);

  Optional<Profile> findFirstByOrderByHornlengthDesc();

  List<Profile> findByOrderByHornlengthDesc();

  List<Profile> findByHornlengthGreaterThan( short min );

  List<Profile> findFirst10ByOrderByLastseenDesc();

}