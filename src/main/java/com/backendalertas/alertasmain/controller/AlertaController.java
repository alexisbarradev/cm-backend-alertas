package com.backendalertas.alertasmain.controller;

import com.backendalertas.alertasmain.model.AlertaModel;
import com.backendalertas.alertasmain.service.AlertaService;
import com.backendalertas.alertasmain.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "http://localhost:4200") // Permitir acceso desde Angular
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private RabbitMQSender rabbitMQSender; // Inyección del servicio para RabbitMQ

    // ✅ Obtener todas las alertas
    @GetMapping
    public List<AlertaModel> getAllAlertas() {
        return alertaService.getAllAlertas();
    }

    // ✅ Obtener una alerta por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlertaModel> getAlertaById(@PathVariable Long id) {
        return alertaService.getAlertaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Crear una nueva alerta
    @PostMapping
    public ResponseEntity<AlertaModel> createAlerta(@RequestBody AlertaModel alertaModel) {
        try {
            // Validar campos requeridos de fecha y hora
            if (alertaModel.getFecha() == null || alertaModel.getHora() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Guardar la alerta en la base de datos
            AlertaModel savedAlerta = alertaService.saveAlerta(alertaModel);

            // Enviar alerta a RabbitMQ solo si tiene nivel >= 180
            if (savedAlerta.getNivel() >= 180) {
                rabbitMQSender.sendAlertMessage(savedAlerta); // Enviar el objeto como JSON
                System.out.println("✅ Alerta enviada a RabbitMQ: " + savedAlerta);
            } else {
                System.out.println("❌ Alerta descartada: Nivel menor a 180");
            }

            return ResponseEntity.ok(savedAlerta);
        } catch (Exception e) {
            System.err.println("❌ Error al crear la alerta: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Actualizar una alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<AlertaModel> updateAlerta(@PathVariable Long id, @RequestBody AlertaModel alertaDetails) {
        try {
            return alertaService.getAlertaById(id)
                    .map(existingAlerta -> {
                        existingAlerta.setTipo(alertaDetails.getTipo());
                        existingAlerta.setNivel(alertaDetails.getNivel());
                        existingAlerta.setFecha(alertaDetails.getFecha()); // Actualizar fecha
                        existingAlerta.setHora(alertaDetails.getHora());   // Actualizar hora
                        existingAlerta.setIdPaciente(alertaDetails.getIdPaciente());
                        AlertaModel updatedAlerta = alertaService.saveAlerta(existingAlerta);

                        // Enviar alerta a RabbitMQ solo si el nivel es mayor o igual a 180
                        if (updatedAlerta.getNivel() >= 180) {
                            rabbitMQSender.sendAlertMessage(updatedAlerta); // Enviar el objeto actualizado
                            System.out.println("✅ Alerta de SEÑAL ALTA actualizada: " + updatedAlerta);
                        }

                        return ResponseEntity.ok(updatedAlerta);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar la alerta: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✅ Eliminar una alerta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        try {
            if (alertaService.getAlertaById(id).isPresent()) {
                alertaService.deleteAlerta(id);
                System.out.println("✅ Alerta eliminada: ID " + id);
                return ResponseEntity.noContent().build();
            }
            System.out.println("❌ Alerta no encontrada: ID " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar la alerta: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

