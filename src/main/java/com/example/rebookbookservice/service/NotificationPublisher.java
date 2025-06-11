package com.example.rebookbookservice.service;


import com.example.rebookbookservice.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPublisher {
    private final AmqpTemplate amqpTemplate;

    @Value("${notification.exchange}")
    private String exchange;

    @Value("${notification.routing-key}")
    private String routingKey;



    public void sendNotification(NotificationMessage message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}