package isp.idaho.gov;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Stamp {

  @Id
  @GeneratedValue
  private Long id;
  private String country;
  private LocalDate created;

  public static Stamp of(String country, LocalDate created) {
    Stamp stamp = new Stamp();
    stamp.setCountry(country);
    stamp.setCreated(created);
    return stamp;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getCreated() {
    return created;
  }

  public void setCreated(LocalDate created) {
    this.created = created;
  }

  @Override
  public String toString() {
    return "Stamp{" +
      "id=" + id +
      ", country='" + country + '\'' +
      ", created=" + created +
      '}';
  }
}
