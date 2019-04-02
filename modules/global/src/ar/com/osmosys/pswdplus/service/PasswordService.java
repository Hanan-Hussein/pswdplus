package ar.com.osmosys.pswdplus.service;


import java.util.UUID;

public interface PasswordService {
    String NAME = "pswdplus_PasswordService";

    boolean isPasswordInHistory(UUID userId, String passwordHash);


}