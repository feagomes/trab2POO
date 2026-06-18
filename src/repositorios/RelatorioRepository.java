package repositorios;

import documentos.RelatorioMed;
import java.util.List;
import javax.persistence.EntityManager;
import util.HibernateUtil;

public class RelatorioRepository {

    //Metodo pra salvar/atualizar o relatorio
    public void salvar(RelatorioMed r) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //comeca a operacao
        gerenciador.getTransaction().begin();
        //salva ou atualiza os dados
        gerenciador.merge(r);
        //confirma se salvou/atualizou
        gerenciador.getTransaction().commit();
        //fecha o banco
        gerenciador.close();
    }

    //Metodo para listar todos os relatorios
    public List<RelatorioMed> buscarTodos() {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //pega os relatorios e coloca numa lista
        List<RelatorioMed> lista = gerenciador.createQuery("from RelatorioMed", RelatorioMed.class).getResultList();
        //fecha o banco
        gerenciador.close();
        return lista;
    }

    //Metodo pra pegar um relatorio pelo ID
    public RelatorioMed buscarPorId(Long id) {
        //abre o banco
        EntityManager gerenciador = HibernateUtil.getEntityManager();
        //busca pelo ID
        RelatorioMed resultado = gerenciador.find(RelatorioMed.class, id);                        
        //fecha o banco
        gerenciador.close();
        return resultado;
    }
}