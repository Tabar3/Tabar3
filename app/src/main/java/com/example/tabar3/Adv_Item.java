package com.example.tabar3;


import java.util.ArrayList;

public class Adv_Item {

   String advId, charId;
   String advDes;
   ArrayList<Integer> typeOfAdv;
   String advNum, advName;

   public Adv_Item() {

   }

   public Adv_Item(String advId, String charId, String advDes, ArrayList<Integer> typeOfAdv, String advNum, String advName) {
      this.advId = advId;
      this.charId = charId;
      this.advDes = advDes;
      this.typeOfAdv = typeOfAdv;
      this.advNum = advNum;
      this.advName = advName;
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

   public ArrayList<Integer> getTypeOfAdv() {
      return typeOfAdv;
   }

   public void setTypeOfAdv(ArrayList<Integer> typeOfAdv) {
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