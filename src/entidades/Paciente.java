package entidades;

import documentos.Prontuario;
import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;
import javax.persistence.*; 

/**
 * Classe responsável por representar um paciente no sistema da clínica.
 * * A classe armazena informações pessoais, prontuário médico,
 * mensagens e histórico de enfermidades do paciente.
 */

@Entity
@Table(name = "pacientes")
public class Paciente {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String nascimento;
    private String endereco;
    private String email;
    private String telefone;
    private String convenio;

    @OneToOne(cascade = CascadeType.ALL) 
    private Prontuario prontuario;

    @ElementCollection 
    private List<String> mensagens = new ArrayList<>();

    @ElementCollection 
    private List<String> enfermidades = new ArrayList<>();
    
    //adicionei essas doencas pra facilitar na tela, se nao na lista ia ficar muito confusa
    private boolean fuma;
    private boolean bebe;
    private boolean colesterol;
    private boolean doencaCard;
    private boolean diabete;
    private String cirurgias;
    private String alergias;
    
    @Transient 
    private Scanner sc = new Scanner(System.in);
    
    
    public Paciente() {
    }

    // Construtor da classe paciente com os parametros: nome, data de nascimento, endereço, email, telefone e convenio
    public Paciente (String nome, String nascimento, String endereco, String email, String telefone, String convenio) {
        this.nome = nome;
        this.convenio = convenio;
        this.endereco = endereco;
        this.nascimento = nascimento;
        this.email = email;
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return this.nome; 
    }

    // remove o paciente do prontuario
    public void resetProntuario (){
        this.prontuario = null;
    }
    // metodos gets e sets da classe Paciente
    public Prontuario getProntuario() {
        return prontuario;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void setProntuario(Prontuario pontuario) { 
        this.prontuario = pontuario;
    }

    public void addEnfermidade (String e){
        this.enfermidades.add(e);
    }

    public List<String> getEnfermidades(){
        return this.enfermidades;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getConvenio() {
        return convenio;
    }

    public String getEndereco() {
        return endereco;
    }
    
    //Getter e setter pro ID do banco
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFuma() {
        return fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public boolean isBebe() {
        return bebe;
    }

    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    public boolean isColesterol() {
        return colesterol;
    }

    public void setColesterol(boolean colesterol) {
        this.colesterol = colesterol;
    }

    public boolean isDoencaCard() {
        return doencaCard;
    }

    public void setDoencaCard(boolean doencaCard) {
        this.doencaCard = doencaCard;
    }

    public boolean isDiabete() {
        return diabete;
    }

    public void setDiabete(boolean diabete) {
        this.diabete = diabete;
    }

    public String getCirurgias() {
        return cirurgias;
    }

    public void setCirurgias(String cirurgias) {
        this.cirurgias = cirurgias;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
    
    
    
}