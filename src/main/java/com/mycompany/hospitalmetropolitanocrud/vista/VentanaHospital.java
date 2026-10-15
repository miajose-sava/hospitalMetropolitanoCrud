package com.mycompany.hospitalmetropolitanocrud.vista;

import com.mycompany.hospitalmetropolitanocrud.dao.CitaMedicaDAO;
import com.mycompany.hospitalmetropolitanocrud.modelo.CitaMedica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaHospital extends JFrame {

    private final CitaMedicaDAO citaDAO = new CitaMedicaDAO();

    private JTextField txtId, txtPaciente, txtFecha, txtHora;
    private JComboBox<String> cbEspecialidad, cbMedico, cbEstado;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;

    public VentanaHospital() {
        setTitle("Hospital Metropolitano - Sistema de Agendamiento de Citas");
        setSize(950, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título superior
        JLabel lblTitulo = new JLabel("HOSPITAL METROPOLITANO - GESTIÓN DE CITAS MÉDICAS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel Izquierdo: Formulario de datos
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Agendamiento de Cita"));

        JPanel panelCampos = new JPanel(new GridLayout(7, 2, 6, 6));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtPaciente = new JTextField();
        cbEspecialidad = new JComboBox<>(new String[]{"Cardiología", "Pediatría", "Medicina General", "Traumatología", "Dermatología"});
        cbMedico = new JComboBox<>(new String[]{"Dr. Carlos Mendoza", "Dra. Sofía Andrade", "Dr. Juan Pérez", "Dra. Elena Ramos"});
        txtFecha = new JTextField("2026-10-25");
        txtHora = new JTextField("09:30");
        cbEstado = new JComboBox<>(new String[]{"Pendiente", "Confirmada", "Atendida", "Cancelada"});

        panelCampos.add(new JLabel("ID Cita:"));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Paciente:"));
        panelCampos.add(txtPaciente);
        panelCampos.add(new JLabel("Especialidad:"));
        panelCampos.add(cbEspecialidad);
        panelCampos.add(new JLabel("Médico:"));
        panelCampos.add(cbMedico);
        panelCampos.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelCampos.add(txtFecha);
        panelCampos.add(new JLabel("Hora (HH:MM):"));
        panelCampos.add(txtHora);
        panelCampos.add(new JLabel("Estado:"));
        panelCampos.add(cbEstado);

        // Panel inferior de botones CRUD
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 6, 6));
        JButton btnCrear = new JButton("Agendar Cita");
        JButton btnActualizar = new JButton("Reagendar / Actualizar");
        JButton btnEliminar = new JButton("Cancelar Cita");
        JButton btnLimpiar = new JButton("Limpiar Campos");

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panelIzquierdo.add(panelCampos, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel Central: Tabla de citas registradas
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Paciente", "Especialidad", "Médico", "Fecha", "Hora", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setRowHeight(22);
        JScrollPane scrollTabla = new JScrollPane(tablaCitas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Citas Registradas en Base de Datos"));
        add(scrollTabla, BorderLayout.CENTER);

        // Asignación de acciones a botones
        btnCrear.addActionListener(e -> accionGuardar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Cargar datos del registro seleccionado en la tabla hacia el formulario
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaCitas.getSelectedRow();
            if (fila >= 0) {
                txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtPaciente.setText(modeloTabla.getValueAt(fila, 1).toString());
                cbEspecialidad.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
                cbMedico.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
                txtFecha.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtHora.setText(modeloTabla.getValueAt(fila, 5).toString());
                cbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
            }
        });

        // Consulta inicial de datos (READ)
        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<CitaMedica> lista = citaDAO.listarTodas();
        for (CitaMedica c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getPaciente(), c.getEspecialidad(),
                    c.getMedico(), c.getFecha(), c.getHora(), c.getEstado()
            });
        }
    }

    // CREATE
    private void accionGuardar() {
        if (txtPaciente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre del paciente.");
            return;
        }
        try {
            CitaMedica cita = new CitaMedica(
                    txtPaciente.getText().trim(),
                    cbEspecialidad.getSelectedItem().toString(),
                    cbMedico.getSelectedItem().toString(),
                    txtFecha.getText().trim(),
                    txtHora.getText().trim(),
                    cbEstado.getSelectedItem().toString()
            );
            citaDAO.crear(cita);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Cita agendada correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE
    private void accionActualizar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione primero una cita de la tabla para modificar.");
            return;
        }
        try {
            CitaMedica cita = new CitaMedica();
            cita.setId(Integer.parseInt(txtId.getText().trim()));
            cita.setPaciente(txtPaciente.getText().trim());
            cita.setEspecialidad(cbEspecialidad.getSelectedItem().toString());
            cita.setMedico(cbMedico.getSelectedItem().toString());
            cita.setFecha(txtFecha.getText().trim());
            cita.setHora(txtHora.getText().trim());
            cita.setEstado(cbEstado.getSelectedItem().toString());

            citaDAO.actualizar(cita);
            cargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Cita actualizada correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // DELETE
    private void accionEliminar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione primero una cita de la tabla para cancelar.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea cancelar esta cita médica?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                citaDAO.eliminar(id);
                cargarTabla();
                limpiarFormulario();
                JOptionPane.showMessageDialog(this, "Cita cancelada correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtPaciente.setText("");
        cbEspecialidad.setSelectedIndex(0);
        cbMedico.setSelectedIndex(0);
        txtFecha.setText("2026-10-25");
        txtHora.setText("09:30");
        cbEstado.setSelectedIndex(0);
        tablaCitas.clearSelection();
    }
}