package telas;

import documentos.Consulta;
import entidades.Medico;
import entidades.Paciente;
// Classes responsáveis pelo acesso ao banco de dados
import repositorios.ConsultaRepository;
import repositorios.MedicoRepository;
import repositorios.PacienteRepository;
import javax.swing.*;
import java.awt.*;

public class AgendarConsultas extends JDialog {
    /*
     * Janela responsável pelo agendamento de consultas.
     * Ela herda de JDialog, ou seja, é uma janela secundária
     * que fica vinculada à janela principal.
     */

    private JComboBox<Paciente> botaoPaciente;
    private JComboBox<Medico> botaoMedico;
    private JComboBox<String> botaoTipo;
    private JTextField txtData, txtHorario;
    /*
     * Construtor da janela.
     * Recebe a janela principal como parâmetro.
     */
    public AgendarConsultas(Frame parent) {
        super(parent, "agendar consulta", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        /*
         * Cria um modelo para armazenar pacientes.
         * Esse modelo será usado pelo JComboBox.
         */
        DefaultComboBoxModel<Paciente> pacienteLista = new DefaultComboBoxModel<>();
        //aqui ele vai buscar todos e selecionar apenas o nome para inserir na combobox, por isso o addElement(p)
        for (Paciente p : new PacienteRepository().buscarTodos()) {pacienteLista.addElement(p);}
         // Cria o ComboBox usando a lista carregada
        botaoPaciente = new JComboBox<>(pacienteLista);

        DefaultComboBoxModel<Medico> medicoLista = new DefaultComboBoxModel<>();
        //mesma coisa com os medicos
        for (Medico m : new MedicoRepository().buscarTodos()) {medicoLista.addElement(m);}
        botaoMedico = new JComboBox<>(medicoLista);

        String[] tipos = {"Consulta normal", "Retorno"};
        botaoTipo = new JComboBox<>(tipos);

        painelForm.add(new JLabel("Paciente:"));
        painelForm.add(botaoPaciente);

        painelForm.add(new JLabel("Medico:"));
        painelForm.add(botaoMedico);

        painelForm.add(new JLabel("Data (dia/mes/ano):"));
        txtData = new JTextField(); 
        painelForm.add(txtData);

        painelForm.add(new JLabel("Horario (hora:min):"));
        txtHorario = new JTextField(); 
        painelForm.add(txtHorario);

        painelForm.add(new JLabel("Tipo:"));
        painelForm.add(botaoTipo);
        // botão para confirmar agendamento
        JButton botaoAgendar = new JButton("Confirmar ");
        
        add(painelForm, BorderLayout.CENTER);
        add(botaoAgendar, BorderLayout.SOUTH);

        botaoAgendar.addActionListener(e -> {
            Consulta cons = new Consulta();
            cons.setP((Paciente) botaoPaciente.getSelectedItem());
            cons.setM((Medico) botaoMedico.getSelectedItem());
            cons.setData(txtData.getText());
            cons.setHorario(txtHorario.getText());
            cons.setTipo(botaoTipo.getSelectedItem().toString());

            new ConsultaRepository().salvarConsulta(cons);
            JOptionPane.showMessageDialog(this, "Consulta agendada");
            dispose();
        });
    }
}
