package com.example.tabar3;


public class Camp_Item {

    String campId,campName,campDes1,campDes2;

    public Camp_Item(String campId, String campName, String campDes1, String campDes2) {
        this.campId = campId;
        this.campName = campName;
        this.campDes1 = campDes1;
        this.campDes2 = campDes2;
    }

    public Camp_Item(){}

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampDes1() {
        return campDes1;
    }

    public void setCampDes1(String campDes1) {
        this.campDes1 = campDes1;
    }

    public String getCampDes2() {
        return campDes2;
    }

    public void setCampDes2(String campDes2) {
        this.campDes2 = campDes2;
    }
}