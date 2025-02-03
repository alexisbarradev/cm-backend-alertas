package com.backendalertas.alertasmain.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ALERTAS")
public class AlertaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALERTA_ID")
    private Long id;

    @Column(name = "TIPO", nullable = false)
    private String tipo;

    @Column(name = "NIVEL", nullable = false)
    private int nivel;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha; // Guardamos solo la fecha

    @Column(name = "HORA", nullable = false)
    private LocalTime hora; // Guardamos solo la hora

    @Column(name = "ID_PACIENTE", nullable = false)
    private String idPaciente;

    // Getters y setters para los nuevos campos
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    // Getters y setters para los demás campos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }
}

