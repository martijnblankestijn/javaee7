package nl.ordina.javaee7.jpa;

import javax.persistence.AttributeConverter;

import static nl.ordina.javaee7.jpa.Geslacht.*;
/**
 *
 */
//@Converter(autoApply = true)
public class GeslachtConverter implements AttributeConverter<Geslacht, Integer> {
  @Override
  public Integer convertToDatabaseColumn(Geslacht geslacht) {
    System.out.println("convertToDatabaseColumn voor " + geslacht);
    switch (geslacht) {
      case VROUW: return 2;
      case MAN: return 4;
      default: return 8;
    }
  }

  @Override
  public Geslacht convertToEntityAttribute(Integer integer) {
    System.out.println("convertToEntityAttribute voor " + integer);
    switch (integer) {
      case 2: return VROUW;
      case 4: return MAN;
      default: return ONBEKEND;
    }
  }
}
