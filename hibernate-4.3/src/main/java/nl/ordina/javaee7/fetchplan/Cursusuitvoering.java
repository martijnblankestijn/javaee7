package nl.ordina.javaee7.fetchplan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cursusuitvoering {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne private Cursus cursus;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UITVOERING_ID")
    private Set<Sessie> sessies = new HashSet<Sessie>();

    protected Cursusuitvoering() {
    }

    public Cursusuitvoering(Cursus cursus, Set<Sessie> sessies) {
        this.cursus = cursus;
        this.sessies = sessies;
    }

    @Override
    public String toString() {
        return "Cursusuitvoering{" +
            "sessies=" + sessies +
            ", cursus=" + cursus +
            ", id=" + id +
            '}';
    }
}
