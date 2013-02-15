package nl.ordina.javaee7.batch;

import javax.batch.annotation.ItemProcessor;
import javax.batch.annotation.ProcessItem;


/**
 *
 */
@ItemProcessor(value = "InverterDataItemProcessor ")
public class InverterDataItemProcessor {
    @ProcessItem
    public String processData(String record) throws Exception {
        System.out.println("Processing " + record);
        return "processed:" + record;
    }
}
