// src/main/java/com/example/frontend/view/FrmAdministradores.java
package com.example.frontend.view;

import com.example.frontend.model.AdministradorDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmAdministradores extends JFrame {
    private DefaultTableModel model;

    public FrmAdministradores() {
        setTitle("Gestión de Administradores");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Correo", "Contraseña", "Rol"},
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
            AdministradorDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/administradores",
                    AdministradorDto[].class
            );
            model.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Error al cargar administradores:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtNombre = new JTextField(20);
        JTextField txtCorreo = new JTextField(25);
        JTextField txtContrasena = new JTextField(15);
        JTextField txtRol = new JTextField(15);

        Object[] msg = {
                "Nombre:", txtNombre,
                "Correo:", txtCorreo,
                "Contraseña:", txtContrasena,
                "Rol:", txtRol
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Administrador", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                AdministradorDto dto = new AdministradorDto();
                dto.setNombre(txtNombre.getText());
                dto.setCorreo(txtCorreo.getText());
                dto.setContrasena(txtContrasena.getText());
                dto.setRol(txtRol.getText());

                HttpClientUtil.post("http://localhost:8080/api/administradores", dto, AdministradorDto.class);
                JOptionPane.showMessageDialog(this, "Administrador creado exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un administrador", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String nombre = (String) model.getValueAt(row, 1);
        String correo = (String) model.getValueAt(row, 2);
        String contrasena = (String) model.getValueAt(row, 3);
        String rol = (String) model.getValueAt(row, 4);

        JTextField txtNombre = new JTextField(nombre, 20);
        JTextField txtCorreo = new JTextField(correo, 25);
        JTextField txtContrasena = new JTextField(contrasena, 15);
        JTextField txtRol = new JTextField(rol, 15);

        Object[] msg = {
                "Nombre:", txtNombre,
                "Correo:", txtCorreo,
                "Contraseña:", txtContrasena,
                "Rol:", txtRol
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Administrador", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                AdministradorDto dto = new AdministradorDto();
                dto.setIdAdministrador(id);
                dto.setNombre(txtNombre.getText());
                dto.setCorreo(txtCorreo.getText());
                dto.setContrasena(txtContrasena.getText());
                dto.setRol(txtRol.getText());

                HttpClientUtil.put("http://localhost:8080/api/administradores/" + id, dto, AdministradorDto.class);
                JOptionPane.showMessageDialog(this, "Administrador actualizado");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un administrador", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar administrador ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/administradores/" + id);
                JOptionPane.showMessageDialog(this, "Administrador eliminado");
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
        SwingUtilities.invokeLater(() -> new FrmAdministradores().setVisible(true));
    }
}