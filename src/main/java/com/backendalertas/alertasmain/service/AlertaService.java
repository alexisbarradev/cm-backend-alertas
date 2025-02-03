package com.backendalertas.alertasmain.service;

import com.backendalertas.alertasmain.model.AlertaModel;
import com.backendalertas.alertasmain.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    // Obtener todas las alertas
    public List<AlertaModel> getAllAlertas() {
        return alertaRepository.findAll();
    }

    // Obtener una alerta por ID
    public Optional<AlertaModel> getAlertaById(Long id) {
        return alertaRepository.findById(id);
    }

    // Guardar o actualizar una alerta
    public AlertaModel saveAlerta(AlertaModel alerta) {
        if (alerta.getFecha() == null || alerta.getHora() == null) { 
            throw new RuntimeException("Los campos 'fecha' y 'hora' no pueden estar vacíos.");
        }
        return alertaRepository.save(alerta);
    }

    // Eliminar una alerta por ID
    public void deleteAlerta(Long id) {
        if (alertaRepository.existsById(id)) {
            alertaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar la alerta. ID no encontrado: " + id);
        }
    }
}
