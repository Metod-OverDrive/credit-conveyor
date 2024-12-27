package com.practice.techwork.service;

import com.practice.techwork.model.TechWorkMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Setter
public class TechWorkService {

    @Value("${rabbit-queue.name}")
    private String queueTechWorkName;

    private final RabbitTemplate rabbitTemplate;

    public void sendTechWorkStatus(TechWorkMessage message) {
        rabbitTemplate.convertAndSend(queueTechWorkName, message);
    }

}
