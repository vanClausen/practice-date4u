package de.vanclausen.date4u;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableRetry
public class Date4uApplication {

  private final Logger log = LoggerFactory.getLogger( getClass() );

//  public Date4uApplication() {
//    log.info( "Constructor" );
//  }

//  @Autowired UUID uuid1;
//  @Autowired UUID uuid2;
  public static void main( String[] args ) {


    ApplicationContext ctx = SpringApplication.run( Date4uApplication.class, args );


//    SpringApplication spa = new SpringApplication( Date4uApplication.class );
//    spa.setLazyInitialization( true );
//    ApplicationContext ctx = spa.run( args );

//    for ( String name : ctx.getBeanDefinitionNames() ) {
//      System.out.println( name );
//    }

//    FileSystem fileSystem = ctx.getBean( FileSystem.class );
//    System.out.printf( "%d GB%n", DataSize.ofBytes( fileSystem.getFreeDiskSpace() ).toGigabytes());


//    new SpringApplicationBuilder( Date4uApplication.class )
//        .headless( false )
////				.web( false )
//        .bannerMode( Banner.Mode.OFF )
//        .logStartupInfo( false )
//        .run( args );
  }

}
