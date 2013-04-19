package nl.ordina.javaee7.validation.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 *
 */
@ApplicationScoped
public class MethodValidationService {

  @NotNull @Future
  public Date estimateResignDate(
          @NotNull @Size(min=5, max=8) String key,
          @NotNull @Past Date employmentDate) {

    return estimateResignDate(employmentDate);
  }

  private Date estimateResignDate(final Date employmentDate) {
    Date date = new Date(employmentDate.getYear() + 4, employmentDate.getMonth(), employmentDate.getDay());
    System.out.println("Resultaat: " + date);
    return date;
  }

  @NotNull @Future
  public Date randomGuess() {
    return new Date(110,1,1);
  }
}
