package nl.ordina.javaee7.validation.presentation;

import nl.ordina.javaee7.validation.boundary.MethodValidationService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Date;

/**
 *
 */
@Model
public class Index {
  private Date employmentDate;
  private String key;
  private Date estimatedResignDate;

  @Inject MethodValidationService service;
  private String randomGuess;

  public String check() {
    System.out.println("Validating key: [" + key + "], employment since [" + employmentDate + "]");
    try {
      estimatedResignDate = service.estimateResignDate(key, employmentDate);
    } catch (ConstraintViolationException e) {
      e.printStackTrace();
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
    }
    return null;
  }

  public String randomGuess() {
    System.out.println("Random guess");
    estimatedResignDate = service.randomGuess();
    return null;
  }

  public Date getEmploymentDate() {
    return employmentDate;
  }

  public void setEmploymentDate(final Date employmentDate) {
    this.employmentDate = employmentDate;
  }

  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public Date getEstimatedResignDate() {
    return estimatedResignDate;
  }

  public void setEstimatedResignDate(final Date estimatedResignDate) {
    this.estimatedResignDate = estimatedResignDate;
  }

}
