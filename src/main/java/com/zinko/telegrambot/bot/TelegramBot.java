package com.zinko.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message requestMessage = update.getMessage();
            long chatId = requestMessage.getChatId();
            log.info(requestMessage.getText());
            log.info(requestMessage.getChatId().toString());
            if (requestMessage.hasText()) {
                if (requestMessage.getText().equals("/start")) {
                    sendMessage(chatId, "I'm working");
                } else {
                    sendMessage(chatId, "Unknown command");
                }
            } else {
                sendMessage(chatId, "Incorrect data format");
            }
        }
    }

    private void sendMessage(long chatId, String message) {
        SendMessage responseMessage = new SendMessage();
        responseMessage.setChatId(chatId);
        responseMessage.setText(message);
        try {
            execute(responseMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
