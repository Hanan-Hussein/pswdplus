package ar.com.osmosys.pswdplus.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(PasswordService.NAME)
public class PasswordServiceBean implements PasswordService {


    @Override
    public boolean isPasswordInHistory(UUID userId, String passwordHash)
    {
        return false;
    }


}