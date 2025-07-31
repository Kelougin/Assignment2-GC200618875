package com.example.demo.Model;

public class Details {
    private String members; //Variable for holding members value
    private String icon; //Variable for holding icon value
    private String name; //Variable for holding name value
    private String change; //Variable for holding change value
    private String price; //Variable for holding price value
    private String today; //Variable for holding todays change value

    //Getter for today value
    public String getToday() {
        return today;
    }
    //Setter for today change value
    public void setToday(String today) {
        this.today = today;
    }
    //Setter for members
    public void setMembers(String members) {
        this.members = members;
    }
    //Setter for icon
    public void setIcon(String icon) {
        this.icon = icon;
    }
    //Setter for name
    public void setName(String name) {
        this.name = name;
    }
    //Setter for price
    public void setPrice(String price) {
        this.price = price;
    }
    //Setter for change
    public void setChange(String change) {
        this.change = change;
    }
    //Getter for item icon
    public String getIcon() {
        return icon;
    }
    //Getter for members
    public String getMembers() {
        return members;
    }
    //Getter for change vallue
    public String getChange() {
        return change;
    }
    //Getter for name
    public String getName() {
        return name;
    }
    //Getter for price
    public String getPrice() {
        return price;
    }
}
