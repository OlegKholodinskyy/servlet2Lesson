package exception;

import org.hibernate.HibernateException;

/**
 * Created by oleg on 03.07.2019.
 */
public class RepoAccessEcxeption extends Exception {
    public RepoAccessEcxeption (String measge){
        super(measge);
    }
}
