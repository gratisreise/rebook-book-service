package com.example.rebookbookservice.external.rabbitmq;

import com.example.rebookbookservice.common.enums.MessageStatus;
import com.example.rebookbookservice.domain.outbox.OutBoxRepository;
import com.example.rebookbookservice.domain.outbox.Outbox;
import com.example.rebookbookservice.external.rabbitmq.message.NotificationBookMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationPublisher {

  private final OutBoxRepository outboxRepository;
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Value("${notification.exchange}")
  private String exchange;

  @Value("${notification.routing-key}")
  private String routingKey;

  @Transactional
  @Scheduled(fixedDelay = 2000)
  public void processOutbox() {

    List<Outbox> pendingEvents =
        outboxRepository.findTop20ByStatusOrderByCreatedAtAsc(MessageStatus.PENDING);

    for (Outbox event : pendingEvents) {
      try {
        NotificationBookMessage message =
            objectMapper.readValue(event.getPayload(), NotificationBookMessage.class);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        event.setStatus(MessageStatus.PROCESSED);
        event.setProcessedAt(LocalDateTime.now());
        outboxRepository.save(event);

      } catch (Exception e) {
        event.setStatus(MessageStatus.FAILED);
        outboxRepository.save(event);
      }
    }
  }
}
