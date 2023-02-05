package de.vanclausen.date4u.interfaces.shell;

import de.vanclausen.date4u.core.FileSystem;
import de.vanclausen.date4u.core.configuration.Date4uProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.nio.file.Path;

@ShellComponent
public class FsCommands {

  private final FileSystem fs;
  private final Environment environment;

//  @Value( "${date4u.filesystem.minimum-free-disk-space:1000000}" )
//  private long minimumFreeDiskSpace;

  private final Date4uProperties date4uProperties;

  public FsCommands( FileSystem fs, Environment environment, Date4uProperties date4uProperties ) {
    this.fs = fs;
    this.environment = environment;
    this.date4uProperties = date4uProperties;
  }

  @ShellMethod( "Display required free disk space" )
  public long minimumFreeDiskSpace() {
    return date4uProperties.getFilesystem().getMinimumFreeDiskSpace();
  }

  @ShellMethod( "Display free disk space" )
  public String freeDiskSpace() { return DataSize.ofBytes( fs.getFreeDiskSpace() ).toGigabytes() + " GB"; }

  @ShellMethod( "Display user directory" )
  public String userHome() { return environment.getProperty( "user.home" ); }

  @ShellMethod( "Display if a path exists")
  public boolean exists( Path path ) {
    System.out.println( path );
    return true;
  }
}

@Component
class StringToPathConverter implements Converter<String, Path> {
  @Override
  public Path convert( String source ) {
    return Path.of( source );
  }
}