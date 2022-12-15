package com.example.tgbotecho.bot;

import com.example.tgbotecho.client.EchoBotHttpClient;
import com.example.tgbotecho.domain.Delay;
import com.example.tgbotecho.domain.MessageInner;
import com.example.tgbotecho.domain.Response;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class EchoBot extends TelegramLongPollingBot {

    private final EchoBotHttpClient echoBotHttpClient;
    private final Delay delay;


    public EchoBot(EchoBotHttpClient echoBotHttpClient,
                   Delay delay) {
        this.echoBotHttpClient = echoBotHttpClient;
        this.delay = delay;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();
                MessageInner message = new MessageInner(inMessage.getText(), inMessage.getFrom().getUserName());
                Thread.sleep(delay.getDelay());
                Response response = echoBotHttpClient.receiveEchoMessage(message);
                SendMessage outMessage = new SendMessage();
                outMessage.setText(inMessage.getText() + " " + response.getMsgCount());
                outMessage.setChatId(inMessage.getChatId());
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return "nemanovidtest_bot";
    }

    @Override
    public String getBotToken() {
        return "5844961988:AAFOWVvPk6DdHaFdgXzH_yUoa6j5ib9tFHQ";
    }
}

