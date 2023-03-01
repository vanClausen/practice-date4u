package de.vanclausen.date4u.interfaces.shell;

import de.vanclausen.date4u.core.profile.Profile;
import de.vanclausen.date4u.core.profile.ProfileRepository;
import de.vanclausen.date4u.core.profile.Unicorn;
import de.vanclausen.date4u.core.profile.UnicornRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class RepositoryCommands {

  public static final int PAGE_SIZE = 10;
  final ProfileRepository profiles;
  final UnicornRepository unicorns;
  private Lazy<Page<Profile>> currentPage;

  public RepositoryCommands( ProfileRepository profiles, UnicornRepository unicorns ) {
    this.profiles = profiles;
    currentPage = Lazy.of( () -> profiles.findAll( Pageable.ofSize( PAGE_SIZE ) ) );
    this.unicorns = unicorns;
  }

  @ShellMethod("Display all profiles")
  public List<Profile> list() {
    return currentPage.get().getContent();
  }

  @ShellMethod( "Previous page" )
  public List<Profile> pp() {
    currentPage = currentPage.map( page -> profiles.findAll( page.previousOrFirstPageable() ) );
    return list();
  }

  @ShellMethod( "Next page" )
  public List<Profile> np() {
    currentPage = currentPage.map( page -> profiles.findAll( page.nextOrLastPageable() ) );
    return list();
  }

  @ShellMethod( "Update last seen")
  public Optional<Profile> updateLastSeen( long id ) {
    Optional<Profile> maybeProfile = profiles.findById( id );
    maybeProfile.ifPresent( profile -> {
      profile.setLastseen( LocalDateTime.now() );
      profiles.save( profile );
    } );
    return maybeProfile;
  }

  @ShellMethod( "Show profiles last seen after year" )
  public String afterYear(int year) {
    return profiles.findProfileLastSeenAfter( Year.of( year ) ).toString();
  }

  @ShellMethod( "Find unicorn by mail" )
  public String findByMail(String mail) {
    return unicorns.findUnicornByEmail( mail )
        .stream()
        .map( o -> o.map( u -> u.getEmail() ).orElse( "Nothing found for \"%s\"".formatted( mail ) ) )
        .toString();
  }

}
