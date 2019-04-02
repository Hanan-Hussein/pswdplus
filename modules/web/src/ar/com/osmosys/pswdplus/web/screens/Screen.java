package ar.com.osmosys.pswdplus.web.screens;

import ar.com.osmosys.pswdplus.entity.PasswordHistory;
import ar.com.osmosys.pswdplus.service.PasswordService;
import com.haulmont.cuba.core.global.PasswordEncryption;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.UUID;

public class Screen extends AbstractWindow {

    @Inject
    PasswordService passwordService;

    @Inject
    protected PasswordEncryption passwordEncryption;

    @Inject
    TextField passwordTextField;

    @Inject
    UserSession userSession;

    public void onCheckPasswordClick() {

        String enteredPassword=passwordTextField.getValue();

        UUID userId=userSession.getUser().getId();

        String hash=passwordEncryption.getPasswordHash(userId,enteredPassword);
        showNotification(Boolean.toString(passwordService.isPasswordInHistory(userId,hash)));







    }
}