// src/main/java/com/example/frontend/view/FrmMenuPrincipal.java
package com.example.frontend.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMenuPrincipal extends JFrame {

    public FrmMenuPrincipal() {
        setTitle("Stream Account Manager - Menú Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Menú de Gestión", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Botones para cada módulo
        addButton(panel, "Gestión de Suscriptores", e -> abrirFormulario(new FrmSuscriptores()));
        addButton(panel, "Gestión de Suscripciones", e -> abrirFormulario(new FrmSuscripciones()));
        addButton(panel, "Gestión de Pagos", e -> abrirFormulario(new FrmPagos()));
        addButton(panel, "Gestión de Administradores", e -> abrirFormulario(new FrmAdministradores()));
        addButton(panel, "Gestión de Cuentas", e -> abrirFormulario(new FrmCuentas()));
        addButton(panel, "Gestión de Perfiles", e -> abrirFormulario(new FrmPerfiles()));
        addButton(panel, "Gestión de Plataformas", e -> abrirFormulario(new FrmPlataformas()));
        addButton(panel, "Salir", e -> System.exit(0));

        add(panel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String texto, ActionListener listener) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.addActionListener(listener);
        panel.add(btn);
    }

    private void abrirFormulario(JFrame form) {
        form.setVisible(true);
        this.setVisible(false); // Oculta el menú (opcional: puedes usar dispose() si prefieres)

        // Cuando se cierre el formulario, vuelve al menú
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                FrmMenuPrincipal.this.setVisible(true);
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                FrmMenuPrincipal.this.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmMenuPrincipal().setVisible(true);
        });
    }
}