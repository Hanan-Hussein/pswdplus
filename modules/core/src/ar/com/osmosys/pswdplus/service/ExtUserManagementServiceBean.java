package ar.com.osmosys.pswdplus.service;

import ar.com.osmosys.pswdplus.config.PswdConfig;
import ar.com.osmosys.pswdplus.entity.PasswordHistory;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.EntityAccessException;
import com.haulmont.cuba.security.app.UserManagementServiceBean;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

public class ExtUserManagementServiceBean extends UserManagementServiceBean {

    @Inject
    PswdConfig pswdConfig;

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
                passwordHistory.setCreatedAt(new Date());
                passwordHistory.setPasswordHash(newPasswordHash);
                passwordHistory.setUser(user);
                em.persist(passwordHistory);

                Query qCount=em.createQuery("select count(ph) from pswdplus$PasswordHistory ph where ph.user.id=:userId");
                qCount.setParameter("userId",userId);
                long historicPasswordsCount=(Long)qCount.getSingleResult();

                if(historicPasswordsCount>=pswdConfig.getPswdHistoryLength())
                {
                    Query qOldest=em.createQuery("select ph from pswdplus$PasswordHistory ph where ph.user.id=:userId order by ph.createdAt");
                    qOldest.setParameter("userId", userId);
                    PasswordHistory ph=(PasswordHistory) qOldest.getFirstResult();
                    em.setSoftDeletion(false);
                    em.remove(ph);
                }

            }

            user.setPassword(newPasswordHash);
            user.setChangePasswordAtNextLogon(false);

            // reset remember me for user
            Query query = em.createQuery("delete from sec$RememberMeToken rt where rt.user.id=:userId");
            query.setParameter("userId", userId);
            query.executeUpdate();

            tx.commit();

            //missing catch in case of query errors...

        } finally {
            tx.end();
        }
    }


}
