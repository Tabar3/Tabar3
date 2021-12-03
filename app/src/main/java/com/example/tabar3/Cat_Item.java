package com.example.tabar3;

public class Cat_Item {

    String catId, foodName, foodType, numHuman, region, street, building, phone, UserId;
    String numclothe, clothesType;
    String toolAge, Des, toolType,typeCat;
    Boolean booked;

    public Cat_Item() {
    }

    public Cat_Item(String catId, String foodName, String foodType, String numHuman, String region, String street, String building, String phone, String userId, String numclothe, String clothesType, String toolAge, String des, String toolType, String typeCat, Boolean booked) {
        this.catId = catId;
        this.foodName = foodName;
        this.foodType = foodType;
        this.numHuman = numHuman;
        this.region = region;
        this.street = street;
        this.building = building;
        this.phone = phone;
        UserId = userId;
        this.numclothe = numclothe;
        this.clothesType = clothesType;
        this.toolAge = toolAge;
        Des = des;
        this.toolType = toolType;
        this.typeCat = typeCat;
        this.booked = booked;
    }

    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getNumHuman() {
        return numHuman;
    }

    public void setNumHuman(String numHuman) {
        this.numHuman = numHuman;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNumclothe() {
        return numclothe;
    }

    public void setNumclothe(String numclothe) {
        this.numclothe = numclothe;
    }

    public String getClothesType() {
        return clothesType;
    }

    public void setClothesType(String clothesType) {
        this.clothesType = clothesType;
    }

    public String getToolAge() {
        return toolAge;
    }

    public void setToolAge(String toolAge) {
        this.toolAge = toolAge;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }
}