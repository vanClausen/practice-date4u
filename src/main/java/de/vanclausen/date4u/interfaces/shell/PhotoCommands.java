package de.vanclausen.date4u.interfaces.shell;

import de.vanclausen.date4u.core.photo.PhotoService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ShellComponent
public class PhotoCommands {

  private final PhotoService photoService;

  public PhotoCommands( PhotoService photoService ) {
    this.photoService = photoService;
  }

  @ShellMethod( "Show photo" )
  String showPhoto( String name ) { // show-photo
    return photoService.download( name )
        .map( bytes -> {
          try {
            var image = ImageIO.read( new ByteArrayInputStream( bytes ) );
            return "Width: " + image.getWidth() + ", Height: " + image.getHeight();
          }
          catch ( IOException e ) {
            return "Unable to read image dimensions";
          }
        } )
        .orElse( "Image not found" );
  }

  @ShellMethod( "Upload photo" )
  String uploadPhoto( String filename ) throws IOException { // upload-photo
    byte[] bytes = Files.readAllBytes( Paths.get( filename ) );
    return "Uploaded " + photoService.upload( bytes );
  }

  @ShellMethod( "Get random profile photo" )
  String randomPhoto( String gender ) {
    return "random photo: " + photoService.getRandomPhoto( gender );
  }

}
