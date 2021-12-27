package com.example.tabar3;

public class mo_Item{

    String moId,charId,moName,type,moNum;

    public mo_Item() {
    }

    public mo_Item(String moId, String charId, String moName, String type, String moNum) {
        this.moId = moId;
        this.charId = charId;
        this.moName = moName;
        this.type = type;
        this.moNum = moNum;
    }

    public String getMoId() {
        return moId;
    }

    public void setMoId(String moId) {
        this.moId = moId;
    }

    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getMoName() {
        return moName;
    }

    public void setMoName(String moName) {
        this.moName = moName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoNum() {
        return moNum;
    }

    public void setMoNum(String moNum) {
        this.moNum = moNum;
    }
}