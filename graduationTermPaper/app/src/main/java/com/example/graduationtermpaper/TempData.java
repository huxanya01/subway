package com.example.graduationtermpaper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TempData {
    public Set<String> dataSet;

    public TempData(){
        dataSet = new HashSet<>();
    }

    public void setData(String text){
        dataSet.add(text);
    }

    public void removeData(String text){
        dataSet.remove(text);
    }

    public Set<String> getData(){
        return dataSet;
    }

    public String getData(int index){
        List<String> stringsList = new ArrayList<>(dataSet);
        return stringsList.get(index);
    }

    public int getSize(){
        return dataSet.size();
    }

    public void setArrayToSet(String[] array){
        for(int i=0;i<array.length;i++){
            dataSet.add(array[i]);
        }
    }


}
