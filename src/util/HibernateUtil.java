package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    // cria a fabrica usando o nome do persistence-unit (depende de como vc cria o banco)
    //tem uma seção no persistence.xml, quando for rodar troca la tambem
    //(alem do nome do banco, user e senha)
    private static final EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("trab2pooPU");

    // abre e devolve o gerenciador do banco (EntityManager)
    //ele que vai pegar, atualizar, excluir e adicionar no banco
    //dai em vez de criar a EntityManagerFactory toda vez em toda classe, cria o manager pra eficiencia
    public static EntityManager getEntityManager() {
        return fabrica.createEntityManager();
    }
}