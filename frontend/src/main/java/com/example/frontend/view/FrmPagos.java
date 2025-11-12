// src/main/java/com/example/frontend/view/FrmPagos.java
package com.example.frontend.view;

import com.example.frontend.model.PagoDto;
import com.example.frontend.service.PagoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPagos extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmPagos() {
        setTitle("Gestión de Pagos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Fecha Pago", "Monto", "Método", "ID Suscripción"},
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
            PagoService service = new PagoService();
            List<PagoDto> lista = service.listar();

            for (PagoDto p : lista) {
                model.addRow(new Object[]{
                        p.getIdPago(),
                        p.getFechaPago(),
                        p.getMontoPagado(),
                        p.getMetodoPago(),
                        p.getIdSuscripcion()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar pagos:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmPagos().setVisible(true);
        });
    }
}