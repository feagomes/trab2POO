package repositorios;

import documentos.Prontuario;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class ProntuarioRepository {
     /**
     * Salva um novo prontuário ou atualiza um prontuário já existente.
     */
    public void salvar(Prontuario p) {
        // Obtém uma conexão com o banco através do EntityManager
        EntityManager gerenciador = HibernateUtil.getEntityManager();
         // Inicia uma transação para permitir alterações no banco
        gerenciador.getTransaction().begin();
        // Insere ou atualiza o prontuário na base de dados
        gerenciador.merge(p);
        //confirma se salvou/atualizou
        gerenciador.getTransaction().commit();
        //fecha o banco, ou seja, libera os recursos usados na conexão
        gerenciador.close();
    }

    //Metodo para listar todos os prontuarios
    public List<Prontuario> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega os prontuarios e o adiciona em uma lista
        List<Prontuario> lista = gerenciador.createQuery("from Prontuario", Prontuario.class).getResultList();
        //fecha o banco
        gerenciador.close();
        return lista;
    }

    //Metodo pra pegar um prontuario pelo ID
    public Prontuario buscarPorId(Long id) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca pelo ID
        Prontuario resultado = gerenciador.find(Prontuario.class, id);                        
        //fecha o banco
        gerenciador.close();
        return resultado;
    }
}
