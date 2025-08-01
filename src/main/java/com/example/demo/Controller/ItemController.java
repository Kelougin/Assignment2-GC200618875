package com.example.demo.Controller;

import com.example.demo.API.ItemAPI;
import com.example.demo.StartApplication;
import com.example.demo.Model.Item;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemController {

    @FXML
    private TableColumn<Item, String> colDescription; //Variable for description column

    @FXML
    private TableColumn<Item, String> colName; //Variable for name column

    @FXML
    private TableColumn<Item, String> colType; //Variable for type column

    @FXML
    private TableView<Item> table; //Variable for table

    @FXML
    private TextField search; //Variable for text search

    private List<Item> items = new ArrayList<>(); //Array list to hold item

    @FXML
    public void initialize(){
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description")); //Use property value to get value from description
        colName.setCellValueFactory(new PropertyValueFactory<>("name")); //Use property value to get value from name
        colType.setCellValueFactory(new PropertyValueFactory<>("type")); //Use property value to get value from type

        //Set event for table row
        table.setRowFactory(_ ->{
            TableRow<Item> row = new TableRow<>(); //Variable for rows
            row.setOnMouseClicked(click -> { //Set event for on mouse clicked
                if(!row.isEmpty() && click.getClickCount() == 2){ //If row is clicked twice and is not empty do the following
                    Item itemClicked = row.getItem(); //Stores item of the row in a variable
                    try{
                        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("details-view.fxml")); //Get resources from view
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600); //Loads resources in scene
                        DetailsController detailsController = fxmlLoader.getController(); //Loads controller
                        detailsController.initialize(itemClicked); //Runs controller with item from row
                        Stage detailsWindow = new Stage(); //Set a new stage
                        detailsWindow.setTitle("Details for: " + itemClicked.getName());  //Set stage title
                        detailsWindow.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/Images/rs3icon.png")).toExternalForm())); //Replace top left icon
                        detailsWindow.setScene(scene); //Set stage scene
                        detailsWindow.initModality(Modality.APPLICATION_MODAL); //Make it so you can't interact with item window while details window is open
                        detailsWindow.show(); //Show stage
                    }catch (Exception e){
                        System.err.println(e.getMessage()); //Prints exception
                    }
                }
            });
            return row; //Returns rows
        });

        try{
            List<Item> itemFetched = new ItemAPI().fetchAll(100); //Fetches items and stored them in array
            items.addAll(itemFetched); //Adds items fetched to items
            Platform.runLater(()-> table.getItems().setAll(items)); //Set values in table
        }catch(Exception e){
            System.out.printf("Error: %s\n",e.getMessage()); //Prints error message
        }

        search.textProperty().addListener((observable, oldValue, newValue) -> filterItems(newValue)); //Event for searching
    }
    /**
     * Method for filtering items
     * @param itemLookedUp String to hold word that is being looked up*/
    private void filterItems(String itemLookedUp){
        if(itemLookedUp == null || itemLookedUp.isBlank()){ //Check if item looked is blank or null
            table.getItems().setAll(items); //Gets all items for table
            return; //Returns
        }
        String lowerCase = itemLookedUp.toLowerCase(); //Set itemlookedup value to lower case
        List<Item> filteredItems = new ArrayList<>(); //Array for holding filtered items
        for(Item item : items){ //Loop through each item
            if(item.getName().toLowerCase().contains(lowerCase)){ //Check if parameter value is equal to an items name
                filteredItems.add(item); //Adds item to filter items
            }
        }
        table.getItems().setAll(filteredItems); //Gets filtered items for table
    }

}
