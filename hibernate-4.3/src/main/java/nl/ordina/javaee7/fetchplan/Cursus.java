package nl.ordina.javaee7.fetchplan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraphs({
    @NamedEntityGraph(name = "CursusIncSessies",
        attributeNodes = {
            @NamedAttributeNode("naam"),
            @NamedAttributeNode(value = "uitvoeringen", subgraph = "uitvoeringenIncSessies")
        },
        subgraphs =
            {
                @NamedSubgraph(name = "uitvoeringenIncSessies",
                    attributeNodes = @NamedAttributeNode(value = "sessies", subgraph = "sessie")),
                @NamedSubgraph(name = "sessie", attributeNodes = @NamedAttributeNode("dag"))
            }
    ),


})
@Entity
public class Cursus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String naam;
    private String doelstelling;
    private int aantalSessies;
    @OneToMany(mappedBy = "cursus")
    private List<Cursusuitvoering> uitvoeringen = new ArrayList<Cursusuitvoering>();

    protected Cursus() {
    }

    public Cursus(String naam, String doelstelling, int aantalSessies) {
        this.naam = naam;
        this.doelstelling = doelstelling;
        this.aantalSessies = aantalSessies;
    }

    @Override
    public String toString() {
        return "Cursus{" +
            "id=" + id +
            ", naam='" + naam + '\'' +
            ", doelstelling='" + doelstelling + '\'' +
            ", aantalSessies=" + aantalSessies +
            '}';
    }

    public List<Cursusuitvoering> getUitvoeringen() {
        return uitvoeringen;
    }

    public Long getId() {
        return id;
    }
}
