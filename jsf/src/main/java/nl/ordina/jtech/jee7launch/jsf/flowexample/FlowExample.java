package nl.ordina.jtech.jee7launch.jsf.flowexample;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import java.io.Serializable;

public class FlowExample implements Serializable {
    private static final long serialVersionUID = 1l;

    @Produces @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        System.err.println("Building the flow!");

        String flowId = "flowExample";

        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        flowBuilder.returnNode("/index");

        return flowBuilder.getFlow();
    }
}
