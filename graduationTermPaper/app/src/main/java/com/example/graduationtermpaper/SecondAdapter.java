package com.example.graduationtermpaper;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater myInflater;
    private final List<String> mySecond;
    TempData tempData;
    private final Button buttonSave;
    private final String secondTitle;
    ViewHolder holder;
    boolean lockTag = false;
    List<String> tempList;
    private final RecyclerView recyclerView;

    public SecondAdapter(Context context, List<String> secondList, TempData tempData, Button buttonSave,String secondTitle,RecyclerView recyclerView) {
        myInflater = LayoutInflater.from(context);
        this.context = context;
        this.mySecond = secondList;
        this.tempData = tempData;
        this.buttonSave = buttonSave;
        this.secondTitle = secondTitle;
        this.recyclerView = recyclerView;
        tempList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = myInflater.inflate(R.layout.seconditem_layout,parent,false);
        SecondAdapter.ViewHolder viewHolder = new SecondAdapter.ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = mySecond.get(position);
        holder.checkBox.setText(option);
        holder.checkBox.setChecked(false);

        this.holder = holder;

        if(tempData.getSize()!=0&& lockTag !=true){
            for(int i=0;i<tempData.getSize();i++){
                if(tempData.getData(i).equals(holder.checkBox.getText())){
                    holder.checkBox.setChecked(true);
                    buttonSave.setEnabled(false);
                }
            }
        }


//        if (lockTag){
//            Toast.makeText(context, "position:"+position, Toast.LENGTH_SHORT).show();
//            if (!holder.checkBox.isClickable())
//            holder.checkBox.setEnabled(false);
//        }



    }


    @Override
    public int getItemCount() {
        return mySecond.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_id);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonSave.setEnabled(true);


                    String text = buttonView.getText().toString();

                    if (isChecked == true) {
//                        Toast.makeText(context, "checked=" + text, Toast.LENGTH_SHORT).show();
                        tempData.setData(text);
                        tempList.add(text);

                    } else {
//                        Toast.makeText(context, "unchecked=" + text, Toast.LENGTH_SHORT).show();
                        tempData.removeData(text);


                        if (tempData.getSize()==0){
                            buttonSave.setEnabled(false);
                        }
                    }

//                    if (secondTitle.equals("加醬汁With Sauce(請選擇您要的醬汁)")&&tempData.getSize()>=2){
//                        Toast.makeText(context, "heeeeeeeere", Toast.LENGTH_SHORT).show();
//                        for(int i=0;i<mySecond.size();i++){
//
//                            mySecond.get(i)
//
//
//                             lockTag = true;
//                            onBindViewHolder(holder,i);
//                            new Handler().post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    notifyItemRangeChanged(0,7);
//                                }
//                            });
//                            //notifyItemRangeChanged(0,7);
//
//                        }
//                        //notifyItemRangeChanged(0,7);
//                  }
//                    if (secondTitle.equals("加醬汁With Sauce(請選擇您要的醬汁)")&&tempData.getSize()<2){
//                        if (checkBox.isEnabled()==false){
//                            checkBox.setEnabled(true);
//                        }
//                    }


                }
            });


        }
    }
}
