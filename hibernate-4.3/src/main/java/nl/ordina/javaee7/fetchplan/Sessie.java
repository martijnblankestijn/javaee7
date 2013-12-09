package nl.ordina.javaee7.fetchplan;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Sessie {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dag;

    protected Sessie() {
    }

    public Sessie(Date dag) {
        this.dag = dag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sessie sessie = (Sessie) o;

        if (!dag.equals(sessie.dag)) return false;
        if (id != null ? !id.equals(sessie.id) : sessie.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + dag.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sessie{" +
            "id=" + id +
            ", dag=" + dag +
            '}';
    }
}
