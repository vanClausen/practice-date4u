package de.vanclausen.date4u.core.photo;

import de.vanclausen.date4u.core.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Validated
public class PhotoService {
  private final FileSystem fs;
  private final Thumbnail thumbnail;
  private final RandomPhoto randomPhoto;
  //  private final ApplicationEventPublisher publisher;

  private final Logger log = LoggerFactory.getLogger( getClass() );

  public PhotoService( FileSystem fs, Thumbnail thumbnail, RandomPhoto randomPhoto ) {
    this.fs = fs;
    this.thumbnail = thumbnail;
    this.randomPhoto = randomPhoto;
  }

  @Cacheable( cacheNames = "date4u.filesystem.file", unless = "#result == null" )
  public Optional<byte[]> download( String name ) {
    try {
      log.info( "Load image {}", name );
      return Optional.ofNullable( fs.load( name + ".jpg" ) );
    }
    catch ( UncheckedIOException e ) {
      return Optional.empty();
    }
  }

  @Cacheable( cacheNames = "date4u.photo", key = "#photo.name" )
  public Optional<byte[]> download( @Valid Photo photo ) {
    return download( photo.name );
  }

  public String upload( byte[] imageBytes ) {
    Future<byte[]> thumbnailBytes = this.thumbnail.thumbnail( imageBytes );

    String imageName = UUID.randomUUID().toString();

//    NewPhotoEvent newPhotoEvent = new NewPhotoEvent( imageName, OffsetDateTime.now() );
//    publisher.publishEvent( newPhotoEvent );

    // First: store original image
    fs.store( imageName + ".jpg", imageBytes );

    // Second: store thumbnail
    try {
      log.info( "upload" );
      fs.store( imageName + "-thumb.jpg", thumbnailBytes.get() );
    }
    catch ( InterruptedException | ExecutionException e ) {
      throw new IllegalStateException( e );
    }

    return imageName;
  }

  public String getRandomPhoto (String gender) {
    try {
      return randomPhoto.receive( gender );
    }
    catch ( IOException e ) {
      return "Service not available";
    }
  }
}
