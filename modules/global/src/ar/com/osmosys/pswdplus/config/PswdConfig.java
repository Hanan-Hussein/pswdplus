package ar.com.osmosys.pswdplus.config;


import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;

@Source(type= SourceType.DATABASE)
public interface PswdConfig extends Config {

    @Property("pswdplus.daysToPswdExpiration")
    Integer getDaysToPswdExpiration();
    void setDaysToPswdExpiration(Integer daysToPswdExpiration);

    @Property("pswdplus.pswdHistoryLength")
    Integer getPswdHistoryLength();
    void setPswdHistoryLength(Integer pswdHistoryLength);

    @Property("pswdplus.usePswdHistory")
    Boolean getUsePswdHistory();
    void setUsePswdHistory(Boolean usePswdHistory);

    @Property("psdwplus.usePswdExpiration")
    Boolean getUsePswdExpiration();
    void setUsePswdExpiration(Boolean usePswdExpiration);

    
}
