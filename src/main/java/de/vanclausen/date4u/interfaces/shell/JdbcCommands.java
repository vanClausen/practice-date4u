//package de.vanclausen.date4u.interfaces.shell;
//
//import org.springframework.jdbc.core.DataClassRowMapper;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//
//@ShellComponent
//public class JdbcCommands {
//
//  private final NamedParameterJdbcTemplate namedJdbcTemplate;
//
//  public JdbcCommands( NamedParameterJdbcTemplate namedJdbcTemplate ) {
//    this.namedJdbcTemplate = namedJdbcTemplate;
//  }
//
//
//  @ShellMethod( "Show hornlength of a given profile by nickname" )
//  public String hornlength( String nickname ) {
//    String sql = "SELECT hornlength FROM Profile WHERE nickname = :name";
//    List<Integer> lengths = namedJdbcTemplate.queryForList( sql,
//                                                      Map.of("name", nickname),
//                                                      Integer.class );
//    return lengths.isEmpty() ? "Unknown profile for nickname " + nickname
//        : lengths.get( 0 ).toString();
//  }
//
//  @ShellMethod( "Show last seen unicorns after Timestamp" )
//  public void lastseen( String timestamp ) {
//    String sql = """
//        SELECT  nickname AS nick, lastseen
//        FROM    Profile
//        WHERE   Lastseen > :time
//        ORDER BY lastseen""";
//
//    record NickLastseen( String nick, LocalDateTime lastseen ) { }
//
//    // Row-Mapper
////    jdbcTemplate.query(
////        sql,
////        ( row, __ ) -> new NickLastseen( row.getString( "nickname" ), row.getTimestamp( "lastseen" ).toLocalDateTime()),
////        LocalDate.parse( timestamp )
////    ).stream()
////        .map( value -> value.nick + ", " + value.lastseen )
////        .forEach( System.out::println );
//
//    // DataClassRowMapper
//    namedJdbcTemplate.query(
//        sql, Map.of("time", LocalDate.parse( timestamp ) ), new DataClassRowMapper<>(NickLastseen.class)
//    ).stream()
//        .map( value -> value.nick + ", " + value.lastseen )
//        .forEach( System.out::println );
//  }
//}
