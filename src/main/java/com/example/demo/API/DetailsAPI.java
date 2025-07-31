package com.example.demo.API;

import com.example.demo.Model.Details;
import com.example.demo.Model.Item;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DetailsAPI {

    private static final String BASE_URL = "https://secure.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item="; //Variable for holding base url

    private final HttpClient httpClient = HttpClient.newHttpClient(); //Variable for http client
    /**
     * Method for fetching the api
     * @param item is the item object that is passed in order to get id
     * @return a detail object in order to populate details window*/
    public Details fetchDetails(Item item) throws IOException, InterruptedException {
        String url = BASE_URL + item.getId(); //Completes url with itm id
        //System.out.println("URL: " + url); for debugging
        HttpRequest request = HttpRequest.newBuilder() //build http request
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); //Stores response in variable
        if (response.statusCode() != 200) { //If connection failed
            throw new IOException("Failed : HTTP error code : " + response.statusCode() + "-" + response.body()); //Throws exception
        }
        JsonObject rootObject = JsonParser.parseString(response.body()).getAsJsonObject(); //Gets json file
        JsonObject itemObject = rootObject.get("item").getAsJsonObject(); //Gets item object use item key
        Details details = new Details(); //Creates new details object
        details.setName(item.getName()); //Sets details name
        details.setMembers(itemObject.get("members").getAsString()); //Sets details for members variable
        details.setPrice(itemObject.getAsJsonObject("current").get("price").getAsString()); //Sets price variable for details
        details.setIcon(itemObject.get("icon").getAsString()); //Sets icon url in details
        details.setChange(itemObject.getAsJsonObject("day90").get("change").getAsString()); //Sets price change
        details.setToday(itemObject.getAsJsonObject("today").get("trend").getAsString()); //Sets price change

        return details; //returns details object

    }
}
