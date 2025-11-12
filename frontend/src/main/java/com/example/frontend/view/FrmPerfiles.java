// src/main/java/com/example/frontend/view/FrmPerfiles.java
package com.example.frontend.view;

import com.example.frontend.model.PerfilDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPerfiles extends JFrame {
    private DefaultTableModel model;

    public FrmPerfiles() {
        setTitle("Gestión de Perfiles");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "PIN", "Estado", "ID Cuenta"},
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
            PerfilDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/perfiles",
                    PerfilDto[].class
            );
            model.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Error al cargar perfiles:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtPin = new JTextField(8);
        JTextField txtEstado = new JTextField("Disponible", 15);
        JTextField txtIdCuenta = new JTextField("1", 8);

        Object[] msg = {
                "Nombre del Perfil:", txtNombre,
                "PIN (4 dígitos):", txtPin,
                "Estado:", txtEstado,
                "ID Cuenta:", txtIdCuenta
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Perfil", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PerfilDto dto = new PerfilDto();
                dto.setNombrePerfil(txtNombre.getText());
                dto.setPin(txtPin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdCuenta(Long.parseLong(txtIdCuenta.getText()));

                HttpClientUtil.post("http://localhost:8080/api/perfiles", dto, PerfilDto.class);
                JOptionPane.showMessageDialog(this, "Perfil creado exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un perfil", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String nombre = (String) model.getValueAt(row, 1);
        String pin = (String) model.getValueAt(row, 2);
        String estado = (String) model.getValueAt(row, 3);
        Long idCuenta = (Long) model.getValueAt(row, 4);

        JTextField txtNombre = new JTextField(nombre, 20);
        JTextField txtPin = new JTextField(pin, 8);
        JTextField txtEstado = new JTextField(estado, 15);
        JTextField txtIdCuenta = new JTextField(idCuenta.toString(), 8);

        Object[] msg = {
                "Nombre del Perfil:", txtNombre,
                "PIN (4 dígitos):", txtPin,
                "Estado:", txtEstado,
                "ID Cuenta:", txtIdCuenta
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Perfil", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PerfilDto dto = new PerfilDto();
                dto.setIdPerfil(id);
                dto.setNombrePerfil(txtNombre.getText());
                dto.setPin(txtPin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdCuenta(Long.parseLong(txtIdCuenta.getText()));

                HttpClientUtil.put("http://localhost:8080/api/perfiles/" + id, dto, PerfilDto.class);
                JOptionPane.showMessageDialog(this, "Perfil actualizado");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un perfil", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar perfil ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/perfiles/" + id);
                JOptionPane.showMessageDialog(this, "Perfil eliminado");
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
        SwingUtilities.invokeLater(() -> new FrmPerfiles().setVisible(true));
    }
}