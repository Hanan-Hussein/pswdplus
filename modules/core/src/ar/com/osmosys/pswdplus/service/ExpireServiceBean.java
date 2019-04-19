package ar.com.osmosys.pswdplus.service;

import ar.com.osmosys.pswdplus.config.PswdConfig;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Component(ExpireService.NAME)
public class ExpireServiceBean implements ExpireService, Callable {


    @Inject
    PswdConfig pswdConfig;

    @Inject
    Persistence persistence;



    public String expireOldPasswords()
    {

        if(pswdConfig.getUsePswdExpiration())
        {

            int days=pswdConfig.getDaysToPswdExpiration();
            String expiredUsers="Expired users:";
            Transaction tx=persistence.createTransaction();
            EntityManager em=persistence.getEntityManager();
            List<User> users;

            users=em.createQuery("select u from sec$User u where u.changePasswordAtNextLogon<>1").getResultList();
            Query qLatestPwdChange=em.createQuery("select max(ph.createdAt) from pswdplus$PasswordHistory ph where ph.user.id=:userId");

            for (User user:users)
            {
                qLatestPwdChange.setParameter("userId", user.getId());
                Date lastPasswordChange=(Date)qLatestPwdChange.getSingleResult();
                if (isPasswordExpired(lastPasswordChange,days))
                {
                    user.setChangePasswordAtNextLogon(true);
                    expiredUsers=expiredUsers + user.getLogin() + ",";
                }

            }

            tx.commit();

        }

        return null;
    }

    private boolean isPasswordExpired(Date lastChange, int validityDays)
    {
        long diff=new Date().getTime() - lastChange.getTime();
        long days= TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        long validityDaysLong=validityDays;

        return (days>validityDays);

    }

    public Object call() throws Exception
    {
        return null;
    }




}