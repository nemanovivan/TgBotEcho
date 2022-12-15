package com.example.tgbotecho.service;

import com.example.tgbotecho.dao.EchoBotDao;
import com.example.tgbotecho.domain.Delay;
import com.example.tgbotecho.domain.MessageInner;
import com.example.tgbotecho.domain.Response;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EchoBotService {

    private final EchoBotDao echoBotDao;
    private final Delay delay;

    public EchoBotService(EchoBotDao echoBotDao, Delay delay) {
        this.echoBotDao = echoBotDao;
        this.delay = delay;
    }
    ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

    public void updateQueueDelay(Delay delay){
        this.delay.setDelay(delay.getDelay());
    }

    public Response receiveEchoMessage(MessageInner messageInner) {
        if (Objects.equals(messageInner.getText(), "/start")) {
            echoBotDao.addNewUser(messageInner.getUserSender());
            return null;
        } else {
            Integer count = echoBotDao.updateAndGetMsgCountForUser(messageInner.getUserSender()).get();
            Response response = new Response(count, true);
            return response;
        }
    }

}
