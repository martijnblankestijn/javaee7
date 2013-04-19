package nl.ordina.javaee7.presentation;

import javax.enterprise.inject.Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Model
public class PersonsHolder {
  private List<Person> persons = new ArrayList<>(Arrays.asList(
          new Person(17, "Sander"),
          new Person(19, "Martin"),
          new Person(25, "Vincent"),
          new Person(31, "Gabriël")));

  public List<Person> getPersons() {
    return persons;
  }
}
