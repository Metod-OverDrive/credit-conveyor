package com.practice.conveyor.handler;

import com.practice.conveyor.config.interceptor.RestInterceptor;
import com.practice.conveyor.model.TechWorkMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TechWorkReceiver {

    private final RestInterceptor restInterceptor;

    @RabbitListener(queues = {"techWorkQueue"})
    public void receive(TechWorkMessage message) {
        log.info("Change tech work status: {}", message.toString());
        restInterceptor.changeTechWorkStatus(message.getStatus());
    }
}
