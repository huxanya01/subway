package com.example.graduationtermpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrefData {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final String[] meal;
    private List<String> mealList;
    private final String dataXMLName = "test";

    private Set<String> addSet;

    public PrefData(Context context,int count) {
        sp = context.getSharedPreferences(dataXMLName+count,0);
        editor = sp.edit();

        meal = context.getResources().getStringArray(R.array.meal);
        mealList = new ArrayList<>();
        mealList = Arrays.asList(meal);

        if (!getShowFlag()){
            addSet = new HashSet<>();
        }else{ //addSet變數用舊有資料集
            addSet = sp.getStringSet("addSet",new HashSet<>());
        }

    }
    public void setShowFlag(boolean flag){editor.putBoolean("showFlag",flag).commit();}

    public boolean getShowFlag(){return sp.getBoolean("showFlag",false);}

    public void setIndex(int index){
        editor.putInt("index",index).commit();
    }

    public int getIndex(){
        return sp.getInt("index",-1);
    }

    public void setFood(String food){
        editor.putString("food",food).commit();
    }

    public String getFood(){
        return sp.getString("food","");
    }

    public void setDescription(String description){
        editor.putString("description",description).commit();
    }

    public String getDescription(){
        return sp.getString("description","");
    }

    public void setPrice(int price){
        editor.putInt("price",price).commit();
    }

    public int getPrice(){
        return sp.getInt("price",0);
    }

    public void setUnitPrice(int unitPrice){editor.putInt("unitPrice",unitPrice).commit();}

    public int getUnitPrice(){return sp.getInt("unitPrice",0);}

    public void setPicture(int picture){
        editor.putInt("picture",picture).commit();
    }

    public int getPicture(){
        return sp.getInt("picture",0);
    }

    public void setAddPrice(int addPrice){
        editor.putInt("addPrice",addPrice).commit();
    }

    public int getAddPrice(){
        return sp.getInt("addPrice",0);
    }

    public void setSidePrice(int sidePrice){
        editor.putInt("sidePrice",sidePrice).commit();
    }

    public int getSidePrice(){
        return sp.getInt("sidePrice",0);
    }

    public void setDrinkPrice(int drinkPrice){
        editor.putInt("drinkPrice",drinkPrice).commit();
    }

    public int getDrinkPrice(){
        return sp.getInt("drinkPrice",0);
    }

    public String getToastTitle(){
        return mealList.get(0);
    }

    public String getBreadTitle(){
        return  mealList.get(1);
    }

    public String getCheeseTitle(){
        return mealList.get(2);
    }

    public String getAddTitle(){
        return mealList.get(3);
    }

    public String getVeggieTitle(){
        return mealList.get(4);
    }

    public String getPickleTitle(){
        return mealList.get(5);
    }

    public String getSauceTitle(){
        return  mealList.get(6);
    }

    public String getSideTitle(){
        return  mealList.get(7);
    }

    public String getDrinkTitle(){
        return  mealList.get(8);
    }

    public void setToast(String toast){
        editor.putString("toast",toast).commit();
    }

    public String getToast(){
        return sp.getString("toast","");
    }

    public void setBread(String bread){
        editor.putString("bread",bread).commit();
    }

    public String getBread(){
        return sp.getString("bread","");
    }

    public void setCheese(String cheese){
        editor.putString("cheese",cheese).commit();
    }

    public String getCheese(){
        return sp.getString("cheese","");
    }

    public void setAdd(String add){
        addSet.add(add);
        editor.putStringSet("addSet",addSet).commit();
    }

    public void setAddArray(String[] addArray){
        editor.putStringSet("addSet",new HashSet<>(Arrays.asList(addArray))).commit();
    }

    public void removeAdd(String add){
        addSet.remove(add);
        editor.putStringSet("addSet",addSet).commit();
    }

    public void clearAdd(){
        editor.putStringSet("addSet",new HashSet<>()).commit();
    }

    public String[] getAdd(){
        Set<String> addSet1 = sp.getStringSet("addSet",new HashSet<>());
        return addSet1.toArray(new String[0]);
    }

    public void setVeggie(String[] veggieArray){
        editor.putStringSet("veggieSet",new HashSet<>(Arrays.asList(veggieArray))).commit();
    }

    public void removeVeggie(){//可以用set new String[]{}取代
        editor.putStringSet("veggieSet",new HashSet<>()).commit();
    }

    public String[] getVeggie(){
        Set<String> veggieSet = sp.getStringSet("veggieSet",new HashSet<>());
        return veggieSet.toArray(new String[0]);
    }

    public void setPickle(String[] pickleArray){
        editor.putStringSet("pickleSet",new HashSet<>(Arrays.asList(pickleArray))).commit();
    }

    public void removePickle(){//可以用set new String[]{}取代
        editor.putStringSet("pickleSet",new HashSet<>()).commit();
    }

    public String[] getPickle(){
        Set<String> pickleSet = sp.getStringSet("pickleSet",new HashSet<>());
        return pickleSet.toArray(new String[0]);
    }

    public void setSauce(String[] sauceArray){
        editor.putStringSet("sauceSet",new HashSet<>(Arrays.asList(sauceArray))).commit();
    }

    public void removeSauce(){//可以用set new String[]{}取代
        editor.putStringSet("sauceSet",new HashSet<>()).commit();
    }

    public String[] getSauce(){
        Set<String> sauceSet = sp.getStringSet("sauceSet",new HashSet<>());
        return  sauceSet.toArray(new String[0]);
    }

    public void setSide(String side){
        editor.putString("side",side).commit();
    }

    public String getSide(){
        return sp.getString("side","");
    }

    public void setDrink(String drink){
        editor.putString("drink",drink).commit();
    }

    public String getDrink(){
        return sp.getString("drink","");
    }
    //若有要存到資料庫再用，況且也不是放在這裡
    public void setUtensil(boolean utensil){
        editor.putBoolean("utensil",utensil).commit();
    }

    public boolean getUtensil(){
        return sp.getBoolean("utensil",false);
    }

    public void setNote(String note){
        editor.putString("note",note).commit();
    }

    public String getNote(){
        return sp.getString("note","");
    }
    ////////
    public void setNumber(int number){editor.putInt("number",number).commit();}

    public int getNumber(){ return sp.getInt("number",0);}

    public void clear() {
        editor.clear().commit();
    }
}
