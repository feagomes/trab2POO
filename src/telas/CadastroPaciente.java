package telas;

import entidades.Paciente;
import repositorios.PacienteRepository;
import javax.swing.*;
import java.awt.*;

public class CadastroPaciente extends JDialog {

    private JTextField txtNome, txtNascimento, txtEndereco, txtTelefone, txtEmail;
    private JComboBox<String> botaoConvenio;
    private Paciente pacienteAtual;

    public CadastroPaciente(Frame parent, Paciente p) {
        super(parent, "cadastrar paciente", true);
        // guarda o paciente cadastrado
        this.pacienteAtual = p;
        
        setSize(300, 500); // tela fina e alta
        setLocationRelativeTo(parent);
        setResizable(false);
        /*
         * Painel do formulário;
         * GridLayout(6,2), 6 linhas e 2 colunas
         */
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 10, 20));
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //coloca as caixas de texto pra preencher/atualizar
        painelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField(); 
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Nascimento:"));
        txtNascimento = new JTextField(); 
        painelForm.add(txtNascimento);

        painelForm.add(new JLabel("Endereco:"));
        txtEndereco = new JTextField(); 
        painelForm.add(txtEndereco);

        painelForm.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(); 
        painelForm.add(txtTelefone);

        painelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField(); 
        painelForm.add(txtEmail);

        painelForm.add(new JLabel("Convenio:"));
        String[] tiposConvenio = {"Particular", "Plano de Saude"};
        botaoConvenio = new JComboBox<>(tiposConvenio);
        painelForm.add(botaoConvenio);

        JButton botaoSalvar = new JButton("Salvar");
        add(painelForm, BorderLayout.CENTER);
        add(botaoSalvar, BorderLayout.SOUTH);

        //carrega os dados se ja tiver salvo
        /*
         * Se recebemos um paciente,
         * significa que estamos editando.
         *
         * Então carregamos os dados dele
         * nos campos da tela.
         */
        if (pacienteAtual != null) {
            txtNome.setText(pacienteAtual.getNome());
            txtNascimento.setText(pacienteAtual.getNascimento());
            txtEndereco.setText(pacienteAtual.getEndereco());
            txtTelefone.setText(pacienteAtual.getTelefone());
            txtEmail.setText(pacienteAtual.getEmail());
            botaoConvenio.setSelectedItem(pacienteAtual.getConvenio());
        }
         /*
         * Evento handler do botão Salvar.
         * Executado quando o usuário clicar.
         */
        botaoSalvar.addActionListener(e -> {
            // Se pacienteAtual for null, estamos criando um novo paciente.
            if (pacienteAtual == null) {
            //copia os dados da tela, para o objeto paciente
                pacienteAtual = new Paciente();
            }
            pacienteAtual.setNome(txtNome.getText());
            pacienteAtual.setNascimento(txtNascimento.getText());
            pacienteAtual.setEndereco(txtEndereco.getText());
            pacienteAtual.setTelefone(txtTelefone.getText());
            pacienteAtual.setEmail(txtEmail.getText());
            pacienteAtual.setConvenio(botaoConvenio.getSelectedItem().toString());
            // salva e atualiza os dados no Banco
            new PacienteRepository().salvarPaciente(pacienteAtual);
            JOptionPane.showMessageDialog(this, "Paciente cadastrado.");
            // fecha a janela
            dispose();
        });
    }
}
