package ar.com.osmosys.pswdplus.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(PasswordService.NAME)
public class PasswordServiceBean implements PasswordService {


    @Inject
    Persistence persistence;

    @Override
    public boolean isPasswordInHistory(UUID userId, String passwordHash)
    {
        boolean result=false;

        Transaction tx=persistence.createTransaction();
        EntityManager em=persistence.getEntityManager();

        Query qCount=em.createQuery("select count(ph) from pswd$PasswordHistory ph where ph.user.id=:userId and ph.passwordHash=:passwordHash");
        qCount.setParameter("userId",userId);
        qCount.setParameter("passwordHash",passwordHash);
        long count=(Long) qCount.getSingleResult();

        if(count>0)
            result=true;

        return result;

    }


}