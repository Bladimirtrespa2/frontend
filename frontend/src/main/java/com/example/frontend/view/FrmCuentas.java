// src/main/java/com/example/frontend/view/FrmCuentas.java
package com.example.frontend.view;

import com.example.frontend.model.CuentaDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmCuentas extends JFrame {
    private DefaultTableModel model;

    public FrmCuentas() {
        setTitle("Gestión de Cuentas");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new String[]{"ID", "Correo", "Contraseña", "Inicio", "Fin", "Estado", "Plataforma", "Admin", "Perfiles"},
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
            CuentaDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/cuentas",
                    CuentaDto[].class
            );
            model.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Error al cargar cuentas:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtCorreo = new JTextField(25);
        JTextField txtContrasena = new JTextField(15);
        JTextField txtInicio = new JTextField("2025-01-01", 12);
        JTextField txtFin = new JTextField("2025-12-31", 12);
        JTextField txtEstado = new JTextField("Activo", 15);
        JTextField txtIdPlataforma = new JTextField("1", 8);
        JTextField txtIdAdministrador = new JTextField("1", 8);

        Object[] msg = {
                "Correo:", txtCorreo,
                "Contraseña:", txtContrasena,
                "Fecha Inicio (yyyy-MM-dd):", txtInicio,
                "Fecha Fin (yyyy-MM-dd):", txtFin,
                "Estado:", txtEstado,
                "ID Plataforma:", txtIdPlataforma,
                "ID Administrador:", txtIdAdministrador
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Cuenta", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                CuentaDto dto = new CuentaDto();
                dto.setCorreoCuenta(txtCorreo.getText());
                dto.setContrasenaCuenta(txtContrasena.getText());
                dto.setFechaInicio(txtInicio.getText());
                dto.setFechaFin(txtFin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdPlataforma(Long.parseLong(txtIdPlataforma.getText()));
                dto.setIdAdministrador(Long.parseLong(txtIdAdministrador.getText()));

                HttpClientUtil.post("http://localhost:8080/api/cuentas", dto, CuentaDto.class);
                JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String correo = (String) model.getValueAt(row, 1);
        String contrasena = (String) model.getValueAt(row, 2);
        String inicio = (String) model.getValueAt(row, 3);
        String fin = (String) model.getValueAt(row, 4);
        String estado = (String) model.getValueAt(row, 5);
        Long idPlataforma = (Long) model.getValueAt(row, 7);
        Long idAdministrador = (Long) model.getValueAt(row, 8);

        JTextField txtCorreo = new JTextField(correo, 25);
        JTextField txtContrasena = new JTextField(contrasena, 15);
        JTextField txtInicio = new JTextField(inicio, 12);
        JTextField txtFin = new JTextField(fin, 12);
        JTextField txtEstado = new JTextField(estado, 15);
        JTextField txtIdPlataforma = new JTextField(idPlataforma.toString(), 8);
        JTextField txtIdAdministrador = new JTextField(idAdministrador.toString(), 8);

        Object[] msg = {
                "Correo:", txtCorreo,
                "Contraseña:", txtContrasena,
                "Fecha Inicio (yyyy-MM-dd):", txtInicio,
                "Fecha Fin (yyyy-MM-dd):", txtFin,
                "Estado:", txtEstado,
                "ID Plataforma:", txtIdPlataforma,
                "ID Administrador:", txtIdAdministrador
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Cuenta", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                CuentaDto dto = new CuentaDto();
                dto.setIdCuenta(id);
                dto.setCorreoCuenta(txtCorreo.getText());
                dto.setContrasenaCuenta(txtContrasena.getText());
                dto.setFechaInicio(txtInicio.getText());
                dto.setFechaFin(txtFin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdPlataforma(Long.parseLong(txtIdPlataforma.getText()));
                dto.setIdAdministrador(Long.parseLong(txtIdAdministrador.getText()));

                HttpClientUtil.put("http://localhost:8080/api/cuentas/" + id, dto, CuentaDto.class);
                JOptionPane.showMessageDialog(this, "Cuenta actualizada");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar cuenta ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/cuentas/" + id);
                JOptionPane.showMessageDialog(this, "Cuenta eliminada");
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
        SwingUtilities.invokeLater(() -> new FrmCuentas().setVisible(true));
    }
}