package nl.ordina.javaee7.rest.boundary;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement
public class Systeem {
  private long id;
  private String serialNumber;
  private long capacity;

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(final String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public long getCapacity() {
    return capacity;
  }

  public void setCapacity(final long capacity) {
    this.capacity = capacity;
  }

  @Override public String toString() {
    return "Systeem{" +
            "id=" + id +
            ", serialNumber='" + serialNumber + '\'' +
            ", capacity=" + capacity +
            '}';
  }
}
