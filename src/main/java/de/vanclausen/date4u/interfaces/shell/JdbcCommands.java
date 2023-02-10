package de.vanclausen.date4u.interfaces.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class JdbcCommands {

  final JdbcTemplate jdbcTemplate;

  public JdbcCommands( JdbcTemplate jdbcTemplate ) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @ShellMethod( "Show hornlength of a given profile by nickname" )
  public String hornlength( String nickname ) {
    String sql = "SELECT hornlength FROM Profile WHERE nickname=?";
    List<Integer> lengths = jdbcTemplate.queryForList( sql, Integer.class, nickname);
    return lengths.isEmpty() ? "Unknown profile for nickname " + nickname
        : lengths.get( 0 ).toString();
  }

}
