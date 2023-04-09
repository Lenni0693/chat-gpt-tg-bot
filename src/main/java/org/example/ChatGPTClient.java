package org.example;

import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatGPTClient {
    private final String API_URL = "https://api.openai.com";
    private final String API_KEY = "Bearer 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";

    private ChatGPTService service;

    public ChatGPTClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        service = retrofit.create(ChatGPTService.class);
    }

    public String generateResponse(String prompt) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("model", "text-davinci-003");
        body.addProperty("prompt", prompt);
        body.addProperty("temperature", 0.5);
        body.addProperty("max_tokens", 4000);

        Call<JsonObject> call = service.generateResponse(API_KEY, body);
        Response<JsonObject> response = call.execute();

        if (response.isSuccessful()) {
            JsonObject responseBody = response.body();
            String text = responseBody.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString();
            return text.trim();
        } else {
            throw new IOException("Unexpected HTTP response: " + response.code() + " " + response.message());
        }
    }
}
