package nl.ordina.javaee7.server.websockets.inverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Dumb inverter data holder.
 */
public class InverterData {
    public String serialNumberInverter;
    public Date timestamp;
    public BigDecimal temperature;
    public BigDecimal vpv;
    public BigDecimal iac;
    public BigDecimal vac;
    public BigDecimal fac;
    public BigDecimal pac;
    public BigDecimal etotal;
    public BigDecimal htotal;
    public BigDecimal mode;
    public BigDecimal etoday;
    public BigDecimal etotalh;
    public BigDecimal htotalh;
}
