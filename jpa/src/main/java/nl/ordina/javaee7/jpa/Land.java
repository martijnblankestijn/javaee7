package nl.ordina.javaee7.jpa;

import java.io.Serializable;

/**
 *
 */
public class Land implements Serializable{
  private String code;
  private String naam;

  public Land(String code, String naam) {
    this.code = code;
    this.naam = naam;
  }

  public String getCode() {
    return code;
  }

  public String getNaam() {
    return naam;
  }

  @Override
  public String toString() {
    return "Land{" +
            "code='" + code + '\'' +
            ", naam='" + naam + '\'' +
            '}';
  }
}
