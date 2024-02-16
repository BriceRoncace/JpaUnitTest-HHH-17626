package isp.idaho.gov;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Person implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private Passport passport;

  public static Person of(String name, Passport passport) {
    Person person = new Person();
    person.setName(name);
    person.setPassport(passport);
    return person;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Passport getPassport() {
    return passport;
  }

  public void setPassport(Passport passport) {
    this.passport = passport;
  }

  @Override
  public String toString() {
    return "Person{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", passport=" + passport +
      '}';
  }
}
