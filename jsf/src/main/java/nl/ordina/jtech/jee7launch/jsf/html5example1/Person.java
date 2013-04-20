package nl.ordina.jtech.jee7launch.jsf.html5example1;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Person implements Serializable {
    private String firstName;
    private String familyName;
    private String email;
    
    private String greeting;
    
    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }
    
    
    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setEmail(String telephoneNumber) {
        this.email = telephoneNumber;
    }
    
    public String getGreeting() {
        if (greeting != null) {
            return greeting;
        } else {
            return "Who are you...";
        }
    }
        
    public String sayHello() {
        if (allFieldsFilled()) {
            greeting = "Hello " + firstName + " " + familyName + "! We'll be sending you junk on " + email; 
        }
        
        return "";
    }
    
    public String getGreetingsClass() {
        return allFieldsFilled() ? "weKnowYou" : "weDontKnowYou";
    }
    
    private boolean isNotBlank(String value) {
        return value != null && !"".equals(value.trim());
    }

    private boolean allFieldsFilled() {
        return isNotBlank(firstName) && isNotBlank(familyName) && isNotBlank(email);
    }
}
