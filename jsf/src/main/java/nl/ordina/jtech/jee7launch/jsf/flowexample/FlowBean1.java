package nl.ordina.jtech.jee7launch.jsf.flowexample;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@FlowScoped("flowExample")
public class FlowBean1 implements Serializable {

    private String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
