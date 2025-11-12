// src/main/java/com/example/frontend/view/FrmSuscripciones.java
package com.example.frontend.view;

import com.example.frontend.model.SuscripcionDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmSuscripciones extends JFrame {
    private DefaultTableModel model;

    public FrmSuscripciones() {
        setTitle("Gestión de Suscripciones");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Inicio", "Fin", "Estado", "ID Suscriptor", "ID Plataforma", "Monto"},
                0
        );
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Botones
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

        // Eventos
        btnCrear.addActionListener(e -> crear());
        btnEditar.addActionListener(e -> editar(table));
        btnEliminar.addActionListener(e -> eliminar(table));
        btnActualizar.addActionListener(e -> cargarDatos(table));

        cargarDatos(table);
    }

    private void cargarDatos(JTable table) {
        try {
            SuscripcionDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/v1/suscripciones",
                    SuscripcionDto[].class
            );
            model.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Error al cargar suscripciones:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtInicio = new JTextField(12);
        JTextField txtFin = new JTextField(12);
        JTextField txtEstado = new JTextField(15);
        JTextField txtIdSuscriptor = new JTextField(8);
        JTextField txtIdPlataforma = new JTextField(8);
        JTextField txtMonto = new JTextField(10);

        Object[] msg = {
                "Fecha Inicio (yyyy-MM-dd):", txtInicio,
                "Fecha Fin (yyyy-MM-dd):", txtFin,
                "Estado:", txtEstado,
                "ID Suscriptor:", txtIdSuscriptor,
                "ID Plataforma:", txtIdPlataforma,
                "Monto Mensual:", txtMonto
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Suscripción", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                SuscripcionDto dto = new SuscripcionDto();
                dto.setFechaInicio(txtInicio.getText());
                dto.setFechaFin(txtFin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdSuscriptor(Long.parseLong(txtIdSuscriptor.getText()));
                dto.setIdPlataforma(Long.parseLong(txtIdPlataforma.getText()));
                dto.setMontoMensual(Double.parseDouble(txtMonto.getText()));

                HttpClientUtil.post("http://localhost:8080/api/v1/suscripciones", dto, SuscripcionDto.class);
                JOptionPane.showMessageDialog(this, "Suscripción creada exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una suscripción", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String inicio = (String) model.getValueAt(row, 1);
        String fin = (String) model.getValueAt(row, 2);
        String estado = (String) model.getValueAt(row, 3);
        Long idSuscriptor = (Long) model.getValueAt(row, 4);
        Long idPlataforma = (Long) model.getValueAt(row, 5);
        Double monto = (Double) model.getValueAt(row, 6);

        JTextField txtInicio = new JTextField(inicio, 12);
        JTextField txtFin = new JTextField(fin, 12);
        JTextField txtEstado = new JTextField(estado, 15);
        JTextField txtIdSuscriptor = new JTextField(idSuscriptor.toString(), 8);
        JTextField txtIdPlataforma = new JTextField(idPlataforma.toString(), 8);
        JTextField txtMonto = new JTextField(monto.toString(), 10);

        Object[] msg = {
                "Fecha Inicio (yyyy-MM-dd):", txtInicio,
                "Fecha Fin (yyyy-MM-dd):", txtFin,
                "Estado:", txtEstado,
                "ID Suscriptor:", txtIdSuscriptor,
                "ID Plataforma:", txtIdPlataforma,
                "Monto Mensual:", txtMonto
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Suscripción", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                SuscripcionDto dto = new SuscripcionDto();
                dto.setIdSuscripcion(id);
                dto.setFechaInicio(txtInicio.getText());
                dto.setFechaFin(txtFin.getText());
                dto.setEstado(txtEstado.getText());
                dto.setIdSuscriptor(Long.parseLong(txtIdSuscriptor.getText()));
                dto.setIdPlataforma(Long.parseLong(txtIdPlataforma.getText()));
                dto.setMontoMensual(Double.parseDouble(txtMonto.getText()));

                HttpClientUtil.put("http://localhost:8080/api/v1/suscripciones/" + id, dto, SuscripcionDto.class);
                JOptionPane.showMessageDialog(this, "Suscripción actualizada");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una suscripción", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar suscripción ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/v1/suscripciones/" + id);
                JOptionPane.showMessageDialog(this, "Suscripción eliminada");
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
        SwingUtilities.invokeLater(() -> new FrmSuscripciones().setVisible(true));
    }
}