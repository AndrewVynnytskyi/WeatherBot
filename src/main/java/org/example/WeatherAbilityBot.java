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
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
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
        return;
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
                 silent.send("Your location set sucsesfully", getChatId(upd));
             }
        };
        return Reply.of(action, Flag.LOCATION);
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
                                                assert response.body() != null;
                                                for (String element : response.body().toArray())
                                                {
                                                    silent.send(element, ctx.chatId());
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
                                            if(response.isSuccessful() && response.body()!=null)
                                            {

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
                            /help""", ctx.chatId());
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

}
