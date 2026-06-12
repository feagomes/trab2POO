package repositorios;

import entidades.Paciente;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Classe de CRUD da tabela de pacientes no banco
 */
public class PacienteRepository {

    //Metodo pra salvar/atualizar o paciente
    public void salvarPaciente(Paciente p) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //aqui é a operacao de salvar/atualizar mesmo (comeca -> salva/atualiza)
        Transaction operacao = sessao.beginTransaction();
        sessao.saveOrUpdate(p);
        //confirma se salvou/atualizou
        operacao.commit();
        //fecha o banco
        sessao.close();
    }

    //Metodo para excluir o paciente
    public void excluirPaciente(Paciente p) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //aqui é a operacao de deletar mesmo (comeca -> exclui)
        Transaction operacao = sessao.beginTransaction();
        sessao.delete(p);
        //confirma a exclusao
        operacao.commit();
        //fecha o banco
        sessao.close();
    }

    //Metodo para listar todos os pacientes
    public List<Paciente> buscarTodos() {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //pega os pacientes e coloca numa lista
        List<Paciente> lista = sessao.createQuery("from Paciente", Paciente.class).list();
        //fecha o banco
        sessao.close();
        return lista;
    }

    //Metodo pra pegar um paciente pelo telefone dele
    public Paciente buscarPorTelefone(String telefone) {
        //abre o banco
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //busca pelo telefone
        Paciente resultado = sessao.createQuery("from Paciente where telefone = :tel", Paciente.class).setParameter("tel", telefone).uniqueResult();                        
        //fecha o banco
        sessao.close();
        return resultado;
    }
}