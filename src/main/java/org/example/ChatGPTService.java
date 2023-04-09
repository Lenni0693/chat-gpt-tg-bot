package org.example;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import com.google.gson.JsonObject;

public interface ChatGPTService {
    @POST("/v1/completions")
    Call<JsonObject> generateResponse(@Header("Authorization") String apiKey, @Body JsonObject body);

}
