// src/main/java/com/example/frontend/view/FrmSuscriptores.java
package com.example.frontend.view;

import com.example.frontend.model.SuscriptorDto;
import com.example.frontend.service.SuscriptorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmSuscriptores extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmSuscriptores() {
        setTitle("Gestión de Suscriptores");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Correo"},
                0
        );
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Botón para recargar
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.addActionListener(e -> cargarDatos());
        add(btnRefresh, BorderLayout.SOUTH);

        cargarDatos(); // Carga inicial
    }

    private void cargarDatos() {
        model.setRowCount(0); // Limpia la tabla
        try {
            SuscriptorService service = new SuscriptorService();
            List<SuscriptorDto> lista = service.listar();

            for (SuscriptorDto s : lista) {
                model.addRow(new Object[]{
                        s.getIdSuscriptor(),
                        s.getNombre(),
                        s.getCorreo()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar suscriptores:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmSuscriptores().setVisible(true);
        });
    }
}