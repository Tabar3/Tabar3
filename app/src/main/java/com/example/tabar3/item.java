package com.example.tabar3;

import java.util.ArrayList;


public class item {
   String charityId,charityName,charityLoc, charityPhone ,charityDes,charityEmail,typeOfUser;
   String UserEmail,UserId,UserPhone,UserName,UserLoc;
   Boolean accept;

   public item(String charityId, String charityName, String charityLoc, String charityPhone, String charityDes, String charityEmail, String typeOfUser, String UserEmail, String UserId, String UserPhone, String UserName, String UserLoc , Boolean accept) {
      this.charityId = charityId;
      this.charityName = charityName;
      this.charityLoc = charityLoc;
      this.charityPhone = charityPhone;
      this.charityDes = charityDes;
      this.charityEmail = charityEmail;
      this.typeOfUser = typeOfUser;
      this.UserEmail = UserEmail;
      this.UserId = UserId;
      this.UserPhone = UserPhone;
      this.UserName = UserName;
      this.UserLoc = UserLoc;
      this.accept=accept;
   }

   public Boolean getAccept() {
      return accept;
   }

   public void setAccept(Boolean accept) {
      this.accept = accept;
   }

   public item(String charityId, String charityName, String charityLoc, String charityPhone, String charityDes, String charityEmail, String typeOfUser) {
      this.charityId = charityId;
      this.charityName = charityName;
      this.charityLoc = charityLoc;
      this.charityPhone = charityPhone;
      this.charityDes = charityDes;
      this.charityEmail = charityEmail;
      this.typeOfUser = typeOfUser;
   }

   public item() {
   }

   public String getCharityEmail() {
      return charityEmail;
   }

   public String getTypeOfUser() {
      return typeOfUser;
   }

   public void setTypeOfUser(String typeOfUser) {
      this.typeOfUser = typeOfUser;
   }

   public void setCharityEmail(String charityEmail) {
      this.charityEmail = charityEmail;
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

   public String getUserEmail() {
      return UserEmail;
   }

   public void setUserEmail(String UserEmail) {
      this.UserEmail = UserEmail;
   }

   public String getUserId() {
      return UserId;
   }

   public void setUserId(String UserId) {
      this.UserId = UserId;
   }

   public String getUserPhone() {
      return UserPhone;
   }

   public void setUserPhone(String UserPhone) {
      this.UserPhone = UserPhone;
   }

   public String getUserName() {
      return UserName;
   }

   public void setUserName(String UserName) {
      this.UserName = UserName;
   }

   public String getUserLoc() {
      return UserLoc;
   }

   public void setUserLoc(String UserLoc) {
      this.UserLoc = UserLoc;
   }
}


