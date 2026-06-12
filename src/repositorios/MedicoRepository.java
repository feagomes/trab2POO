package repositorios;

import entidades.Medico;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Classe de CRUD da tabela de medicos no banco
 */
public class MedicoRepository {

    //Metodo pra salvar/atualizar o medico
    public void salvarMedico(Medico m) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //aqui é a operacao de salvar/atualizar mesmo (comeca -> salva/atualiza)
        Transaction operacao = sessao.beginTransaction();
        sessao.saveOrUpdate(m);
        //confirma se salvou/atualizou
        operacao.commit();
        //fecha o banco
        sessao.close();
    }

    //Metodo para listar todos os medicos 
    public List<Medico> buscarTodos() {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //pega os medicos e coloca numa lista
        List<Medico> lista = sessao.createQuery("from Medico", Medico.class).list();
        //fecha o banco
        sessao.close();
        return lista;
    }

    //Metodo pra pegar um medico pelo nome dele
    public Medico buscarPorNome(String nome) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //busca pelo nome
        Medico resultado = sessao.createQuery("from Medico where nome = :nome", Medico.class).setParameter("nome", nome).uniqueResult();                        
        //fecha o banco
        sessao.close();
        return resultado;
    }
}