package ar.com.osmosys.pswdplus.web.screens;

import ar.com.osmosys.pswdplus.service.PasswordService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.app.security.user.changepassw.ChangePasswordDialog;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;

public class ExtChangePasswordDialog extends ChangePasswordDialog {

    @Inject
    PasswordService passwordService;

    private String encryptedPassword;
    private String newPassword;

    protected Messages messages= AppBeans.get(Messages.class);
    @Inject
    private Notifications notifications;

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

            this.encryptedPassword=passwordEncryption.getPasswordHash(targetUserId,password);
            this.newPassword=password;


           if (currentPasswordRequired
                    && !userManagementService.checkPassword(targetUserId, currentPassword)) {
                errors.add(currentPasswordField, getMessage("wrongCurrentPassword"));

            } else if (userManagementService.checkPassword(targetUserId, password)) {
                errors.add(passwField, getMessage("currentPasswordWarning"));

            } else if(passwordService.isPasswordInHistory(targetUserId,encryptedPassword)){
                //errors.add(passwField,getMessage("ar.com.osmosys.pswdplus.web.screens","passwordAlreadyUsed"));
                errors.add(passwField,messages.getMessage(getClass(),"passwordAlreadyUsed"));

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

    @Override
    public void changePassword() {
        if (validateAll()) {

            User targetUser;
            UUID targetUserId;
            if (user == null) {
                targetUserId = userSession.getUser().getId();
                targetUser = userSession.getUser();
            } else {
                targetUserId = user.getId();
                targetUser = user;
            }


            userManagementService.changeUserPassword(targetUserId, encryptedPassword);

            publishPasswordChangedEvent(targetUser, newPassword);

            notifications.create(Notifications.NotificationType.HUMANIZED).withDescription(getMessage("passwordChanged")).show();

            this.closeWithDefaultAction();
            //close(COMMIT_ACTION_ID);
        }
    }


}