package nl.ordina.javaee7.presentation;

public class Person {
  private int age;
  private String voornaam;

  public Person(final int age, final String voornaam) {
    this.age = age;
    this.voornaam = voornaam;
  }

  public int getAge() {
    return age;
  }

  public String getVoornaam() {
    return voornaam;
  }
}
