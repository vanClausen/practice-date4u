package de.vanclausen.date4u.interfaces.shell;

import de.vanclausen.date4u.core.profile.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class EntityManagerCommands {

  private static final int PAGE_SIZE = 10;
  private final EntityManager em;
  private final Logger log = LoggerFactory.getLogger( getClass() );

  public EntityManagerCommands( EntityManager em ) { this.em = em; }

  @ShellMethod( "Display profile" )
  public String findprofile( long id ) {
    return Optional.ofNullable( em.find( Profile.class, id ) )
        .map( this::formatProfileAsTable )
        .orElse( "Unknown profile for ID " + id );
  }

  private String formatProfileAsTable( Profile profile ) {
    TableModel tableModel = new TableModelBuilder<String>()
        .addRow().addValue( "ID" ).addValue( "" + profile.getId() )
        .addRow().addValue( "Nickname" ).addValue( profile.getNickname() )
        .addRow().addValue( "Birthdate" ).addValue( profile.getBirthdate().toString() )
        .build();
    Table table = new TableBuilder( tableModel ).addFullBorder( BorderStyle.fancy_light ).build();
    return table.render( 100 );
  }

  @ShellMethod( "Display profile for a given page" )
  public List<Profile> page ( int page ) {
    return em.createQuery( "SELECT p FROM Profile p", Profile.class )
        .setFirstResult( page * PAGE_SIZE )
        .setMaxResults( PAGE_SIZE )
        .getResultList();
  }

  @ShellMethod( "Display latest seen profiles" )
  public List<Profile> lastseen() {
    return em.createQuery( "SELECT p FROM Profile p WHERE p.lastseen > :lastseen", Profile.class )
        .setParameter( "lastseen", LocalDateTime.now().minusMonths( 18 ) )
        .getResultList();
  }

  @ShellMethod("Show hornlength count")
  public void counthorns() {
    em.createQuery( "SELECT p.hornlength, COUNT(p) FROM Profile p GROUP BY p.hornlength ORDER BY p.hornlength DESC", Tuple.class )
        .getResultList()
        .forEach( p -> log.info( "hornlength: {}, count: {}", p.get( 0 ), p.get( 1 ) ) );
  }
}
