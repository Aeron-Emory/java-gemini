package bot;

import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class GoogleAIClient {
    private static final String API_URL = "https://api.generativeai.googleapis.com/v1beta2/models/gemini-1.5-flash:generateText";
    private static final String API_KEY = System.getenv("GOOGLE_API_KEY"); // API key from environment variable

    public static String generateResponse(String prompt) {
        OkHttpClient client = new OkHttpClient();
        JsonObject json = new JsonObject();
        json.addProperty("prompt", prompt);
        json.addProperty("maxOutputTokens", 200);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
                return jsonResponse.getAsJsonObject("data").get("text").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I couldn't generate a response.";
    }
}
