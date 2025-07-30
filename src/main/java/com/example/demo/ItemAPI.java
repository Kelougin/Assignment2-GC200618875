package com.example.demo;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class ItemAPI {

    private static final String BASE_URL = "https://secure.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<Item> fetchAll(int maxItems) throws IOException, InterruptedException {
        List<Item> items = new ArrayList<>();
        int itemIdIncrement = 21787;

        while (items.size() < maxItems) {
            String url = BASE_URL + itemIdIncrement;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Failed : HTTP error code : " + response.statusCode() + "-" + response.body());
            }
            else {
                System.out.print("Success : HTTP status code ");
            }
            JsonObject rootObject = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject itemObject = rootObject.getAsJsonObject("item");
            Item item = new Gson().fromJson(itemObject, Item.class);
            items.add(item);
        }
        return items;
    }
}
