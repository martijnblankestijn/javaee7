package nl.ordina.javaee7.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 */
@Converter
public class LandConverter implements AttributeConverter<Land, String>{

  @Override
  public String convertToDatabaseColumn(Land land) {
    return land.getCode();
  }

  @Override
  public Land convertToEntityAttribute(String code) {
    return new Land(code, "Vertaling van " + code);
  }
}
