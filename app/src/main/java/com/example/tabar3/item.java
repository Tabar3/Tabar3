package com.example.tabar3;

import java.util.ArrayList;


public class item {
   String ItemId;
   String ItemName;

   public item(String itemId, String itemName) {
      ItemId = itemId;
      ItemName = itemName;
   }

   public String getItemId() {
      return ItemId;
   }

   public void setItemId(String itemId) {
      ItemId = itemId;
   }

   public String getItemName() {
      return ItemName;
   }

   public void setItemName(String itemName) {
      ItemName = itemName;
   }
}


