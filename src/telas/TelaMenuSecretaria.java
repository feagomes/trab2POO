package telas;

import documentos.Consulta;
import entidades.Paciente;
import repositorios.ConsultaRepository;
import repositorios.PacienteRepository;
import services.GerenciadorMensagens;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TelaMenuSecretaria extends JFrame {

    private JTabbedPane abas;
    private JTable tabelaPacientes;
    private JTable tabelaConsultas;

    public TelaMenuSecretaria() {
        setTitle("Painel da Secretaria");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        abas = new JTabbedPane();

        //configura a aba de pacientes
        tabelaPacientes = new JTable();
        atualizarTabelaPacientes();
        abas.addTab("Pacientes cadastrados", new JScrollPane(tabelaPacientes));

        //configura a aba de consultas
        tabelaConsultas = new JTable();
        atualizarTabelaConsultas();
        abas.addTab("Consultas marcadas", new JScrollPane(tabelaConsultas));

        add(abas, BorderLayout.CENTER);

        //botoes embaixo
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoMensagem = new JButton("Mensagem");
        JButton botaoNovoMedico = new JButton("Cadastrar Medico");

        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoMensagem);
        painelBotoes.add(botaoNovoMedico);
        add(painelBotoes, BorderLayout.SOUTH);

        //Adicionar simples
        botaoAdicionar.addActionListener(e -> {
            if (abas.getSelectedIndex() == 0) {
                new CadastroPaciente(this, null).setVisible(true);
                atualizarTabelaPacientes();
            } else {
                new AgendarConsultas(this).setVisible(true);
                atualizarTabelaConsultas();
            }
        });

        //Atualizar: Aqui tambem fiz um pra cada aba, mas na consulta achei melhor nao dar pra editar  
        //Se acharem melhor eu posso mudar depois pegando a consulta pela data e horario
        botaoAtualizar.addActionListener(e -> {
            if (abas.getSelectedIndex() == 0) {
                int linha = tabelaPacientes.getSelectedRow();
                if (linha >= 0) {
                    Long id = (Long) tabelaPacientes.getValueAt(linha, 0);
                    //pra achar o paciente pelo ID
                    Paciente pacienteEditar = null;
                    for (Paciente p : new PacienteRepository().buscarTodos()) {
                        if (p.getId().equals(id)) {
                            pacienteEditar = p;
                        }
                    }
                    if (pacienteEditar != null) {
                        new CadastroPaciente(this, pacienteEditar).setVisible(true);
                        atualizarTabelaPacientes();
                    }
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela");
                }
            } 
            else {
                System.out.println("Nao e possivel atualizar uma consulta");
            }
        });

        //Excluir: fiz um pra consulta e um pra paciente, dai na consulta ele deleta só a ultiam criada
        botaoExcluir.addActionListener(e -> {
            String telefone = JOptionPane.showInputDialog("Buscar pelo telefone:");
            if (telefone != null && !telefone.isEmpty()) {
                if (abas.getSelectedIndex() == 0) {
                    PacienteRepository pr = new PacienteRepository();
                    Paciente p = pr.buscarPorTelefone(telefone);
                    if (p != null) {
                        pr.excluirPaciente(p);
                        atualizarTabelaPacientes();
                        JOptionPane.showMessageDialog(this, "Paciente excluido");
                    } else {
                        JOptionPane.showMessageDialog(this, "Paciente nao encontrado");
                    }
                } else {
                    ConsultaRepository cr = new ConsultaRepository();
                    Consulta ultima = null;
                    //busca a consulta mais recente do paciente pelo telefone
                    for (Consulta c : cr.buscarTodos()) {
                        if (c.getP() != null && telefone.equals(c.getP().getTelefone())) {
                            ultima = c; 
                        }
                    }
                    if (ultima != null) {
                        cr.excluirConsulta(ultima);
                        atualizarTabelaConsultas();
                        JOptionPane.showMessageDialog(this, "Consulta excluida");
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada");
                    }
                }
            }
        });

        //Pra enviar a mensagem
        botaoMensagem.addActionListener(e -> {
            String amanha = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ConsultaRepository consRepositorio = new ConsultaRepository();
            GerenciadorMensagens mensagem = new GerenciadorMensagens();
            
            List<Consulta> envios = new ArrayList<>();            
            for (Consulta c : consRepositorio.buscarTodos()) {
                if (c.getData() != null && c.getData().equals(amanha) && c.getP() != null) {
                    
                    String email = c.getP().getEmail();
                    String tel = c.getP().getTelefone();
                    
                    boolean temEmail = email != null && !email.isEmpty();
                    boolean temTel = tel != null && !tel.isEmpty();
                    
                    if (temEmail || temTel) {
                        if (temEmail) {
                            System.out.println("Para " + email + " :");
                        }
                        if (temTel) {
                            System.out.println("Para " + tel + " :");
                        }
                        envios.add(c);
                    }
                }
            }
            
            if (!envios.isEmpty()) {
                mensagem.dispararLembretesDeConsulta(envios, amanha);
            }
            
            JOptionPane.showMessageDialog(this, "Mensagens enviadas no console");
        });
        
        //Cadastrar o medico
        botaoNovoMedico.addActionListener(e -> {
            String nomeMedico = JOptionPane.showInputDialog(this, "Digite o nome do novo medico:");
            if (nomeMedico != null && !nomeMedico.trim().isEmpty()) {
                entidades.Medico m = new entidades.Medico();
                m.setNome(nomeMedico);
                //salva no banco
                new repositorios.MedicoRepository().salvarMedico(m);
                JOptionPane.showMessageDialog(this, "Medico cadastrado");
            }
        });
    }

    private void atualizarTabelaPacientes() {
        String[] colunas = {"ID", "Nome", "Nascimento", "Endereco", "Telefone", "Email", "Convenio"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for (Paciente p : new PacienteRepository().buscarTodos()) {
            //busca todos os atributos do paciente e ja coloca na tela pra só atualizar
            modelo.addRow(new Object[]{p.getId(), p.getNome(), p.getNascimento(), p.getEndereco(), p.getTelefone(), p.getEmail(), p.getConvenio()});
        }
        //dai coloca na tabela
        tabelaPacientes.setModel(modelo);
    }

    private void atualizarTabelaConsultas() {
        String[] colunas = {"Paciente", "Medico", "Data", "Horario", "Tipo"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for (Consulta c : new ConsultaRepository().buscarTodos()) {
            //essa parte foi pra se o paciente/medico nao existir
            //aparece vazio e nao da erro na interface, ja que nao vai retornar null
            String nomePac = c.getP() != null ? c.getP().getNome() : "Vazio";
            String nomeMed = c.getM() != null ? c.getM().getNome() : "Vazio";
            modelo.addRow(new Object[]{nomePac, nomeMed, c.getData(), c.getHorario(), c.getTipo()});
        }
        tabelaConsultas.setModel(modelo);
    }
}   