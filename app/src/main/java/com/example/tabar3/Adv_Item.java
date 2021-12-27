package com.example.tabar3;


import java.util.ArrayList;

public class Adv_Item {

   String advId, charId;
   String advDes,charName, typeOfAdv;
   String advNum, advName;

   public Adv_Item() {

   }

   public Adv_Item(String advId, String charId, String advDes, String typeOfAdv, String advNum, String advName,String charName) {
      this.advId = advId;
      this.charId = charId;
      this.advDes = advDes;
      this.typeOfAdv = typeOfAdv;
      this.advNum = advNum;
      this.advName = advName;
      this.charName=charName;
   }

   public String getCharName() {
      return charName;
   }

   public void setCharName(String charName) {
      this.charName = charName;
   }

   public String getAdvId() {
      return advId;
   }

   public void setAdvId(String advId) {
      this.advId = advId;
   }

   public String getCharId() {
      return charId;
   }

   public void setCharId(String charId) {
      this.charId = charId;
   }

   public String getAdvDes() {
      return advDes;
   }

   public void setAdvDes(String advDes) {
      this.advDes = advDes;
   }

   public String getTypeOfAdv() {
      return typeOfAdv;
   }

   public void setTypeOfAdv(String typeOfAdv) {
      this.typeOfAdv = typeOfAdv;
   }

   public String getAdvNum() {
      return advNum;
   }

   public void setAdvNum(String advNum) {
      this.advNum = advNum;
   }

   public String getAdvName() {
      return advName;
   }

   public void setAdvName(String advName) {
      this.advName = advName;
   }
}