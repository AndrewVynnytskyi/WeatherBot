package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {


        TelegramBotsApi Weather = new TelegramBotsApi(DefaultBotSession.class);
        Weather.registerBot(new WeatherAbilityBot());
        int port = Integer.parseInt(System.getenv("PORT"));
        GreetServer server=new GreetServer();
        server.start(port);
    }
}