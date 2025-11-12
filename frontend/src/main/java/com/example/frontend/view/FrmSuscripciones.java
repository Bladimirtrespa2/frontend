// src/main/java/com/example/frontend/view/FrmSuscripciones.java
package com.example.frontend.view;

import com.example.frontend.model.SuscripcionDto;
import com.example.frontend.service.SuscripcionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmSuscripciones extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmSuscripciones() {
        setTitle("Gestión de Suscripciones");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Fecha Inicio", "Fecha Fin", "Estado", "ID Suscriptor", "ID Plataforma", "Monto"},
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
            SuscripcionService service = new SuscripcionService();
            List<SuscripcionDto> lista = service.listar();

            for (SuscripcionDto s : lista) {
                model.addRow(new Object[]{
                        s.getIdSuscripcion(),
                        s.getFechaInicio(),
                        s.getFechaFin(),
                        s.getEstado(),
                        s.getIdSuscriptor(),
                        s.getIdPlataforma(),
                        s.getMontoMensual()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar suscripciones:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmSuscripciones().setVisible(true);
        });
    }
}