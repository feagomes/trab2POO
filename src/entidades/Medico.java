package entidades;

import documentos.Consulta;
import documentos.RelatorioMed;
import documentos.Prontuario;
import java.util.ArrayList;
import java.util.List; 
import java.util.Optional;
import java.util.Scanner;
import javax.persistence.*;

/**
 * Classe responsável por representar um médico no sistema.
 * * O médico pode:
 * - realizar consultas
 * - gerenciar relatórios
 * - atualizar prontuários
 * - registrar enfermidades de pacientes
 */
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    @OneToMany(mappedBy = "m", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    private List<Consulta> consultas = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    private List<RelatorioMed> relatorios = new ArrayList<>();
    
    @Transient //esse transient é pro banco ignorar o scanner e a variavel clientes atendidos
    private Scanner sc = new Scanner(System.in);
    
    @Transient 
    private int clientesAtendidos;

    public Medico() {
    }

    /**
     * Construtor da classe Medico.
     */
    public Medico(String nome) {
        this.nome = nome;
    }
    // metodos gets e sets dos atributos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addConsultas(Consulta c) {
        this.consultas.add(c);
    }
    
    public int getClientesAtendidos() {
        return this.relatorios.size();
    }
    
    @Override
    public String toString() {
        return this.nome; 
    }

    /**
     * Gerencia informações adicionais do relatório médico do paciente.
     * * Permite:
     * - adicionar enfermidades
     * - remover enfermidades
     * - atualizar enfermidades
     */
    public void gerenciarRelatorioAdicional(List<Paciente> pacientes) {
        //solicita o telefone do paciente
        System.out.println("Digite o telefone do Paciente:\n");
        String telefone = sc.nextLine();
        // Verifica se o paciente existe
        if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            return;
        }
        // // Recupera o paciente encontrado
        Paciente paciente = this.RetornaPacientePeloTelefone(pacientes, telefone).get();
        int opt = -1;
        // // Menu de gerenciamento das enfermidades
        while (opt != 0) {
            System.out.print("1- Add enfermidades\n2- Remove enfermidades\n3- Atualizar enfermidades\n0- Sair\n");
            opt = sc.nextInt();
            switch (opt) {
                    //adiciona a enfermidade
                case 1:
                    System.out.print("Qual a enfermidade?\n");
                    String e = sc.nextLine();
                    paciente.addEnfermidade(e);
                    break;
                    //remove a enfermidade
                case 2:
                    System.out.print("Qual a enfermidade?\n");
                    String e_rem = sc.nextLine();
                    paciente.getEnfermidades().remove(e_rem);
                    break;
                    //atualiza a enfermidade
                case 3:
                    System.out.print("Qual a enfermidade?\n");
                    String e_att = sc.nextLine();
                    for (String enfermidade : paciente.getEnfermidades()) {
                        if (enfermidade.equals(e_att)) {
                            System.out.println(enfermidade + "\n");
                            System.out.println("Digite a nova versao:\n");
                            enfermidade = sc.nextLine();
                        }
                    }
                    break;
                case 0:
            }
            ;
        }
    }
    /**
     * Cadastra um prontuário para um paciente.
     * 
     * O método:
     * - localiza o paciente pelo telefone
     * - registra diagnóstico e prescrição
     * - permite adicionar sintomas ao prontuário
     */
    public void cadastraProtuario(List<Paciente> pacientes) {
        System.out.println("Digite o telefone do Paciente:\n");
        String telefone = sc.nextLine();
        if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            return;
        }
        // Recupera o paciente encontrado
        Paciente paciente = this.RetornaPacientePeloTelefone(pacientes, telefone).get();
        System.out.print("Digite o diagnostico do paciente\n");
        String diagnostico = sc.nextLine();
        System.out.print("Digite a prescricao\n");
        String prescricao = sc.nextLine();
        //Cria o prontuário do paciente
        paciente.setProntuario(new Prontuario(diagnostico, prescricao));
        // Loop para adicionar sintomas
        int opt = -1;
        while (opt != 0) {
            System.out.print("Deseja add mais sintomas?\nS-1\nN-0");
            opt = sc.nextInt();
            switch (opt) {
                    // Adiciona sintoma ao prontuário
                case 1:
                    System.out.print("Qual o sintomas?\n");
                    paciente.getProntuario().addSintoma(sc.nextLine());
                    break;
                case 0:
                    break;
            }
            ;
        }
    }

    public void attProntuario(List<Paciente> pacientes) {
        System.out.println("Digite o telefone do Paciente:\n");
        String telefone = sc.nextLine();
        if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            return;
        }
        // menu do prontuario
        Paciente paciente = this.RetornaPacientePeloTelefone(pacientes, telefone).get();
        int opt = -1;
        while (opt != 0) {
            //  // Menu de atualização do prontuário
            System.out.println("O que deseja alterar?\n1- Diagnostico\n2- Prescricao\n3- Sintomas\n0- Sair\n");
            opt = sc.nextInt();
            switch (opt) {
            // Atualiza diagnóstico
                case 1:
                    System.out.println("Digite o novo diagnostico:\n");
                    paciente.getProntuario().setDiagnostico(sc.nextLine());
                    break;
            //Atualiza prescrição
                case 2:
                    System.out.println("Digite a nova prescricao:\n");
                    paciente.getProntuario().setPrescricao(sc.nextLine());
                    break;
            // Gerencia sintomas do prontuário
                case 3:
                    int opt2 = -1;
                    while (opt2 != 0) {
                        System.out.println("Deseja:\n1- Remover um sintoma\n2- Adicionar sintoma\n0- Sair\n");
                        opt2 = sc.nextInt();
                        switch (opt2) {
                             // Remove sintoma
                            case 1:
                                System.out.println("Digite o sintomas a ser removido:\n");
                                paciente.getProntuario().getSintomas().remove(sc.nextLine());
                                break;
                            // Adiciona sintoma
                            case 2:
                                System.out.println("Digite o sintoma a ser adicionada:\n");
                                paciente.getProntuario().addSintoma(sc.nextLine());
                                break;
                            case 0:
                                break;
                        }
                    }
                    break;
                case 0:
            }
        }

    }

    public void remProntuario(List<Paciente> pacientes) {
        System.out.println("Digite o telefone do Paciente:\n");
        String telefone = sc.nextLine();
        if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            return;
        }
        ;
        this.RetornaPacientePeloTelefone(pacientes, telefone).get().resetProntuario();
    }

    /**
     * Gerencia a emissão de relatórios médicos.
     * * O relatório pode conter:
     * - receita médica
     * - atestado
     * - declaração de acompanhamento
     */
    public void gerarRelatorioMedico(List<Paciente> pacientes) {
        System.out.println("Digite a receita da consulta:\n");
        String receita = sc.nextLine();
        System.out.println("Digite o atestado da consulta:\n");
        String atestado = sc.nextLine();
        System.out.println("Digite o trabalho de acompanhamento:\n");
        String acompanhamento = sc.nextLine();
        System.out.println("Digite o telefone do Paciente:\n");
        String telefone = sc.nextLine();
        if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            return;
        }
        Paciente paciente = this.RetornaPacientePeloTelefone(pacientes, telefone).get();
        // Cria o relatório médico
        RelatorioMed relatorioMed = new RelatorioMed(receita, atestado, acompanhamento, paciente.getNome());
        this.relatorios.add(relatorioMed);
    }

    // Busca um paciente pelo telefone.
    private Optional<Paciente> RetornaPacientePeloTelefone(List<Paciente> ListaPacientes, String telefone) {
        // Percorre a lista de pacientes e verifica se o telefone coincide
        for (Paciente p : ListaPacientes) {
            if (p.getTelefone().equals(telefone)) {
                return Optional.of(p);
            }
        }
        // retorna vazio caso não seja encontrado
        return Optional.empty();
    }

    //Getter e setter pro ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}