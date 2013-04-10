import nl.ordina.javaee7.presentation.Person;
import org.junit.Before;
import org.junit.Test;

import javax.el.ELProcessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ExpressionLanguageTest {

  private List<Person> persons;

  @Before
  public void init() {
    persons = new ArrayList<>(Arrays.asList(
            new Person(17, "Sander"),
            new Person(19, "Martin"),
            new Person(25, "Vincent"),
            new Person(31, "Gabriël")));

  }

  @Test
  public void testSetEl() {
    ELProcessor elProcessor = new ELProcessor();

    System.out.println("testSetEl: " + elProcessor.eval("[1,2,3.4].size()"));
  }


  @Test
  public void testLamba() {

    ELProcessor elProcessor = new ELProcessor();
    elProcessor.setValue("persons", persons);
    String ouderDan18 = "persons.stream().filter(p->p.age > 18).toList().size()";
    System.out.println("testLamba: ouder dan 18                       : "  + elProcessor.eval(ouderDan18));
    assertEquals(3, elProcessor.eval(ouderDan18));

String expr = "persons.stream()" +
                     ".filter(p->p.age > 18)" +
                     ".map(p->p.age)" +
                     ".sum()";
assertEquals(75L, elProcessor.eval(expr));

    String ouderDan18BegintMetGa = "persons.stream().filter(p->p.age > 18 && p.voornaam.startsWith('Ga')).toList().size()";
    System.out.println("testLamba: ouder dan 18, voornaam start met Ga: "  + elProcessor.eval(ouderDan18BegintMetGa));
    assertEquals(1,elProcessor.eval(ouderDan18BegintMetGa));
  }

}
