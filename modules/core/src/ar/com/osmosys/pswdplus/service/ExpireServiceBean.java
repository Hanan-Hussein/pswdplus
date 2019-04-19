package ar.com.osmosys.pswdplus.service;

import ar.com.osmosys.pswdplus.config.PswdConfig;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.Callable;

@Service(ExpireService.NAME)
public class ExpireServiceBean implements ExpireService {


    @Inject
    PswdConfig pswdConfig;


    public String expireOldPasswords()
    {

        return "ok";
    }


}