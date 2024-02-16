package isp.idaho.gov;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JpaUnitTestCase {

  private EntityManagerFactory entityManagerFactory;

  @Before
  public void init() {
    entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
  }

  @After
  public void destroy() {
    entityManagerFactory.close();
  }

  @Test
  public void hhh17626Test() throws Exception {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    // Seed DB with Person
    Person p = Person.of("Bob", Passport.of("United States", LocalDate.of(2034, Month.MARCH, 1), Set.of(Stamp.of("Japan", LocalDate.of(2024, Month.JANUARY, 1)))));
    em.merge(p);

    // Use Criteria API
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Person> criteriaQuery = cb.createQuery(Person.class);
    Root<Person> person = criteriaQuery.from(Person.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(person.get(Person_.name), "Bob"));

    Join<Person, Passport> passportJoin = person.join(Person_.passport);
    predicates.add(cb.like(passportJoin.get(Passport_.country), "United States"));

    // Compile Error; meta model is incorrect! *****************************************************
    //
    // Join<Passport, Stamp> stampJoin = passportJoin.join(Passport_.stamps);
    //
    // Expecting:
    //   public static volatile SetAttribute<Passport, Stamp> stamps;
    // But is:
    //   public static volatile SingularAttribute<Passport, Set<Stamp>> stamps;
    // *********************************************************************************************

    // Workaround (don't use metamodel)
    Join<Passport, Stamp> stampJoin = passportJoin.join("stamps");

    predicates.add(cb.equal(stampJoin.get(Stamp_.country), "Japan"));

    Predicate predicate = and(predicates, cb);
    criteriaQuery.select(person).where(predicate);
    List<Person> persons = em.createQuery(criteriaQuery).getResultList();


    // Confirm we retrieved Person Bob
    Assert.assertEquals(1, persons.size());
    Assert.assertEquals("Bob", persons.getFirst().getName());

    em.getTransaction().commit();
    em.close();
  }

  private Predicate and(List<Predicate> predicates, CriteriaBuilder cb) {
    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
