package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class WeatherBot extends TelegramLongPollingBot {

    private static Location location = new Location();
    @Override
    public void onUpdateReceived(Update update) {
        boolean loc = update.getMessage().hasLocation();

        String chatId = update.getMessage().getChatId().toString();


        if (!update.hasMessage() || (!update.getMessage().hasText()&&!(loc))) {
            try {
                respondWithText(chatId, "Error!We don't understand your commands. Sorry! Please use command /help to see available commands");
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        String message = update.getMessage().getText();
        if(loc)
        {
            location.setLatitude(update.getMessage().getLocation().getLatitude());
            location.setLongitude(update.getMessage().getLocation().getLongitude());
        }

        switch (message) {
            case "/start" -> {
                try {
                    requestGeo(chatId, "\uD83C\uDF26\uFE0F Welcome to WeatherBot! \uD83C\uDF08\n" +
                            "\n" +
                            "I'm here to provide you with the latest weather updates. I'll fetch the current conditions, forecast, and more. Feel free to explore and stay informed! Type /help for a list of available commands.\n" +
                            "\n" +
                            "\uD83C\uDF0D Start by telling me the city or providing your location to get started. Enjoy the weather updates! ☀\uFE0F❄\uFE0F\n"
                    );

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    void respondWithText(String chatId, String respMessage) throws TelegramApiException {
        SendMessage respond = new SendMessage(chatId, respMessage);
        execute(respond);
    }

    @Override
    public String getBotUsername() {
        return ;
    }

    @Override
    public String getBotToken() {
        return ;
    }

    private void requestGeo(String chatId, String respMsg) throws TelegramApiException {
        KeyboardButton keyboardButton = new KeyboardButton("Get access to geolocation");
        keyboardButton.setRequestLocation(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        ArrayList<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        keyboardRowList.add(keyboardRow);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRowList);
        SendMessage sendMessage = new SendMessage(chatId, respMsg);
        sendMessage.setReplyMarkup(keyboardMarkup);
        execute(sendMessage);
    }

}
