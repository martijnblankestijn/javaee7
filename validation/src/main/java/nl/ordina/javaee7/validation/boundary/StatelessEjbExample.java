package nl.ordina.javaee7.validation.boundary;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Stateless
@Named
public class StatelessEjbExample {
  @NotNull
  public String checkReturnAlwaysNull() {
    System.out.println("Check returns null always");
    return null;
  }

  @NotNull
  public String check(@NotNull String s) {
    System.out.println("Returning " + s);
    return s;
  }

}
