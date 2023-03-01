package de.vanclausen.date4u.core.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnicornRepository extends JpaRepository<Unicorn, Long> {

  @Query( "SELECT u FROM Unicorn u WHERE u.email LIKE %:emailAddress%" )
  List<Optional<Unicorn>> findUnicornByEmail( String emailAddress);

}