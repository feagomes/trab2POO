package documentos;

import javax.persistence.*; 

/**
 * Classe responsável por representar um relatório médico.
 * * O relatório pode conter:
 * - receita médica
 * - atestado
 * - declaração de acompanhamento
 * - nome do paciente
 */
@Table(name = "relatorios_medicos") 
public class RelatorioMed {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receita;
    private String atestado;
    private String acompanhamento;
    private String cliente;

    
    public RelatorioMed() {}

    //construtor da classe RelatorioMed, pede receita, atestado, aompanhamento e o cliente.
    public RelatorioMed(String receita, String atestado, String acompanhamento, String cliente) {
        this.receita = receita;
        this.atestado = atestado;
        this.acompanhamento = acompanhamento;
        this.cliente = cliente;
    }    

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}