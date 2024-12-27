package com.practice.techwork.controller;

import com.practice.techwork.model.TechWorkMessage;
import com.practice.techwork.service.TechWorkService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TechWorkController {

    private final TechWorkService techWorkService;

    @GetMapping
    public ResponseEntity<?> setTechWorkStatus(@PathParam("status") Boolean status) {
        if (status != null)
        {
            TechWorkMessage message = new TechWorkMessage();
            message.setId(UUID.randomUUID());
            message.setStatus(status);
            message.setTime(LocalDateTime.now());
            techWorkService.sendTechWorkStatus(message);
            return ResponseEntity.ok(message);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("status must be Boolean.");
        }
    }

}
