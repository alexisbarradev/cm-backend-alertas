package com.backendalertas.alertasmain.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.backendalertas.alertasmain.config.RabbitMQConfig;
import com.backendalertas.alertasmain.model.AlertaModel;// Importa el modelo de alerta

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper; // Para convertir objetos a JSON

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
        
        // Configurar ObjectMapper para soportar LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Formatear fechas como ISO-8601
    }

    public void sendAlertMessage(AlertaModel alerta) {
        try {
            // Convertimos el objeto Alerta a JSON
            String jsonMessage = objectMapper.writeValueAsString(alerta);
            System.out.println("✅ Enviando JSON a RabbitMQ: " + jsonMessage);
            
            // Enviamos el mensaje en formato JSON
            rabbitTemplate.convertAndSend(RabbitMQConfig.ALERT_EXCHANGE, RabbitMQConfig.ALERT_ROUTING_KEY, jsonMessage);
        } catch (JsonProcessingException e) {
            // Manejo de errores al convertir el objeto a JSON
            System.err.println("❌ Error al convertir Alerta a JSON: " + e.getMessage());
        }
    }
}
