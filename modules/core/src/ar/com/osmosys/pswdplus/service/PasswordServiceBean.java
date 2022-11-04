package ar.com.osmosys.pswdplus.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Service(PasswordService.NAME)
public class PasswordServiceBean implements PasswordService {


    @Inject
    private Persistence persistence;

    @Override
    public boolean isPasswordInHistory(UUID userId, String passwordHash) {
        boolean result = false;

        Transaction tx = persistence.createTransaction();

        EntityManager em = persistence.getEntityManager();
        Query query = em.createQuery("select ph.passwordHash from pswdplus$PasswordHistory ph where ph.user.id=:userId");

        query.setParameter("userId", userId);
        List<String> passwordhash = query.getResultList();
        tx.commit();

        long count = 0;
        if (passwordhash.size() > 0) {

            for (String h : passwordhash) {
                if (BCrypt.checkpw(passwordHash, h)) // check password on password history
                    count += 1;
            }
            if (count > 0)
                result = true;
        }
        return result;
    }
}