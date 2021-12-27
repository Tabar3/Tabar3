package com.example.tabar3;


public class Cat_Item {

    String catId, name, type, numHuman, UserId;
    String numclothe, Des,typeCat,bookedName,bookedId;
    Boolean booked;
    String show;

    public Cat_Item() {
    }

    public Cat_Item(String catId, String name, String type, String numHuman, String UserId, String numclothe, String Des, String typeCat, Boolean booked,String bookedName,String bookedId,String show) {
        this.catId = catId;
        this.name = name;
        this.type = type;
        this.numHuman = numHuman;
        this.UserId = UserId;
        this.numclothe = numclothe;
        this.Des = Des;
        this.show=show;
        this.typeCat = typeCat;
        this.booked = booked;
        this.bookedName=bookedName;
        this.bookedId=bookedId;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getBookedId() {
        return bookedId;
    }

    public void setBookedId(String bookedId) {
        this.bookedId = bookedId;
    }

    public String getBookedName() {
        return bookedName;
    }

    public void setBookedName(String bookedName) {
        this.bookedName = bookedName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumHuman() {
        return numHuman;
    }

    public void setNumHuman(String numHuman) {
        this.numHuman = numHuman;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getNumclothe() {
        return numclothe;
    }

    public void setNumclothe(String numclothe) {
        this.numclothe = numclothe;
    }


    public String getDes() {
        return Des;
    }

    public void setDes(String Des) {
        this.Des = Des;
    }

    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }
}