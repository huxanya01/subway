package com.example.graduationtermpaper;

import android.content.Context;
import android.widget.Toast;

public class OldPrefData {
    PrefData prefData;
    int price,addPrice,sidePrice,drinkPrice,number;
    String toast,bread,cheese,side,drink;
    String[] add,veggie,pickle,sauce;

    OldPrefData(PrefData prefData){
        this.prefData = prefData;
        backupPrefData(this.prefData);


    }

    public void backupPrefData(PrefData prefData){

        price = prefData.getPrice();
        addPrice = prefData.getAddPrice();
        sidePrice = prefData.getSidePrice();
        drinkPrice = prefData.getDrinkPrice();

        toast = prefData.getToast();
        bread = prefData.getBread();
        cheese = prefData.getCheese();
        add = prefData.getAdd();
        veggie = prefData.getVeggie();
        pickle = prefData.getPickle();
        sauce = prefData.getSauce();
        side = prefData.getSide();
        drink = prefData.getDrink();

        number = prefData.getNumber();

    }

    public void restorePrefData(){
        prefData.setPrice(price);
        prefData.setAddPrice(addPrice);
        prefData.setSidePrice(sidePrice);
        prefData.setDrinkPrice(drinkPrice);

        prefData.setToast(toast);
        prefData.setBread(bread);
        prefData.setCheese(cheese);

        prefData.clearAdd();
        prefData.setAddArray(add);

        prefData.setVeggie(veggie);
        prefData.setPickle(pickle);
        prefData.setSauce(sauce);
        prefData.setSide(side);
        prefData.setDrink(drink);

        prefData.setNumber(number);
    }
}
