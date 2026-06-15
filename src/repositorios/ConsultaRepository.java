package repositorios;

import documentos.Consulta;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class ConsultaRepository {

    //Metodo pra salvar/atualizar a consulta
    public void salvarConsulta(Consulta c) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //salva ou atualiza os dados
        gerenciador.merge(c);
        //confirma se salvou/atualizou
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para excluir a consulta
    public void excluirConsulta(Consulta c) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //acha a consulta pelo id pra apagar com seguranca
        Consulta alvo = gerenciador.find(Consulta.class, c.getId());
        if (alvo != null) {
            gerenciador.remove(alvo);
        }
        //confirma a exclusao
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para listar todas as consultas 
    public List<Consulta> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega as consultas e coloca numa lista
        List<Consulta> lista = gerenciador.createQuery("from Consulta", Consulta.class).getResultList();
        //fecha o banco
        gerenciador.close();
        return lista;
    }

    //Metodo pra pegar uma consulta pelo horario dela
    public Consulta buscarPorHorario(String horario) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca pelo horario jogando numa lista pra nao dar erro se nao achar nada
        List<Consulta> resultado = gerenciador.createQuery("from Consulta where horario = :horario", Consulta.class).setParameter("horario", horario).getResultList();                        
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