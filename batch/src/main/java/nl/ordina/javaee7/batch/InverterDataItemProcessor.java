package nl.ordina.javaee7.batch;

import nl.mb.inverter.entity.InverterData;

import javax.batch.api.chunk.ItemProcessor;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 */
public class InverterDataItemProcessor implements ItemProcessor {
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  private final int minimalNumberOfParts = 21;

  @Override
  public InverterData processItem(Object ob) throws Exception {
    String record = (String) ob;
    if (LOG.isLoggable(Level.FINEST)) {
      LOG.log(Level.FINEST, "Processing {0}", record);
    }

    String[] parts = record.split(",");

    if (parts.length < minimalNumberOfParts) {
      throw new InvalidRecordException("Expected minimum of " + minimalNumberOfParts + " parts, but got " + parts.length + " parts in record [" + record + "]");
    }

    InverterData data = new InverterData();
    data.timestamp = new Date(Long.parseLong(parts[1]));
    data.temperature = toBigDecimal(parts[2]);
    data.vpv = toBigDecimal(parts[3]);
    data.iac = toBigDecimal(parts[4]);
    data.vac = toBigDecimal(parts[5]);
    data.fac = toBigDecimal(parts[6]);
    data.pac = toBigDecimal(parts[7]);
    data.etotal = toBigDecimal(parts[8]);
    data.htotal = toBigDecimal(parts[9]);
    data.mode = toBigDecimal(parts[10]);
    data.etoday = toBigDecimal(parts[11]);
    data.etotalh = toBigDecimal(parts[12]);
    data.htotalh = toBigDecimal(parts[13]);
    data.err_gv = toBigDecimal(parts[14]);
    data.err_gf = toBigDecimal(parts[15]);
    data.err_gz = toBigDecimal(parts[16]);
    data.err_temp = toBigDecimal(parts[17]);
    data.err_pv1 = toBigDecimal(parts[18]);
    data.err_gfc1 = toBigDecimal(parts[19]);
    data.err_mode = toBigDecimal(parts[20]);
    return data;
  }

  private BigDecimal toBigDecimal(String part) throws InvalidRecordException {
    try {
      return new BigDecimal(part);
    } catch (NumberFormatException e) {
      throw new InvalidRecordException("[" + part + "] is not a BigDecimal");
    }
  }

}
