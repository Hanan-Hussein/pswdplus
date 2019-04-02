package ar.com.osmosys.pswdplus.service;

import ar.com.osmosys.pswdplus.config.PswdConfig;
import ar.com.osmosys.pswdplus.entity.PasswordHistory;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.PersistenceTools;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.EntityAccessException;
import com.haulmont.cuba.security.app.UserManagementServiceBean;
import com.haulmont.cuba.security.entity.User;
import org.apache.http.util.EntityUtils;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.UUID;

import static com.haulmont.bali.util.Preconditions.checkNotNullArgument;

public class ExtUserManagementServiceBean extends UserManagementServiceBean {

    @Inject
    PswdConfig pswdConfig;

    @Override
    public boolean checkPassword(UUID userId, String passwordHash) {
        checkNotNullArgument(userId, "Null userId");
        checkNotNullArgument(passwordHash, "Null new password hash");

        User user;

        Transaction tx = persistence.getTransaction();
        try {
            EntityManager em = persistence.getEntityManager();

            user = em.find(User.class, userId, CHECK_PASSWORD_VIEW);
            if (user == null)
                throw new RuntimeException("Unable to find user with id: " + userId);

            tx.commit();
        } finally {
            tx.end();
        }

        return passwordEncryption.checkPassword(user, passwordHash);
    }

    /*
    Adds an entry into PasswordHistory table. Later on,
    we'll check against the new password against the
    historical passwords
     */
    @Override
    public void changeUserPassword(UUID userId, String newPasswordHash) {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            User user = em.find(User.class, userId, CHANGE_PASSWORD_VIEW);
            if (user == null) {
                throw new EntityAccessException(User.class, userId);
            }

            if(pswdConfig.getUsePswdHistory())
            {
                PasswordHistory passwordHistory=new PasswordHistory();
                passwordHistory.setPasswordHash(newPasswordHash);
                passwordHistory.setUser(user);
                em.persist(passwordHistory);
            }

            user.setPassword(newPasswordHash);
            user.setChangePasswordAtNextLogon(false);

            // reset remember me for user
            Query query = em.createQuery("delete from sec$RememberMeToken rt where rt.user.id=:userId");
            query.setParameter("userId", userId);
            query.executeUpdate();


            tx.commit();
        } finally {
            tx.end();
        }
    }


}
