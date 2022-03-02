package com.example.graduationtermpaper;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalCount {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final String dataXMLName = "test";
    Context context;

    public GlobalCount(Context context) {
        sp = context.getSharedPreferences("count",0);
        editor = sp.edit();
        this.context = context;
    }

    public void createOrRestoreCount(){
        clearAllItem();//先刪除舊資料
        editor.putInt("count",0).commit();
    }

    public void addCount(){
        int count = sp.getInt("count",0);
        count++;
        editor.putInt("count",count).commit();
    }

    public int getCount(){
        return sp.getInt("count",0);
    }

    public void clearItem(int itemNo){ //不需要此方法
        PrefData prefData = new PrefData(context,itemNo);
        prefData.clear();
    }

    public void clearAllItem(){
        int count = sp.getInt("count",0);
        for (int i=0;i<=count;i++){
            PrefData prefData = new PrefData(context,i);
            prefData.clear();
        }
    }
}
