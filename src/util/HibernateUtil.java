package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe pra iniciar a conexao com o banco
 */
public class HibernateUtil {
    //cria a conexao com o banco
    private static final SessionFactory conexao = new Configuration().configure().buildSessionFactory();
    //retorna a conexao pras operacoes usarem
    public static SessionFactory getSessionFactory() {
        return conexao;
    }
}