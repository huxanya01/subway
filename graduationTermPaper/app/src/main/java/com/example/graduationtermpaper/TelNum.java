package com.example.graduationtermpaper;

import android.content.Context;
import android.content.SharedPreferences;

public class TelNum {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;


    public TelNum(Context context) {
        sp = context.getSharedPreferences("phone",0);
        editor = sp.edit();
    }

    public void setTelNum(String telNum){
        editor.putString("phone",telNum).commit();
    }

    public String getTelNum(){
        return sp.getString("phone","");
    }

}
