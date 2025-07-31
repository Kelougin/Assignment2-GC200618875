package com.example.demo.Controller;

import com.example.demo.API.DetailsAPI;
import com.example.demo.Model.Details;
import com.example.demo.Model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DetailsController {

    @FXML
    private Label changeIcon; //Label to hold change information

    @FXML
    private ImageView itemIcon; //Image for the item

    @FXML
    private Label membersLabel; //Label for if item is member

    @FXML
    private Label nameLabel; //Label for items name

    @FXML
    private Label priceLabel; //Label for price

    @FXML
    private Label trendIcon; //Label for pricing trends

    public void initialize(Item item) {
        try {
            Details details = new DetailsAPI().fetchDetails(item); //Fetches item details
            nameLabel.setText(details.getName()); //Set name label value
            priceLabel.setText("Price: "+ details.getPrice() + " gp"); //Set price label value
            trendIcon.setText("Trending: " + details.getToday()); //Set trend label value
            changeIcon.setText("90 Changes: " + details.getChange()); //Set change label value
            membersLabel.setText("Members: " + details.getMembers()); //Set members lable value
            itemIcon.setImage(new Image(details.getIcon())); //Sets image using url
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); //Throws exception
        }
    }
}
