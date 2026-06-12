
import documentos.Consulta;
import entidades.Medico;
import entidades.Paciente;
import entidades.Secretaria;
import services.GerenciadorMensagens;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Classe principal do sistema da clínica médica.
 * 
 * A classe é responsável por:
 * - iniciar o sistema
 * - armazenar listas principais
 * - controlar os menus
 * - gerenciar o fluxo de execução da aplicação
 */
public class ClinicaMed {
    
    public static void main(String[] args) {
        // Lista de pacientes cadastrados
        ArrayList<Paciente> pacientes = new ArrayList<>();
        // Lista de consultas cadastradas
        ArrayList<Consulta> consultas = new ArrayList<>();
         // Lista de médicos cadastrados
        ArrayList<Medico> medicos = new ArrayList<>();
        // Responsável pelo envio de mensagens
        GerenciadorMensagens mensageiro = new GerenciadorMensagens();
         // Scanner para leitura de dados do terminal
        Scanner sc = new Scanner(System.in);
        int opcao = -1;
         // Loop principal do sistema
        while (opcao != 0) {
            System.out.println("1 - Menu da Secretária");
            System.out.println("2 - Menu do Médico");
            System.out.println("3 - Enviar mensagens");
            System.out.println("0 - Sair do Sistema");
            opcao = sc.nextInt();
            sc.nextLine(); // CORREÇÃO: Consome o "Enter", ou seja, consome a quebra de linha deixada pelo nextInt()

            switch (opcao) {
                // Acessa o menu da secretária
                case 1:
                    System.out.print("Digite o nome da secretaria: ");
                    // cria a secretária 
                    Secretaria secretaria = new Secretaria(sc.nextLine());
                    menuSecretaria(secretaria,consultas,medicos,pacientes);
                    break;
                // menu do medico
                case 2:
                    menuMedico(medicos, pacientes);
                    break;
                // envia mensagens
                case 3:
                    System.out.print("Digite a data para enviar os avisos (dd/mm/aaaa): ");
                    String data = sc.nextLine();
                    mensageiro.dispararLembretesDeConsulta(consultas, data);
                    break;
                // encerra o sistema
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
            }
        }
    }
    /**
     * Exibe e controla o menu da secretária.
     * 
     * Permite:
     * - cadastrar pacientes
     * - atualizar pacientes
     * - remover pacientes
     * - criar consultas
     * - atualizar consultas
     * - remover consultas
     * - visualizar consultas do dia seguinte
     */
    private static void menuSecretaria(Secretaria sec, ArrayList<Consulta> consultas, ArrayList<Medico> medicos, ArrayList<Paciente> pacientes) {
        Scanner sc = new Scanner(System.in);
        int opt = -1;
        // loop percorrendo o menu da secretária
        while (opt != 0) {
            System.out.println("1 - Cadastrar Paciente");
            System.out.println("2 - Atualizar Paciente");
            System.out.println("3 - Remover Paciente");
            System.out.println("4 - Agendar Consulta");
            System.out.println("5 - Atualizar Consulta");
            System.out.println("6 - Remover Consulta");
            System.out.println("7 - Relatório de Consultas do dia Seguinte");
            System.out.println("0 - Voltar");
            opt = sc.nextInt();
            switch (opt) {
                // cadastro do paciente, criando, atualizando, removendo, criando a consulta, atualizando-a, removendo-a e acessando a consulta no dia seguinte
                case 1: sec.CriaPaciente(pacientes); break;
                case 2: sec.AtualizaPaciente(pacientes); break;
                case 3: sec.RemovePaciente(pacientes); break;
                case 4: sec.CriaConsulta(pacientes, medicos, consultas); break;
                case 5: sec.AtualizaConsulta(consultas, pacientes, medicos); break;
                case 6: sec.remConsulta(consultas); break;
                case 7: sec.ConsultasDiaSeguinte(consultas); break;
                case 0: break;
            }
        }
    }
     /**
     * Exibe e controla o menu do médico.
     * 
     * O método:
     * - realiza login do médico
     * - cria um médico caso ele não exista
     */
    private static void menuMedico(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nDigite o nome do Médico para acessar o sistema: ");
        String nomeMedico = sc.nextLine();
        //médico está logado
        Medico login = null;
        //procura o médico na lista
        for (Medico m : medicos) {
            if (m.getNome().equals(nomeMedico)) {
                login = m;
                break;
            }
        }
        // Caso o médico não exista, cria um novo cadastro
        if (login == null) {
            Medico m = new Medico(nomeMedico);
            login = m;
            medicos.add(m);
        }

        // opções de acesso ao menu do médico: acesso ao gerenciador, cadastro, atualização, remover, gerar e voltar
        int opt = -1;
        while (opt != 0) {
            System.out.println("1 - Gerenciar Dados Adicionais de Saúde");
            System.out.println("2 - Cadastrar Prontuário");
            System.out.println("3 - Atualizar Prontuário");
            System.out.println("4 - Remover Prontuário");
            System.out.println("5 - Gerar Relatório Médico");
            System.out.println("0 - Voltar");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1: login.gerenciarRelatorioAdicional(pacientes); break;
                case 2: login.cadastraProtuario(pacientes); break;
                case 3: login.attProntuario(pacientes); break;
                case 4: login.remProntuario(pacientes); break;
                case 5: login.gerarRelatorioMedico(pacientes); break;
                case 0: break;
            }
        }
    }
}
