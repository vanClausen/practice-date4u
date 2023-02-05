package de.vanclausen.date4u.core.photo;

import de.vanclausen.date4u.core.FileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Base64;

//@ExtendWith( MockitoExtension.class )
@SpringBootTest( "spring.shell.interactive.enabled=false" )
class PhotoServiceTest {

  private static final byte[] MINIMAL_JPG = Base64.getDecoder().decode( "/9j/4AAQSkZJRgABAQEASABIAAD"
      + "/2wBDAP////////////////////////////////////////////////////////////"
      + "//////////////////////////wgALCAABAAEBAREA/8QAFBABAAAAAAAAAAAAAAAAA"
      + "AAAAP/aAAgBAQABPxA=" );                      // https://git.io/J9GXr;

//  @Mock( lenient = true ) FileSystem fs;
//  @Spy AwtBicubicThumbnail thumbnail;
//  @InjectMocks PhotoService photoService;
  @SpyBean AwtBicubicThumbnail thumbnail;
  @MockBean FileSystem fs;
  @Autowired PhotoService photoService;

//  @BeforeEach
//  void setupFileSystem() {
//    given( fs.getFreeDiskSpace() ).willReturn( 1L );
//    given( fs.load( anyString() ) ).willReturn( MINIMAL_JPG );
//  }

  @Test
  void successful_photo_upload() {
    String imageName = photoService.upload( MINIMAL_JPG );
    assertThat( imageName ).isNotEmpty();

    verify( fs, times( 2 ) ).store( anyString(), any( byte[].class ) );
    verify( thumbnail ).thumbnail( any( byte[].class ) );
  }
}