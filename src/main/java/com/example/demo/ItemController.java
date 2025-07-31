package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ItemController {

    @FXML
    private TableColumn<Item, String> colDescription;

    @FXML
    private TableColumn<Item, String> colName;

    @FXML
    private TableColumn<Item, String> colType;

    @FXML
    private TableView<Item> table;

    @FXML
    private TextField search;

    private List<Item> items = new ArrayList<>();

    @FXML
    public void initialize(){
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        table.setRowFactory(event ->{
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(click -> {
                if(!row.isEmpty() && click.getClickCount() == 2){
                    Item itemClicked = row.getItem();
                    //showDetail(itemClicked);
                }
            });
            return row;
        });

        try{
            List<Item> itemFetched = new ItemAPI().fetchAll(100);
            items.addAll(itemFetched);
            javafx.application.Platform.runLater(()-> table.getItems().setAll(items));
        }catch(Exception e){
            System.out.printf("Error: %s\n",e.getMessage());;
        }

        search.textProperty().addListener((observable, oldValue, newValue) -> filterItems(newValue));
    }
    private void filterItems(String itemLookedUp){
        if(itemLookedUp == null || itemLookedUp.isBlank()){
            table.getItems().setAll(items);
            return;
        }
        String lowerCase = itemLookedUp.toLowerCase();
        List<Item> filteredItems = new ArrayList<>();
        for(Item item : items){
            if(item.getName().toLowerCase().contains(lowerCase)){
                filteredItems.add(item);
            }
        }
        table.getItems().setAll(filteredItems);
    }
}
