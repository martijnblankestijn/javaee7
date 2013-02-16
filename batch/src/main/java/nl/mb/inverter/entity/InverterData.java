package nl.mb.inverter.entity;

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
    public BigDecimal err_gv;
    public BigDecimal err_gf;
    public BigDecimal err_gz;
    public BigDecimal err_temp;
    public BigDecimal err_pv1;
    public BigDecimal err_gfc1;
    public BigDecimal err_mode;
}
