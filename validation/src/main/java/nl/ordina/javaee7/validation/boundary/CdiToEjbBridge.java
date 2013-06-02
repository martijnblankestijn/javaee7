package nl.ordina.javaee7.validation.boundary;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Named
public class CdiToEjbBridge {
  @Inject
  StatelessEjbExample ejb;

  @NotNull public String checkReturnAlwaysNull() {
    System.out.println("Bridge: checkReturnAlwaysNull");
    return ejb.checkReturnAlwaysNull();
  }

  @NotNull public String check(@NotNull final String s) {
    System.out.println("Bridge: check " + s);
    return ejb.check(s);
  }
}
