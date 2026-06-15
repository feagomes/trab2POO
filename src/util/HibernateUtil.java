package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    
    // cria a fabrica usando o nome do seu persistence-unit
    private static final EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("trab2pooPU");

    // abre e devolve o gerenciador do banco (EntityManager)
    public static EntityManager getEntityManager() {
        return fabrica.createEntityManager();
    }
}