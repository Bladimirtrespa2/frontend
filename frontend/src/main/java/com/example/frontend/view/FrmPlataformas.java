// src/main/java/com/example/frontend/view/FrmPlataformas.java
package com.example.frontend.view;

import com.example.frontend.model.PlataformaDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPlataformas extends JFrame {
    private DefaultTableModel model;

    public FrmPlataformas() {
        setTitle("Gestión de Plataformas");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "URL Oficial", "Estado"},
                0
        );
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panel.add(btnCrear);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);
        add(panel, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> crear());
        btnEditar.addActionListener(e -> editar(table));
        btnEliminar.addActionListener(e -> eliminar(table));
        btnActualizar.addActionListener(e -> cargarDatos(table));

        cargarDatos(table);
    }

    private void cargarDatos(JTable table) {
        try {
            PlataformaDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/plataformas",
                    PlataformaDto[].class
            );
            model.setRowCount(0);
            for (PlataformaDto p : lista) {
                model.addRow(new Object[]{
                        p.getIdPlataforma(),
                        p.getNombre(),
                        p.getUrlOficial(),
                        p.getEstado()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar plataformas:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtUrl = new JTextField(30);
        JTextField txtEstado = new JTextField("Activo", 15);

        Object[] msg = {
                "Nombre:", txtNombre,
                "URL Oficial:", txtUrl,
                "Estado:", txtEstado
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Plataforma", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PlataformaDto dto = new PlataformaDto();
                dto.setNombre(txtNombre.getText());
                dto.setUrlOficial(txtUrl.getText());
                dto.setEstado(txtEstado.getText());

                HttpClientUtil.post("http://localhost:8080/api/plataformas", dto, PlataformaDto.class);
                JOptionPane.showMessageDialog(this, "Plataforma creada exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una plataforma", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String nombre = (String) model.getValueAt(row, 1);
        String url = (String) model.getValueAt(row, 2);
        String estado = (String) model.getValueAt(row, 3);

        JTextField txtNombre = new JTextField(nombre, 20);
        JTextField txtUrl = new JTextField(url, 30);
        JTextField txtEstado = new JTextField(estado, 15);

        Object[] msg = {
                "Nombre:", txtNombre,
                "URL Oficial:", txtUrl,
                "Estado:", txtEstado
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Plataforma", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PlataformaDto dto = new PlataformaDto();
                dto.setIdPlataforma(id);
                dto.setNombre(txtNombre.getText());
                dto.setUrlOficial(txtUrl.getText());
                dto.setEstado(txtEstado.getText());

                HttpClientUtil.put("http://localhost:8080/api/plataformas/" + id, dto, PlataformaDto.class);
                JOptionPane.showMessageDialog(this, "Plataforma actualizada");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una plataforma", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar plataforma ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/plataformas/" + id);
                JOptionPane.showMessageDialog(this, "Plataforma eliminada");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JTable getTable() {
        return (JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPlataformas().setVisible(true));
    }
}