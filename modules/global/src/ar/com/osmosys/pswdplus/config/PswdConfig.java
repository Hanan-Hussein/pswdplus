package ar.com.osmosys.pswdplus.config;


import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

@Source(type= SourceType.DATABASE)
public interface PswdConfig extends Config {


    @Property("pswdplus.daysToPswdExpiration")
    @Default("30")
    Integer getDaysToPswdExpiration();
    void setDaysToPswdExpiration(Integer daysToPswdExpiration);

    @Property("pswdplus.pswdHistoryLength")
    @Default("10")
    Integer getPswdHistoryLength();
    void setPswdHistoryLength(Integer pswdHistoryLength);

    @Property("pswdplus.usePswdHistory")
    @Default("true")
    Boolean getUsePswdHistory();
    void setUsePswdHistory(Boolean usePswdHistory);


    @Property("pswdplus.usePswdExpiration")
    @Default("true")
    Boolean getUsePswdExpiration();
    void setUsePswdExpiration(Boolean usePswdExpiration);

}
