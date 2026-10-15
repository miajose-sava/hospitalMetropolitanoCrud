package com.mycompany.hospitalmetropolitanocrud.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas_medicas")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "paciente", nullable = false, length = 100)
    private String paciente;

    @Column(name = "especialidad", nullable = false, length = 80)
    private String especialidad;

    @Column(name = "medico", nullable = false, length = 100)
    private String medico;

    @Column(name = "fecha", nullable = false, length = 20)
    private String fecha;

    @Column(name = "hora", nullable = false, length = 20)
    private String hora;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    // Constructor vacío
    public CitaMedica() {
    }

    // Constructor con parámetros para crear citas rápidamente
    public CitaMedica(String paciente, String especialidad, String medico, String fecha, String hora, String estado) {
        this.paciente = paciente;
        this.especialidad = especialidad;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Métodos Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}