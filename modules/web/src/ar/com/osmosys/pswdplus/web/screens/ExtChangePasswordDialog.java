package ar.com.osmosys.pswdplus.web.screens;

import ar.com.osmosys.pswdplus.service.PasswordService;
import com.haulmont.cuba.gui.app.security.user.changepassw.ChangePasswordDialog;
import com.haulmont.cuba.gui.components.ValidationErrors;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

public class ExtChangePasswordDialog extends ChangePasswordDialog {

    @Inject
    PasswordService passwordService;


    @Override
    protected void postValidate(ValidationErrors errors) {
        super.postValidate(errors);

        String password = passwField.getValue();

        if (errors.isEmpty()) {
            String currentPassword = currentPasswordField.getValue();
            String passwordConfirmation = confirmPasswField.getValue();

            UUID targetUserId;
            if (user == null) {
                targetUserId = userSession.getUser().getId();
            } else {
                targetUserId = user.getId();
            }

            if (currentPasswordRequired
                    && !userManagementService.checkPassword(targetUserId, passwordEncryption.getPlainHash(currentPassword))) {
                errors.add(currentPasswordField, getMessage("wrongCurrentPassword"));

            } else if (userManagementService.checkPassword(targetUserId, passwordEncryption.getPlainHash(password))) {
                errors.add(passwField, getMessage("currentPasswordWarning"));

            } else if(passwordService.isPasswordInHistory(targetUserId,passwordEncryption.getPasswordHash(targetUserId,password))){
                errors.add(passwField,getMessage("passwordAlreadyUsed"));

            } else if (!Objects.equals(password, passwordConfirmation)) {
                errors.add(confirmPasswField, getMessage("passwordsDoNotMatch"));

            } else {
                if (clientConfig.getPasswordPolicyEnabled()) {
                    String regExp = clientConfig.getPasswordPolicyRegExp();
                    if (!password.matches(regExp)) {
                        errors.add(passwField, getMessage("simplePassword"));
                    }
                }
            }
        }
    }


}