package telas;

import documentos.Consulta;
import entidades.Medico;
import entidades.Paciente;
import repositorios.ConsultaRepository;
import repositorios.MedicoRepository;
import repositorios.PacienteRepository;
import javax.swing.*;
import java.awt.*;

public class AgendarConsultas extends JDialog {

    private JComboBox<Paciente> botaoPaciente;
    private JComboBox<Medico> botaoMedico;
    private JComboBox<String> botaoTipo;
    private JTextField txtData, txtHorario;

    public AgendarConsultas(Frame parent) {
        super(parent, "agendar consulta", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        DefaultComboBoxModel<Paciente> pacienteLista = new DefaultComboBoxModel<>();
        //aqui ele vai buscar todos e pegar só o nome pra colocar na combobox, por isso o addElement(p)
        for (Paciente p : new PacienteRepository().buscarTodos()) {pacienteLista.addElement(p);}
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