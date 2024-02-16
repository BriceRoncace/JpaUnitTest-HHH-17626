package isp.idaho.gov;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.Set;

@Embeddable
public class Passport {
  private String country;
  private LocalDate expiration;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<Stamp> stamps;

  public static Passport of(String country, LocalDate expiration, Set<Stamp> stamps) {
    Passport passport = new Passport();
    passport.setCountry(country);
    passport.setExpiration(expiration);
    passport.setStamps(stamps);
    return passport;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getExpiration() {
    return expiration;
  }

  public void setExpiration(LocalDate expiration) {
    this.expiration = expiration;
  }

  public Set<Stamp> getStamps() {
    return stamps;
  }

  public void setStamps(Set<Stamp> stamps) {
    this.stamps = stamps;
  }

  @Override
  public String toString() {
    return "Passport{" +
      "country='" + country + '\'' +
      ", expiration=" + expiration +
      ", stamps=" + stamps +
      '}';
  }
}
