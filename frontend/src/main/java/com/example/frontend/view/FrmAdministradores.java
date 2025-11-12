// src/main/java/com/example/frontend/view/FrmAdministradores.java
package com.example.frontend.view;

import com.example.frontend.model.AdministradorDto;
import com.example.frontend.service.AdministradorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmAdministradores extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmAdministradores() {
        setTitle("Gestión de Administradores");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Correo", "Contraseña", "Rol"},
                0
        );
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.addActionListener(e -> cargarDatos());
        add(btnRefresh, BorderLayout.SOUTH);

        cargarDatos(); // Carga inicial
    }

    private void cargarDatos() {
        model.setRowCount(0); // Limpia la tabla
        try {
            AdministradorService service = new AdministradorService();
            List<AdministradorDto> lista = service.listar();

            for (AdministradorDto a : lista) {
                model.addRow(new Object[]{
                        a.getIdAdministrador(),
                        a.getNombre(),
                        a.getCorreo(),
                        a.getContrasena(),
                        a.getRol()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar administradores:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmAdministradores().setVisible(true);
        });
    }
}