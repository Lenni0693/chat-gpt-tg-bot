package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class TelegramBotGPT extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // обрабатываем сообщение и отправляем ответ
            String response = generateResponse(update.getMessage().getText());
            sendResponse(update.getMessage().getChatId(), response);
        }
    }

    @Override
    public String getBotUsername() {
        return "GPT telegram bot";
    }

    @Override
    public String getBotToken() {
        return "6235032770:AAEehmwFkQcuodakGdCPS52XYy2Jtj6X7j0";
    }

    private String generateResponse(String message) {
        ChatGPTClient chatGPTClient = new ChatGPTClient();
        String response = "";
        try {
            response = chatGPTClient.generateResponse(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Ответ на сообщение: " + response;
    }

    private void sendResponse(Long chatId, String response) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
