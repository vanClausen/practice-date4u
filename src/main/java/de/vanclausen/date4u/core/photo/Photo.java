package de.vanclausen.date4u.core.photo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class Photo {
  public long id;
  @Min( 1 ) public long profile;
  @NotNull @Pattern ( regexp= "[\\w_-]{1,200}" ) public String name;
  public boolean isProfilePhoto;
  @NotNull @Past  public LocalDateTime created;

  public Photo() {
  }

  public Photo( long id, long profile, String name, boolean isProfilePhoto, LocalDateTime created ) {
    this.id = id;
    this.profile = profile;
    this.name = name;
    this.isProfilePhoto = isProfilePhoto;
    this.created = created;
  }

  @Override
  public String toString() {
    return "Photo{" +
        "id=" + id +
        ", profile=" + profile +
        ", name='" + name + '\'' +
        ", isProfilePhoto=" + isProfilePhoto +
        ", created=" + created +
        '}';
  }
}
