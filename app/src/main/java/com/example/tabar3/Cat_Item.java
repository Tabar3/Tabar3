package com.example.tabar3;

public class Cat_Item  {

    String foodId,foodName,foodDes,foodType,numHuman,region,street,building,phone;
    String clotheId,numclothe,clotheDes,clothesType;
    String toolId,toolAge,toolDes,toolType;

    public Cat_Item(String foodId, String foodName, String foodDes, String foodType, String numHuman, String region, String street, String building, String phone) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDes = foodDes;
        this.foodType = foodType;
        this.numHuman = numHuman;
        this.region = region;
        this.street = street;
        this.building = building;
        this.phone = phone;
    }
    public Cat_Item(){}

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDes() {
        return foodDes;
    }

    public void setFoodDes(String foodDes) {
        this.foodDes = foodDes;
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
}