package nl.ordina.javaee7.jpa.entitygraph;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@NamedEntityGraph(
        attributeNodes={@NamedAttributeNode("projects")}
)
@Entity
public class Employee{
  @Id
  @GeneratedValue
  protected long id;
  @Basic
  protected String name;
  @Basic
  protected String employeeNumber;
//  @OneToMany()
//  protected List<Dependents> dependents;
  @OneToMany()
  protected List<Project> projects;
}