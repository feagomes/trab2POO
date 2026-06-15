package repositorios;

import entidades.Paciente;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class PacienteRepository {

    //Metodo pra salvar/atualizar o paciente
    public void salvarPaciente(Paciente p) {
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

    //Metodo para excluir o paciente
    public void excluirPaciente(Paciente p) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //acha o paciente pelo id pra apagar com seguranca
        Paciente alvo = gerenciador.find(Paciente.class, p.getId());
        if (alvo != null) {
            gerenciador.remove(alvo);
        }
        //confirma a exclusao
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para listar todos os pacientes
    public List<Paciente> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega os pacientes e coloca numa lista
        List<Paciente> lista = gerenciador.createQuery("from Paciente", Paciente.class).getResultList();
        //fecha o banco
        gerenciador.close();
        return lista;
    }

    //Metodo pra pegar um paciente pelo telefone dele
    public Paciente buscarPorTelefone(String telefone) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca pelo telefone jogando numa lista pra nao dar erro se nao achar nada
        List<Paciente> resultado = gerenciador.createQuery("from Paciente where telefone = :tel", Paciente.class).setParameter("tel", telefone).getResultList();                        
        //fecha o banco
        gerenciador.close();
        // se nao achou nada devolve nulo
        if (resultado.isEmpty()) {
            return null;
        }
        // se achou devolve o primeiro
        return resultado.get(0);
    }
}