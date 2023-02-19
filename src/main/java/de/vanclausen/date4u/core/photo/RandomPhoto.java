package de.vanclausen.date4u.core.photo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class RandomPhoto {
  private final Logger log = LoggerFactory.getLogger( getClass() );
  int attemptCounter = 1;

  @Retryable
  public String receive ( String gender ) throws IOException {
    log.info( "Attempts: {}", attemptCounter++ );
    if ( attemptCounter <= 3 ) {
      throw new IOException("Not ready to serve");
    }
    String url = "https://randomuser.me/api/?inc=picture&noinfo&gender=" + gender;
    try ( InputStream is = new URL( url ).openStream() ) {
      return StreamUtils.copyToString( is, StandardCharsets.UTF_8 );
    } finally {
      attemptCounter = 1;
    }
  }
}
