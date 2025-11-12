// src/main/java/com/example/frontend/view/FrmSuscriptores.java
package com.example.frontend.view;

import com.example.frontend.model.SuscriptorDto;
import com.example.frontend.service.HttpClientUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmSuscriptores extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public FrmSuscriptores() {
        setTitle("Gestión de Suscriptores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnCrear.addActionListener(e -> crearSuscriptor());
        btnEditar.addActionListener(e -> editarSuscriptor());
        btnEliminar.addActionListener(e -> eliminarSuscriptor());

        panel.add(btnCrear);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        add(panel, BorderLayout.SOUTH);

        cargarDatos();

        // Doble clic para editar
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarSuscriptor();
                }
            }
        });
    }

    private void cargarDatos() {
        try {
            SuscriptorDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/v1/suscriptores",
                    SuscriptorDto[].class
            );
            model.setRowCount(0);
            for (SuscriptorDto s : lista) {
                model.addRow(new Object[]{s.getIdSuscriptor(), s.getNombre(), s.getCorreo()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearSuscriptor() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtCorreo = new JTextField(20);
        Object[] msg = {"Nombre:", txtNombre, "Correo:", txtCorreo};
        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Suscriptor", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                SuscriptorDto dto = new SuscriptorDto(null, txtNombre.getText(), txtCorreo.getText());
                HttpClientUtil.post("http://localhost:8080/api/v1/suscriptores", dto, SuscriptorDto.class);
                JOptionPane.showMessageDialog(this, "Suscriptor creado exitosamente");
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarSuscriptor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un suscriptor", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id = (Long) model.getValueAt(row, 0);
        String nombreActual = (String) model.getValueAt(row, 1);
        String correoActual = (String) model.getValueAt(row, 2);

        JTextField txtNombre = new JTextField(nombreActual, 20);
        JTextField txtCorreo = new JTextField(correoActual, 20);
        Object[] msg = {"Nombre:", txtNombre, "Correo:", txtCorreo};
        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Suscriptor", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                SuscriptorDto dto = new SuscriptorDto(id, txtNombre.getText(), txtCorreo.getText());
                HttpClientUtil.put("http://localhost:8080/api/v1/suscriptores/" + id, dto, SuscriptorDto.class);
                JOptionPane.showMessageDialog(this, "Suscriptor actualizado");
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarSuscriptor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un suscriptor", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar suscriptor ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/v1/suscriptores/" + id);
                JOptionPane.showMessageDialog(this, "Suscriptor eliminado");
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}