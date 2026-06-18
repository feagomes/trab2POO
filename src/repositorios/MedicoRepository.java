package repositorios;

import entidades.Medico;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class MedicoRepository {

    //Deixei o metodo contrutor do gerenciador dentro de cada funcao nos 3 repositorios
    //ja que tem que fechar de qualquer jeito, se pensarem em algum metodo mais eficiente 
    //colocando ele fora pra evitar varias conexoes pode colocar
    
    //Metodo pra salvar/atualizar o medico
    public void salvarMedico(Medico m) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //salva ou atualiza os dados
        gerenciador.merge(m);
        //confirma se salvou/atualizou
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para listar todos os medicos 
    public List<Medico> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega os medicos e coloca numa lista
        List<Medico> lista = gerenciador.createQuery("from Medico", Medico.class).getResultList();
        //fecha o banco
        gerenciador.close();
        return lista;
    }

    //Metodo pra pegar um medico pelo nome dele
    public Medico buscarPorNome(String nome) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca pelo nome jogando numa lista pra nao dar erro se nao achar nada
        List<Medico> resultado = gerenciador.createQuery("from Medico where nome = :nome", Medico.class).setParameter("nome", nome).getResultList();                        
        //fecha o banco
        gerenciador.close();
        // se nao achou nada devolve nulo
        if (resultado.isEmpty()) {
            return null;
        }
        // se achou devolve o primeiro
        return resultado.get(0);
    }
    
    //Metodo pra buscar o medico pelo ID pro login 
    public Medico buscarPorId(Long id) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca o medico pelo ID
        Medico m = gerenciador.find(Medico.class, id);
        //fecha o banco
        gerenciador.close();
        return m;
    }
}