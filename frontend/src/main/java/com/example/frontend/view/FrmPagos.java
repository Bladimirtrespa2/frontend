// src/main/java/com/example/frontend/view/FrmPagos.java
package com.example.frontend.view;

import com.example.frontend.model.PagoDto;
import com.example.frontend.service.HttpClientUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPagos extends JFrame {
    private DefaultTableModel model;

    public FrmPagos() {
        setTitle("Gestión de Pagos");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabla
        model = new DefaultTableModel(
                new String[]{"ID", "Fecha Pago", "Monto", "Método", "ID Suscripción"},
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
            PagoDto[] lista = HttpClientUtil.getArray(
                    "http://localhost:8080/api/v1/pagos",
                    PagoDto[].class
            );
            model.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Error al cargar pagos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crear() {
        JTextField txtFecha = new JTextField("2025-10-01", 12);
        JTextField txtMonto = new JTextField("14.99", 10);
        JTextField txtMetodo = new JTextField("Tarjeta", 15);
        JTextField txtIdSuscripcion = new JTextField("1", 8);

        Object[] msg = {
                "Fecha Pago (yyyy-MM-dd):", txtFecha,
                "Monto Pagado:", txtMonto,
                "Método de Pago:", txtMetodo,
                "ID Suscripción:", txtIdSuscripcion
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Crear Pago", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PagoDto dto = new PagoDto();
                dto.setFechaPago(txtFecha.getText());
                dto.setMontoPagado(Double.parseDouble(txtMonto.getText()));
                dto.setMetodoPago(txtMetodo.getText());
                dto.setIdSuscripcion(Long.parseLong(txtIdSuscripcion.getText()));

                HttpClientUtil.post("http://localhost:8080/api/v1/pagos", dto, PagoDto.class);
                JOptionPane.showMessageDialog(this, "Pago creado exitosamente");
                cargarDatos(getTable());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al crear:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pago", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        String fecha = (String) model.getValueAt(row, 1);
        Double monto = (Double) model.getValueAt(row, 2);
        String metodo = (String) model.getValueAt(row, 3);
        Long idSuscripcion = (Long) model.getValueAt(row, 4);

        JTextField txtFecha = new JTextField(fecha, 12);
        JTextField txtMonto = new JTextField(monto.toString(), 10);
        JTextField txtMetodo = new JTextField(metodo, 15);
        JTextField txtIdSuscripcion = new JTextField(idSuscripcion.toString(), 8);

        Object[] msg = {
                "Fecha Pago (yyyy-MM-dd):", txtFecha,
                "Monto Pagado:", txtMonto,
                "Método de Pago:", txtMetodo,
                "ID Suscripción:", txtIdSuscripcion
        };

        int opt = JOptionPane.showConfirmDialog(this, msg, "Editar Pago", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            try {
                PagoDto dto = new PagoDto();
                dto.setIdPago(id);
                dto.setFechaPago(txtFecha.getText());
                dto.setMontoPagado(Double.parseDouble(txtMonto.getText()));
                dto.setMetodoPago(txtMetodo.getText());
                dto.setIdSuscripcion(Long.parseLong(txtIdSuscripcion.getText()));

                HttpClientUtil.put("http://localhost:8080/api/v1/pagos/" + id, dto, PagoDto.class);
                JOptionPane.showMessageDialog(this, "Pago actualizado");
                cargarDatos(table);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminar(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pago", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar pago ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                HttpClientUtil.delete("http://localhost:8080/api/v1/pagos/" + id);
                JOptionPane.showMessageDialog(this, "Pago eliminado");
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
        SwingUtilities.invokeLater(() -> new FrmPagos().setVisible(true));
    }
}