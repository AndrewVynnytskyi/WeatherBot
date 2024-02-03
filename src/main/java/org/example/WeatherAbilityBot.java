package org.example;

import org.example.dtos.WeatherDto;
import org.example.dtos.WeatherForecast7Dto;
import org.example.dtos.WeatherForecastDto;
import org.example.network.Forecast7Querries;
import org.example.network.ForecastQuerries;
import org.example.network.WeatherClient;
import org.example.network.WeatherQueries;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class WeatherAbilityBot extends AbilityBot {
    private final String appid = ;

    private final String appid1 = ;
    private final WeatherClient Client = new WeatherClient();
    private final WeatherQueries weatherClient = Client.getWeatherClient();
    private final ForecastQuerries forecastClient = Client.getForecastClient();
    private  final Forecast7Querries forecast7Client = Client.getForecast7Client();
    private static Location location =new Location();


    protected WeatherAbilityBot() {
        super( );
    }

    @Override
    public long creatorId() {
        return ;
    }




    public Reply GetGeo()
    {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd)  ->
        {
             location.setLatitude(upd.getMessage().getLocation().getLatitude());
             location.setLongitude(upd.getMessage().getLocation().getLongitude());

             if (location != null)
             {
                 Map<Long, Location> Geoposition = db.getMap("location");
                 Geoposition.put(upd.getMessage().getChatId(),location);
                 silent.send("Your location set successfully", getChatId(upd));
             }
        };
        return Reply.of(action, Flag.LOCATION);
    }

    public Reply getMarkupReply() {
        BiConsumer<BaseAbilityBot , Update> action = (bot ,upd) ->
        {
            String call_data = upd.getCallbackQuery().getData();
            var message = (Message) upd.getCallbackQuery().getMessage();
            long chat_id = upd.getCallbackQuery().getMessage().getChatId();
            Map<Long, ArrayList<String>> currentW7 = db.getMap("weather7");
            ArrayList<String> currentWeather = currentW7.get(chat_id);
            Map<Long, ArrayList<String>> currentDate7 = db.getMap("Date");
            ArrayList<String> currentDate = currentDate7.get(chat_id);
            String answer = "";
            switch (call_data)
            {
                case "0" -> answer = currentWeather.getFirst();
                case "1" -> answer = currentWeather.get(1);
                case "2" -> answer = currentWeather.get(2);
                case "3" -> answer = currentWeather.get(3);
                case "4" -> answer = currentWeather.get(4);
                case "5" -> answer = currentWeather.get(5);
                case "6" -> answer = currentWeather.get(6);

            }
            EditMessageText new_message = new EditMessageText();
            new_message.setChatId(Long.toString(chat_id));
            new_message.setText(answer);
            new_message.setMessageId(message.getMessageId());
            new_message.setReplyMarkup(createInlineKeyboard(currentDate));
            try {
                execute(new_message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        };


            return Reply.of(action, Flag.CALLBACK_QUERY);
    }

    public Ability start()
    {
       return Ability.builder()
               .name("start")
               .info("Start Weather bot")
               .locality(USER)
               .privacy(PUBLIC)
               .setStatsEnabled(true)
               .action(ctx ->
               {
                   try {
                       requestGeo(ctx.chatId() , "\uD83C\uDF26\uFE0F Welcome to WeatherBot! \uD83C\uDF08\n" +
                               "\n" +
                               "I'm here to provide you with the latest weather updates. I'll fetch the current conditions, forecast, and more. Feel free to explore and stay informed! Type /help for a list of available commands.\n" +
                               "\n" +
                               "\uD83C\uDF0D Start by telling me the city or providing your location to get started. Enjoy the weather updates! ☀\uFE0F❄\uFE0F\n");
                   } catch (TelegramApiException e) {
                       throw new RuntimeException(e);
                   }




               }).build();
    }

    public Ability currentWeather()
    {
        return Ability.builder().name("current")
                .info("Send a current weather")
                .locality(USER)
                .privacy(PUBLIC)
                .setStatsEnabled(true)
                .action(ctx ->
                        {
                            Map<Long, Location> currentL = db.getMap("location");
                            Location loc = currentL.get(ctx.chatId());
                            weatherClient.getWeather(loc.getLatitude(),loc.getLongitude(),appid, "metric").enqueue(
                                    new Callback<>() {
                                        @Override
                                        public void onResponse(Call<WeatherDto> call, Response<WeatherDto> response) {
                                            if(response.isSuccessful())
                                            {
                                                assert response.body() != null;
                                                silent.send(response.body().toString(), ctx.chatId());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WeatherDto> call, Throwable throwable) {
                                            System.out.println("Error: " + throwable);
                                            throwable.printStackTrace();
                                        }
                                    }
                            );
                        }
                ).build();
    }
    public Ability weatherForecast()
    {
        return Ability.builder()
                .name("detailed_forecast")
                .info("Forecast for five days with step of three hours")
                .locality(USER)
                .privacy(PUBLIC)
                .enableStats()
                .action(ctx ->
                        {
                            Map<Long, Location> currentL = db.getMap("location");
                            Location loc = currentL.get(ctx.chatId());
                            forecastClient.getForecst(loc.getLatitude(), loc.getLongitude(), appid, "metric").enqueue(

                                    new Callback<WeatherForecastDto>() {
                                        @Override
                                        public void onResponse(Call<WeatherForecastDto> call, Response<WeatherForecastDto> response) {
                                            if(response.isSuccessful()) {
                                                if(response.isSuccessful() && response.body()!=null) {

                                                    int index = 0;
                                                    ArrayList<String> message = response.body().toArrayD();
                                                    Map<Long, ArrayList<String>> currentW7 = db.getMap("weather7");
                                                    currentW7.put(ctx.chatId(), response.body().toArrayD());
                                                    Map<Long, ArrayList<String>> currentUserDate = db.getMap("Date");
                                                    currentUserDate.put(ctx.chatId(), response.body().toDataArrayD());
                                                    SendMessage sendMessage = new SendMessage(Long.toString(ctx.chatId()), message.getFirst());
                                                    sendMessage.setReplyMarkup(createInlineKeyboard(response.body().toDataArrayD()));
                                                    try {
                                                        execute(sendMessage);
                                                    } catch (TelegramApiException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                }


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WeatherForecastDto> call, Throwable throwable) {
                                            System.out.println("Error: " + throwable);
                                            throwable.printStackTrace();
                                        }
                                    }
                            );
                        }

                ).build();
    }



    public Ability weatherForecast7()
    {
        return Ability.builder()
                .name("forecast_for_7_days")
                .info("Simplified forecast")
                .locality(USER)
                .privacy(PUBLIC)
                .setStatsEnabled(true)
                .action(ctx ->
                        {

                            Map<Long, Location> currentL = db.getMap("location");
                            Location loc = currentL.get(ctx.chatId());
                            forecast7Client.getForecast7(loc.getLatitude(), loc.getLongitude(),"daily","EET","metric",appid1 ).enqueue(

                                    new Callback<WeatherForecast7Dto>() {
                                        @Override
                                        public void onResponse(Call<WeatherForecast7Dto> call, Response<WeatherForecast7Dto> response) {
                                            if(response.isSuccessful() && response.body()!=null) {

                                                int index = 0;
                                                ArrayList<String> message = response.body().toArray();
                                                Map<Long, ArrayList<String>> currentW7 = db.getMap("weather7");
                                                currentW7.put(ctx.chatId(), response.body().toArray());
                                                Map<Long, ArrayList<String>> currentUserDate = db.getMap("Date");
                                                currentUserDate.put(ctx.chatId(), response.body().toDataArray());
                                                SendMessage sendMessage = new SendMessage(Long.toString(ctx.chatId()), message.getFirst());
                                                sendMessage.setReplyMarkup(createInlineKeyboard(response.body().toDataArray()));
                                                try {
                                                    execute(sendMessage);
                                                } catch (TelegramApiException e) {
                                                    throw new RuntimeException(e);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WeatherForecast7Dto> call, Throwable throwable) {
                                            System.out.println("Error: " + throwable);
                                            throwable.printStackTrace();
                                        }
                                    }
                            );
                        }

                ).build();
    }
    public Ability help() {
        return Ability.builder()
                .name("help")
                .info("Current commands")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    silent.send("""
                            Current commands for now:
                            /start
                            /current
                            /detailed_forecast
                            /forecast_for_7_days
                            /edit_geolocation
                            /for_one_day
                            /help""", ctx.chatId());
                }).build();
    }

    public Ability editGeolocation() {
        return Ability.builder()
                .name("edit_geolocation")
                .info("Edit your geolocation")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        requestGeo(ctx.chatId(), "If you want to update your geolocation, please press this button");
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }).build();
    }

    public Ability detailedForOneDay() {
        return Ability.builder()
                .name("for_one_day")
                .info("Forecast for one dat")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    Map<Long, Location> currentL = db.getMap("location");
                    Location loc = currentL.get(ctx.chatId());
                    forecast7Client.getForecast7(loc.getLatitude(), loc.getLongitude(), "hourly","EET", "metric",appid1).enqueue(
                            new Callback<WeatherForecast7Dto>() {
                                @Override
                                public void onResponse(Call<WeatherForecast7Dto> call, Response<WeatherForecast7Dto> response) {
                                    if(response.isSuccessful() && response.body()!=null) {
                                        silent.send(response.body().toString(), ctx.chatId());
                                    }
                                }

                                @Override
                                public void onFailure(Call<WeatherForecast7Dto> call, Throwable throwable) {
                                    System.out.println("Error: " + throwable);
                                    throwable.printStackTrace();
                                }
                            }
                    );
                }).build();
    }


    void requestGeo(long chatId, String message) throws TelegramApiException {
        KeyboardButton keyboardButton = new KeyboardButton();

        keyboardButton.setText("Get access to your geolocation");
        keyboardButton.setRequestLocation(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
        keyboardRows.add(keyboardRow);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        SendMessage sendMessage = new SendMessage(Long.toString(chatId), message);
        sendMessage.setReplyMarkup(keyboardMarkup);

        execute(sendMessage);
    }

    private InlineKeyboardMarkup createInlineKeyboard(ArrayList<String> message) {


        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtons1 = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        for(String element : message)
        {
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(message.get(message.indexOf(element)));
            keyboardButton.setCallbackData(Integer.toString((message.indexOf(element))));
            if(message.indexOf(element) < 3)
            {
                keyboardButtons.add(keyboardButton);
            }
            else{
                keyboardButtons1.add(keyboardButton);
            }
        }
        keyboardRows.add(keyboardButtons);
        keyboardRows.add(keyboardButtons1);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }

}
