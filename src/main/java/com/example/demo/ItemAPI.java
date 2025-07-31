package com.example.demo;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class ItemAPI {

    private static final String BASE_URL = "https://secure.runescape.com/m=itemdb_rs/api/catalogue/items.json?category="; //Variable to hold base url

    private final HttpClient httpClient = HttpClient.newHttpClient(); //Variable for http client

    /**
     * Method for fetching the api
     * @param maxItems is the number of items you want fetched
     * @return An array of item objects*/
    public List<Item> fetchAll(int maxItems) throws IOException, InterruptedException {
        List<Item> items = new ArrayList<>(); //Array for items
        int page = 1; //Variable for page number
        int letterIncrement = 0; //Variable for letter increment
        int category = 0; //Variable for category number
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",  "r", "s", "t", "u", "v", "w", "x", "y", "z"}; //Array of letters

        while (items.size() < maxItems) {
            String url = BASE_URL + category + "&alpha=" + letters[letterIncrement] + "&page=" + page; //variable for holding api url
            //System.out.println("URL: " + url); for debugging
            HttpRequest request = HttpRequest.newBuilder() //build http request
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); //Stores response in variable
            String responseBody = response.body().trim(); //variable for holding response body
            if (!responseBody.startsWith("{")) { //Check if response body start like a json because for some reason api request are inconsistent and sometime I get an error saying not a json file even though it should be
                System.out.println("Reconnecting " + url); //Prints reconnecting to the url to see which url is failing
                continue; //Continues the loop
            }
            if (response.statusCode() != 200) { //If connection failed7
                throw new IOException("Failed : HTTP error code : " + response.statusCode() + "-" + response.body()); //Throws exception
            }
            JsonObject rootObject = JsonParser.parseString(response.body()).getAsJsonObject(); //Gets json file
            JsonArray itemObject = rootObject.getAsJsonArray("items"); //Gets array of items using items key
            for(JsonElement jsonElement : itemObject) { //For loop to go through each object
                Item item = new Gson().fromJson(jsonElement, Item.class); //Gets values from api to store in class
                items.add(item); // adds item to array
                if(items.size() >= maxItems) break; //Break if item size greater or equal to maxItem number
            }

            if(itemObject.size() <=0){ //If there is no item in the api
                if(letters[letterIncrement].equals("z")){ //Checks if letter is on z
                    letterIncrement = 0; //Reset letter increment
                    page = 1; //Reset page
                    category++; //Increase category number
                }else { //If not on z
                    letterIncrement++; //Move on to next letter
                    page = 1; //reset page to 1
                }
                continue; //Continue the loop
            }
            if(category >= 44) break;
            page++; //Increase page count
        }
        return items; //returns items
    }
}
