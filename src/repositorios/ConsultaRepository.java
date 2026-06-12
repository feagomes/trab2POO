package repositorios;

import documentos.Consulta;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Classe de CRUD do banco de consultas
 */
public class ConsultaRepository {

    //Metodo pra salvar/atualizar a consulta
    public void salvarConsulta(Consulta c) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //aqui é a operacao de salvar/atualizar mesmo (comeca -> salva/atualiza)
        Transaction operacao = sessao.beginTransaction();
        sessao.saveOrUpdate(c);
        //confirma se salvou/atualizou
        operacao.commit();
        //fecha o banco
        sessao.close();
    }

    //Metodo para excluir a consulta
    public void excluirConsulta(Consulta c) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //aqui é a operacao de deletar mesmo (comeca -> exclui)
        Transaction operacao = sessao.beginTransaction();
        sessao.delete(c);
        //confirma a exclusao
        operacao.commit();
        //fecha o banco
        sessao.close();
    }

    //Metodo para listar todas as consultas 
    public List<Consulta> buscarTodos() {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //pega as consultas e coloca numa lista
        List<Consulta> lista = sessao.createQuery("from Consulta", Consulta.class).list();
        //fecha o banco
        sessao.close();
        return lista;
    }

    //Metodo pra pegar uma consulta pelo horario dela
    public Consulta buscarPorHorario(String horario) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //busca pelo horario
        Consulta resultado = sessao.createQuery("from Consulta where horario = :horario", Consulta.class).setParameter("horario", horario).uniqueResult();                        
        //fecha o banco
        sessao.close();
        return resultado;
    }
}