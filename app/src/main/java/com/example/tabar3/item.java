package com.example.tabar3;

import java.util.ArrayList;


public class item {
   String charityId;
   String charityName;

   public item(String charityId, String charityName) {
      this.charityId = charityId;
      this.charityName = charityName;
   }

   public item(String charityName) {
      this.charityName = charityName;
   }

   public item() {
   }

   public String getCharityId() {
      return charityId;
   }

   public void setCharityId(String charityId) {
      this.charityId = charityId;
   }

   public String getCharityName() {
      return charityName;
   }

   public void setCharityName(String charityName) {
      this.charityName = charityName;
   }
}


