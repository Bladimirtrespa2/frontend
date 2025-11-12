// src/main/java/com/example/frontend/view/FrmCuentas.java
package com.example.frontend.view;

import com.example.frontend.model.CuentaDto;
import com.example.frontend.service.CuentaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmCuentas extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmCuentas() {
        setTitle("Gestión de Cuentas");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Correo", "Contraseña", "Inicio", "Fin", "Estado", "Plataforma", "Admin", "Perfiles"},
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
            CuentaService service = new CuentaService();
            List<CuentaDto> lista = service.listar();

            for (CuentaDto c : lista) {
                model.addRow(new Object[]{
                        c.getIdCuenta(),
                        c.getCorreoCuenta(),
                        c.getContrasenaCuenta(),
                        c.getFechaInicio(),
                        c.getFechaFin(),
                        c.getEstado(),
                        c.getNombrePlataforma() != null ? c.getNombrePlataforma() : "ID: " + c.getIdPlataforma(),
                        c.getIdAdministrador(),
                        c.getPerfilesOcupados()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar cuentas:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmCuentas().setVisible(true);
        });
    }
}