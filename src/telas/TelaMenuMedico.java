package telas;

import documentos.Consulta;
import documentos.Prontuario;
import entidades.Paciente;
import repositorios.ConsultaRepository;
import repositorios.PacienteRepository;
import repositorios.ProntuarioRepository;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaMenuMedico extends JFrame {

    private Paciente pacienteAtual;
    private JLabel lblStatusPaciente;
    private JButton botaoDados, botaoProntuario, botaoRelatorio;

    public TelaMenuMedico() {
        setTitle("painel do medico");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //a tela principal do menu com busca pelo telefone em cima
        JPanel painelBusca = new JPanel(new FlowLayout());
        JTextField txtBusca = new JTextField(12);
        JButton botaoBuscar = new JButton("Buscar P/Telefone");
        lblStatusPaciente = new JLabel("Paciente nao selecionado");
        
        painelBusca.add(txtBusca);
        painelBusca.add(botaoBuscar);
        painel.add(painelBusca, BorderLayout.NORTH);

        //adiciona os botoes no meio da tela
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        botaoDados = new JButton("Dados adicionais");
        botaoProntuario = new JButton("Prontuario");
        botaoRelatorio = new JButton("Relatorios Medicos");

        //deixam eles desligados pra nao dar erro
        botaoDados.setEnabled(false);
        botaoProntuario.setEnabled(false);
        botaoRelatorio.setEnabled(false);

        painelBotoes.add(botaoDados);
        painelBotoes.add(botaoProntuario);
        painelBotoes.add(botaoRelatorio);
        
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.add(lblStatusPaciente, BorderLayout.NORTH);
        painelCentro.add(painelBotoes, BorderLayout.CENTER);
        painel.add(painelCentro, BorderLayout.CENTER);

        add(painel);

        //Buscar
        botaoBuscar.addActionListener(e -> {
            pacienteAtual = new PacienteRepository().buscarPorTelefone(txtBusca.getText().trim());
            if (pacienteAtual != null) {
                lblStatusPaciente.setText("Paciente: " + pacienteAtual.getNome());
                botaoDados.setEnabled(true);
                botaoProntuario.setEnabled(true);
                botaoRelatorio.setEnabled(true);
            } else {
                lblStatusPaciente.setText("Paciente nao encontrado");
                botaoDados.setEnabled(false);
                botaoProntuario.setEnabled(false);
                botaoRelatorio.setEnabled(false);
            }
        });

        //o actionlistener pros botoes abrirem os submenus respectivos deles
        botaoDados.addActionListener(e -> new DadosAdicionais(this).setVisible(true));
        botaoProntuario.addActionListener(e -> new DialogProntuario(this).setVisible(true));
        botaoRelatorio.addActionListener(e -> new DialogRelatorios(this).setVisible(true));
    }

    //subtela dos dados adicionais
    class DadosAdicionais extends JDialog {
        private JCheckBox dadoFuma, dadoBebe, dadoColesterol, dadoDiabete, dadoCardiaca;
        private JTextField txtCirurgias, txtAlergias;

        public DadosAdicionais(JFrame parent) {
            super(parent, "Dados adicionais", true);
            setSize(400, 350);
            setLocationRelativeTo(parent);

            JPanel form = new JPanel(new GridLayout(7, 2, 5, 5));
            form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            dadoFuma = new JCheckBox("Fuma");
            dadoBebe = new JCheckBox("Bebe");
            dadoColesterol = new JCheckBox("Colesterol alto");
            dadoDiabete = new JCheckBox("Diabete");
            dadoCardiaca = new JCheckBox("Doenca cardiaca");

            form.add(dadoFuma);
            form.add(dadoBebe);
            form.add(dadoColesterol);
            form.add(dadoDiabete);
            form.add(dadoCardiaca);
            form.add(new JLabel("")); //deixei esse espaco pq tava muito feio ver eles grudados

            form.add(new JLabel("Cirurgias:"));
            txtCirurgias = new JTextField();
            form.add(txtCirurgias);

            form.add(new JLabel("Alergias:"));
            txtAlergias = new JTextField();
            form.add(txtAlergias);

            //carrega os dados que ja existem do paciente
            //e na parte da cirurgia e alergia, mesmo tratamento de erro pra nao bugar tudo retornando null
            if (pacienteAtual != null) {
                dadoFuma.setSelected(pacienteAtual.isFuma());
                dadoBebe.setSelected(pacienteAtual.isBebe());
                dadoColesterol.setSelected(pacienteAtual.isColesterol());
                dadoDiabete.setSelected(pacienteAtual.isDiabete());
                dadoCardiaca.setSelected(pacienteAtual.isDoencaCard());
                txtCirurgias.setText(pacienteAtual.getCirurgias() != null ? pacienteAtual.getCirurgias() : "");
                txtAlergias.setText(pacienteAtual.getAlergias() != null ? pacienteAtual.getAlergias() : "");
            }

            JPanel botoes = new JPanel(new FlowLayout());
            JButton botaoSalvar = new JButton("Salvar");
            JButton botaoRemover = new JButton("Remover ");
            botoes.add(botaoSalvar);
            botoes.add(botaoRemover);
            add(form, BorderLayout.CENTER);
            add(botoes, BorderLayout.SOUTH);

            botaoSalvar.addActionListener(e -> {
                pacienteAtual.setFuma(dadoFuma.isSelected());
                pacienteAtual.setBebe(dadoBebe.isSelected());
                pacienteAtual.setColesterol(dadoColesterol.isSelected());
                pacienteAtual.setDiabete(dadoDiabete.isSelected());
                pacienteAtual.setDoencaCard(dadoCardiaca.isSelected());
                pacienteAtual.setCirurgias(txtCirurgias.getText());
                pacienteAtual.setAlergias(txtAlergias.getText());
                new PacienteRepository().salvarPaciente(pacienteAtual); //usa a funcao do repositorio pra salvar no banco
                dispose();
            });

            botaoRemover.addActionListener(e -> {
                dadoFuma.setSelected(false);
                dadoBebe.setSelected(false);
                dadoColesterol.setSelected(false);
                dadoDiabete.setSelected(false);
                dadoCardiaca.setSelected(false);
                txtCirurgias.setText("");
                txtAlergias.setText("");
                botaoSalvar.doClick(); //usa o botao salvar pra salvar vazio no banco
            });
        }
    }

    //subtela do prontuario
    class DialogProntuario extends JDialog {
        private JTextArea txtSintomas, txtDiagnostico, txtPrescricao;

        public DialogProntuario(JFrame parent) {
            super(parent, "Prontuario", true);
            setSize(400, 400);
            setLocationRelativeTo(parent);

            JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
            form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            //adiciona as caixas de texto 
            form.add(new JLabel("Sintomas:"));
            txtSintomas = new JTextArea();
            form.add(new JScrollPane(txtSintomas));

            form.add(new JLabel("Diagnostico:"));
            txtDiagnostico = new JTextArea();
            form.add(new JScrollPane(txtDiagnostico));

            form.add(new JLabel("Prescricao:"));
            txtPrescricao = new JTextArea();
            form.add(new JScrollPane(txtPrescricao));

            //se existir no paciente, ele carrega o prontuario pra editar
            Prontuario prontuarioSalvo = pacienteAtual.getProntuario();
            if (prontuarioSalvo != null) {
                if (prontuarioSalvo.getSintomas() != null && !prontuarioSalvo.getSintomas().isEmpty()) {
                txtSintomas.setText(String.join(", ", prontuarioSalvo.getSintomas()));
                } 
                else {
                    txtSintomas.setText("");
                }
                txtDiagnostico.setText(prontuarioSalvo.getDiagnostico());
                txtPrescricao.setText(prontuarioSalvo.getPrescricao());
            }
            //botao pra remover e salvar
            JPanel botoes = new JPanel(new FlowLayout());
            JButton botaoSalvar = new JButton("Salvar");
            JButton botaoRemover = new JButton("Remover prontuario");
            botoes.add(botaoSalvar);
            botoes.add(botaoRemover);

            add(form, BorderLayout.CENTER);
            add(botoes, BorderLayout.SOUTH);

            botaoSalvar.addActionListener(e -> {
                Prontuario p = pacienteAtual.getProntuario();
                if (p == null) {
                    p = new Prontuario();
                }
                p.setDiagnostico(txtDiagnostico.getText());
                p.setPrescricao(txtPrescricao.getText());
                if (p.getSintomas() != null) {
                     p.getSintomas().clear();
                }
                p.addSintoma(txtSintomas.getText());

                new ProntuarioRepository().salvar(p);
                pacienteAtual.setProntuario(p);
                new PacienteRepository().salvarPaciente(pacienteAtual);
                dispose();
            });

            botaoRemover.addActionListener(e -> {
                txtSintomas.setText("");
                txtDiagnostico.setText("");
                txtPrescricao.setText("");
                botaoSalvar.doClick(); //salva vazio no banco
            });
        }
    }

    //subtela de relatorios
    class DialogRelatorios extends JDialog {
        public DialogRelatorios(JFrame parent) {
            super(parent, "relatorios", true);
            setSize(300, 250);
            setLocationRelativeTo(parent);

            JPanel botoes = new JPanel(new GridLayout(4, 1, 10, 10));
            botoes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JButton botoesReceita = new JButton("Receita");
            JButton botoesAtestado = new JButton("Atestado");
            JButton botoesAcompanhamento = new JButton("Declaracao de acompanhamento");
            JButton botoesClienteMes = new JButton("Clientes atendidos no mes");

            botoes.add(botoesReceita);
            botoes.add(botoesAtestado);
            botoes.add(botoesAcompanhamento);
            botoes.add(botoesClienteMes);
            add(botoes);
            
            //aqui ele pega a prescricao do prontuario pra mandar no console a receita
            botoesReceita.addActionListener(e -> {
                if (pacienteAtual.getProntuario() != null) {
                    System.out.println("Receita gerada: " + pacienteAtual.getProntuario().getPrescricao());
                } 
                else {
                    System.out.println("Paciente sem receita");
                }
            });

            botoesAtestado.addActionListener(e -> {
                String dias = JOptionPane.showInputDialog("Numero de dias de atestado:");
                if (dias != null) {
                    System.out.println("Atestado de " + dias + " dias para o paciente " + pacienteAtual.getNome());
                }
            });

            botoesAcompanhamento.addActionListener(e -> {
                System.out.println(pacienteAtual.getNome() + " veio acompanhado");
            });

            botoesClienteMes.addActionListener(e -> {
                //pega a data de hoje
                String mesAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));
                System.out.println("Pacientes no mes (" + mesAtual + ")");
                //loop pra pegar todos os que tem consulta esse mes e printar no console separado por -
                for (Consulta c : new ConsultaRepository().buscarTodos()) {
                    if (c.getData().endsWith(mesAtual) && c.getP() != null) {
                        System.out.println("- " + c.getP().getNome());
                    }
                }
            });
        }
    }
}