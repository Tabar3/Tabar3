package com.example.tabar3;

import java.util.ArrayList;


public class item {
   String charityId,charityName,charityLoc, charityPhone ,charityDes;

   public item(String charityId, String charityName, String charityLoc, String charityPhone, String charityDes) {
      this.charityId = charityId;
      this.charityName = charityName;
      this.charityLoc = charityLoc;
      this.charityPhone = charityPhone;
      this.charityDes = charityDes;
   }

   public item(String charityName) {
      this.charityName = charityName;
   }

   public item() {
   }

   public String getCharityDes() {
      return charityDes;
   }

   public void setCharityDes(String charityDes) {
      this.charityDes = charityDes;
   }

   public String getCharityLoc() {
      return charityLoc;
   }

   public void setCharityLoc(String charityLoc) {
      this.charityLoc = charityLoc;
   }

   public String getCharityPhone() {
      return charityPhone;
   }

   public void setCharityPhone(String charityphone) {
      this.charityPhone = charityphone;
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


