package com.example.tabar3;


import java.util.ArrayList;

public class Adv_Item {

   String AdvId;
   String AdvDes;
   ArrayList<Integer> typeOfAdv;
   String AdvNum;

   public Adv_Item(String AdvId, String AdvDes, ArrayList<Integer> typeOfAdv, String AdvNum) {
      this.AdvId = AdvId;
      this.AdvDes = AdvDes;
      this.typeOfAdv = typeOfAdv;
      this.AdvNum = AdvNum;
   }

   public Adv_Item(String AdvId, String AdvDes) {
      this.AdvId = AdvId;
      this.AdvDes = AdvDes;
   }

   public Adv_Item(String AdvDes) {
      this.AdvDes = AdvDes;
   }

   public Adv_Item() {
   }

   public String getAdvId() {
      return AdvId;
   }

   public void setAdvId(String AdvId) {
      this.AdvId = AdvId;
   }

   public String getAdvDes() {
      return AdvDes;
   }

   public void setAdvDes(String AdvDes) {
      this.AdvDes = AdvDes;
   }

   public ArrayList<Integer> getTypeOfAdv() {
      return typeOfAdv;
   }

   public void setTypeOfAdv(ArrayList<Integer> typeOfAdv) {
      this.typeOfAdv = typeOfAdv;
   }

   public String getAdvNum() {
      return AdvNum;
   }

   public void setAdvNum(String AdvNum) {
      this.AdvNum = AdvNum;
   }
}
