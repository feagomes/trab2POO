package entidades;

import documentos.Consulta;
import java.util.List; 
import java.util.Optional;
import java.util.Scanner;

/**
 * Classe responsável por representar a secretária da clínica.
 * * A secretária realiza operações administrativas relacionadas
 * aos pacientes, como:
 * - cadastro
 * - atualização de dados
 * - gerenciamento de informações pessoais
 */

public class Secretaria {
    private String nome;
    private Scanner sc = new Scanner(System.in);

    // construtor da classe secretária
    public Secretaria(String nome) {
        this.nome = nome;
    }

    /**
     * Realiza o cadastro de um paciente no sistema.
     * * O método solicita:
     * - nome
     * - convênio
     * - endereço
     * - nascimento
     * - telefone
     * - email
     */
    public Paciente CriaPaciente(List<Paciente> listaPacientes) {
        // solicita os dados do paciente, nome, convenio, telefone, endereço, data de nascimento e email.
        System.out.print("Digite o nome do paciente\n");
        String nome = sc.nextLine();
        System.out.print("Possui convênio particular/plano de saude\n");
        String convenio = sc.nextLine();
        System.out.print("Digite o endereço do paciente\n");
        String endereco = sc.nextLine();
        System.out.print("Digite a data de nascimento do paciente (dd/mm/aaaa)\n");
        String nascimento = sc.nextLine();
        System.out.print("Digite o telefone do paciente\n");
        String telefone = sc.nextLine();
        System.out.print("Digite o email do paciente\n");
        String email = sc.nextLine();
        //cria o objeto paciente
        Paciente p = new Paciente(nome, nascimento, endereco, email, telefone, convenio);
        // verifica se o paciente já está cadastrado, caso contrario, ele adiciona o paciente à lista
        if (this.RetornaPacientePeloTelefone(listaPacientes, telefone).isEmpty()) {
            listaPacientes.add(p);
        }
        return p;
    }
    
    /**
     * Atualiza informações de um paciente cadastrado.
     * 
     * Permite alterar:
     * - nome
     * - convênio
     * - endereço
     * - nascimento
     * - telefone
     */
    public void AtualizaPaciente(List<Paciente> ListaPaciente) {
        System.out.print("Digite o telefone do paciente\n");
        String telefone = sc.nextLine();

        Optional<Paciente> p = this.RetornaPacientePeloTelefone(ListaPaciente, telefone);
        if (p.isEmpty()) {
            System.out.print("Paciente não encontrado!");
            return;
        }
        
         // Recupera o paciente encontrado
        Paciente paciente = p.get();
        // Menu de atualização dos dados do paciente
        int opt = -1;
        while (opt != 0) {
            System.out.print("O que deseja alterar?\n1-nome\n2-convênio\n3-endereço\n4-nascimento\n5-telefone\n0-sair\n");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    System.out.print("Qual o novo nome?\n");
                    String novo_nome = sc.nextLine();
                    paciente.setNome(novo_nome);
                    break;
                case 2:

                    System.out.print("Qual o novo convenio?\n");
                    String novo_convenio = sc.nextLine();
                    paciente.setConvenio(novo_convenio);
                    break;
                case 3:
                    System.out.print("Qual o novo endereço?\n");
                    String novo_end = sc.nextLine();
                    paciente.setEndereco(novo_end);
                    break;
                case 4:
                    System.out.print("Qual o novo nascimento?\n");
                    String novo_nasc = sc.nextLine();
                    paciente.setNascimento(novo_nasc);
                    break;
                case 5:
                    System.out.print("Qual o novo telefone?\n");
                    String novo = sc.nextLine();
                    paciente.setTelefone(novo);
                    break;
                case 0:
            }
        }
    }
    public void RemovePaciente(List<Paciente> ListaPacientes) {
        System.out.print("Qual o telefone?\n");
        String telefone = sc.nextLine();
         // Busca o paciente pelo telefone
        Optional<Paciente> p = this.RetornaPacientePeloTelefone(ListaPacientes, telefone);
         // Remove o paciente caso exista
        if (!p.isEmpty()) {
            ListaPacientes.remove(p.get());
        } else {
            System.out.print("Paciente não existe!");
        }
    }

    /**
     * Cria uma nova consulta no sistema.
     * O método:
     * - localiza ou cadastra o paciente
     * - localiza ou cadastra o médico
     * - registra data, horário e tipo da consulta
     */
    public void CriaConsulta(List<Paciente> pacientes, List<Medico> medicos, List<Consulta> consultas) {
        
        System.out.print("Digite o telefone do Paciente:\n");
        String tele = sc.nextLine();
        Paciente p;
        // Verifica se o paciente já está cadastrado
        if (this.RetornaPacientePeloTelefone(pacientes, tele).isEmpty()) {
            System.out.print("Paciente nao cadastrado\n");
            // cria o paciente caso não exista
             p = this.CriaPaciente(pacientes);
        } else {
            //recupera o paciente pelo telefone
             p = this.RetornaPacientePeloTelefone(pacientes, tele).get();
        }
         // Solicita o nome do médico
        System.out.print("Digite o nome do Medico:\n");
        String nome_m = sc.nextLine();
        Medico m;
         // Verifica se o médico já está cadastrado
        if (this.RetornaMedicoPeloNome(medicos, nome_m).isEmpty()) {
            System.out.print("Medico nao cadastrado\n");
            m = this.CriaMedico(medicos);
        } else {
            //recupera o medico, caso exista
            m = this.RetornaMedicoPeloNome(medicos, nome_m).get();
        }
        // solicita os dados da consulta
        System.out.print("Digite a data (dd/mm/aaaa):");
        String data = sc.nextLine();
        System.out.print("Digite o horário (hh:mm):");
        String horario = sc.nextLine();
        System.out.print("Digite o tipo da consulta(normal/retorno):");
        String tipo = sc.nextLine();

        // Cria a consulta e adiciona a lista, caso não exista
        Consulta c = new Consulta(p, data, horario, m, tipo);

        if (!consultas.contains(c)) {
            consultas.add(c);
        }
    }

    /**
     * Atualiza informações de uma consulta cadastrada.
     * * Permite alterar:
     * - paciente
     * - data
     * - horário
     * - médico
     * - tipo da consulta
     */
    public void AtualizaConsulta(List<Consulta> consultas, List<Paciente> pacientes, List<Medico> medicos) {
        System.out.print("Digite o horario da consulta(Ex:13:24)\n");
        String horario = sc.nextLine();
        // verifica se a consulta existe
        if (this.RetornaConsultaPeloHorario(consultas, horario).isEmpty()) {
            System.out.print("Consulta não encontrada!");
            return;
        }
         // Recupera a consulta encontrada
        Consulta c = this.RetornaConsultaPeloHorario(consultas, horario).get();
        int opt = -1;
        // menu de atualização dos dados da consulta
        while (opt != 0) {
            System.out.print("O que deseja alterar?\n1-Paciente\n2-Data\n3-Horario\n4-Medico\n5-Tipo\n0-sair\n");
            opt = sc.nextInt();
            sc.nextLine();
            switch (opt) {
                case 1:
                    System.out.println("Digite o telefone do Paciente:\n");
                    String telefone = sc.nextLine();
                    if (this.RetornaPacientePeloTelefone(pacientes, telefone).isEmpty()) {
                        System.out.print("Paciente nao cadastrado\n");
                        this.CriaPaciente(pacientes);
                    }
                    Paciente paciente = this.RetornaPacientePeloTelefone(pacientes, telefone).get();
                    c.setP(paciente);
                    break;
                case 2:
                    System.out.print("Qual a nova data?\n");
                    String data = sc.nextLine();
                    c.setData(data);
                    break;
                case 3:
                    System.out.print("Qual o novo horario?\n");
                    String novo_horario = sc.nextLine();
                    c.setHorario(novo_horario);
                    break;
                case 4:
                    System.out.println("Digite o nome do Medico:\n");
                    String nome = sc.nextLine();
                    if (this.RetornaMedicoPeloNome(medicos, nome).isEmpty()) {
                        System.out.print("Medico nao cadastrado\n");
                        //cria o medico caso não exista
                        this.CriaMedico(medicos);
                    }
                    Medico medico = this.RetornaMedicoPeloNome(medicos, nome).get();
                    c.setM(medico);
                    break;
                case 5:
                    System.out.print("Qual o novo tipo?\n");
                    String tipo = sc.nextLine();
                    c.setTipo(tipo);
                    break;
                case 0:
                    break;
            }
        }
    }

    /**
     * Remove uma consulta do sistema.
     * * A remoção é realizada utilizando o horário da consulta.
     */
    public void remConsulta(List<Consulta> consultas) {
        System.out.print("Digite o horario da consulta(Ex:13:24)\n");
        String horario = sc.nextLine();
        if (this.RetornaConsultaPeloHorario(consultas, horario).isEmpty()) {
            System.out.print("Consulta não encontrada!");
            return;
        } else {
            consultas.remove(this.RetornaConsultaPeloHorario(consultas, horario).get());
        }
    }

    // realiza o cadastro do medico no sistema
    public Medico CriaMedico(List<Medico> listaMedicos) {
        System.out.print("Digite o nome do medico:\n");
        String nome = sc.nextLine();
        // cria o objeto medico
        Medico medico = new Medico(nome);
        // Verifica se o médico já está cadastrado 
        if (this.RetornaMedicoPeloNome(listaMedicos, nome).isEmpty()) {
            // adiciona o medico a lista
            listaMedicos.add(medico);
        }
        return medico;
    }

    /**
     * Exibe as consultas agendadas para o dia seguinte.
     * * O método separa:
     * - pacientes com dados de contato
     * - pacientes sem telefone e email cadastrados
     */
    public void ConsultasDiaSeguinte(List<Consulta> listaConsultas) {
        System.out.println("Digite o dia de amanha(dd/mm/aaaa):\n");
        String data = sc.nextLine();
        // Lista auxiliar para armazenar consultas do dia seguinte
        List<Consulta> consultas_seguintes = new java.util.ArrayList<>();
        // percorre todas as consultas
        for (Consulta c : listaConsultas) {
             // Verifica se a consulta pertence à data informada
            if (c.getData().equals(data)) {
                consultas_seguintes.add(c);
                System.out.println("Pacientes de amanha que possuem email ou telefone cadastrados:\n");
                // Verifica se o paciente possui contato cadastrado
                if (c.getP().getTelefone().length() > 0 || !c.getP().getEmail().equals("")) {
                    System.out.println(c.toString() + "\n");
                }
            }
        }
        System.out.println("Pacientes sem dados de contato:\n");
         // Exibe pacientes sem telefone e email
        for (Consulta c : consultas_seguintes) {
            if (c.getP().getTelefone().length() == 0 && c.getP().getEmail().equals("")) {
                System.out.println(c.toString() + "\n");
            }
        }
    }

    // Busca um paciente pelo telefone.
    private Optional<Paciente> RetornaPacientePeloTelefone(List<Paciente> ListaPacientes, String telefone) {
        for (Paciente p : ListaPacientes) {
            if (p.getTelefone().equals(telefone)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    // Busca uma consulta pelo horário.
    public Optional<Consulta> RetornaConsultaPeloHorario(List<Consulta> consultas, String horario) {
        for (Consulta c : consultas) {
            if (c.getHorario().equals(horario)) {
                return Optional.of(c);
            }
        }
         // Retorna vazio caso a consulta não seja encontrada
        return Optional.empty();
    }

    // busca o medico pelo nome
    public Optional<Medico> RetornaMedicoPeloNome(List<Medico> ListaMedicos, String nome) {
        for (Medico m : ListaMedicos) {
            if (m.getNome().equals(nome)) {
                return Optional.of(m);
            }
        }
        // retorna vazio caso não encontrado
        return Optional.empty();
    }
}