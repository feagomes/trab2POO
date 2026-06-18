package telas;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("clinica medica");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(1, 2, 20, 20));
        painel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton btnSecretaria = new JButton("Secretaria");
        JButton btnMedico = new JButton("Medico");

        painel.add(btnSecretaria);
        painel.add(btnMedico);
        add(painel);

        btnSecretaria.addActionListener(e -> new TelaMenuSecretaria().setVisible(true));
        btnMedico.addActionListener(e -> new TelaMenuMedico().setVisible(true));
    }
}