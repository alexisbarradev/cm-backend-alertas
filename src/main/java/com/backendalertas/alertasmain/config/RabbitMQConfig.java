package com.backendalertas.alertasmain.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ALERT_QUEUE = "myQ";
    public static final String ALERT_EXCHANGE = "test";  // Ya existe como DirectExchange
    public static final String ALERT_ROUTING_KEY = "myKey";

    @Bean
    public Queue queue() {
        return new Queue(ALERT_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {  // CAMBIO de TopicExchange a DirectExchange
        return new DirectExchange(ALERT_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {  // También cambiar aquí
        return BindingBuilder.bind(queue).to(exchange).with(ALERT_ROUTING_KEY);
    }
}


