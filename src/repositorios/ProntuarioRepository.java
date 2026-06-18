package repositorios;

import documentos.Prontuario;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class ProntuarioRepository {

    //Metodo pra salvar/atualizar o prontuario
    public void salvar(Prontuario p) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //salva ou atualiza os dados
        gerenciador.merge(p);
        //confirma se salvou/atualizou
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para listar todos os prontuarios
    public List<Prontuario> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega os prontuarios e coloca numa lista
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