package com.example.tabar3;


import java.util.ArrayList;

public class Adv_Item {

   String AdvId;
   String AdvDes;
   ArrayList<Integer> typeOfAdv;
   String AdvNum,AdvName;

   public Adv_Item(String AdvId, String AdvDes, ArrayList<Integer> typeOfAdv, String AdvNum, String AdvName) {
      this.AdvId = AdvId;
      this.AdvDes = AdvDes;
      this.typeOfAdv = typeOfAdv;
      this.AdvNum = AdvNum;
      this.AdvName = AdvName;
   }

   public Adv_Item() {
   }

   public String getAdvName() {
      return this.AdvName;
   }

   public void setAdvName(String AdvName) {
      this.AdvName = AdvName;
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
