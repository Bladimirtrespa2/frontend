// src/main/java/com/example/frontend/view/FrmPerfiles.java
package com.example.frontend.view;

import com.example.frontend.model.PerfilDto;
import com.example.frontend.service.PerfilService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPerfiles extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmPerfiles() {
        setTitle("Gestión de Perfiles");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "PIN", "Estado", "ID Cuenta"},
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
            PerfilService service = new PerfilService();
            List<PerfilDto> lista = service.listar();

            for (PerfilDto p : lista) {
                model.addRow(new Object[]{
                        p.getIdPerfil(),
                        p.getNombrePerfil(),
                        p.getPin(),
                        p.getEstado(),
                        p.getIdCuenta()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar perfiles:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmPerfiles().setVisible(true);
        });
    }
}