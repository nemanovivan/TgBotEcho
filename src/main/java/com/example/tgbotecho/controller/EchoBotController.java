package com.example.tgbotecho.controller;

import com.example.tgbotecho.domain.Delay;
import com.example.tgbotecho.domain.MessageInner;
import com.example.tgbotecho.domain.Response;
import com.example.tgbotecho.service.EchoBotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoBotController {

    private final EchoBotService echoBotService;

    public EchoBotController(EchoBotService echoBotService) {
        this.echoBotService = echoBotService;
    }

    @PostMapping("/receiveEchoMessage")
    public Response receiveEchoMessage(@RequestBody MessageInner messageInner) {
        return echoBotService.receiveEchoMessage(messageInner);
    }

    @PutMapping("/updateQueueDelay")
    public void updateQueueDelay(@RequestBody Delay delay){
        echoBotService.updateQueueDelay(delay);
    }
}
