// src/main/java/com/example/frontend/view/FrmPlataformas.java
package com.example.frontend.view;

import com.example.frontend.model.PlataformaDto;
import com.example.frontend.service.PlataformaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPlataformas extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FrmPlataformas() {
        setTitle("Gestión de Plataformas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Modelo de la tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "URL", "Estado"},
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
            PlataformaService service = new PlataformaService();
            List<PlataformaDto> lista = service.listar();

            for (PlataformaDto p : lista) {
                model.addRow(new Object[]{
                        p.getIdPlataforma(),
                        p.getNombre(),
                        p.getUrlOficial(),
                        p.getEstado()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar plataformas:\n" + e.getMessage(),
                    "Error de Conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new FrmPlataformas().setVisible(true);
        });
    }
}