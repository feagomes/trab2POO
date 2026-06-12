package documentos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Table(name = "prontuarios")
public class Prontuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Lista de sintomas registrados no prontuário
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> sintomas = new ArrayList<>(); 
    
    // Diagnóstico médico do paciente
    private String diagnostico;
    // Prescrição médica recomendada
    private String prescricao;
    
    /**
     * Construtor que inicializa o prontuário com diagnóstico e prescrição.
     */
    public Prontuario(String diagnostico, String prescricao) {
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
    }

    // construtor vazio exigido pelo Hibernate
    public Prontuario() {}

    // Adiciona um sintoma ao prontuário do paciente
    public void addSintoma(String s){
        this.sintomas.add(s);
    }

    // métodos gets e sets
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public List<String> getSintomas() {
        return sintomas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}