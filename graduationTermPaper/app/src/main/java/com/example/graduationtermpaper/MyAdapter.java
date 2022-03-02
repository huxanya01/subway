package com.example.graduationtermpaper;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final List<Map<String, Object>> myList;
    private final LayoutInflater myInflater;
    private final Context context;

    public MyAdapter(MainActivity context, List<Map<String, Object>> myList) {
        this.myList = myList;
        myInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = myInflater.inflate(R.layout.item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> data = myList.get(position);
        String food = data.get("food").toString();
        String description = data.get("description").toString();
        String price = data.get("price").toString();
        int photo = (int)data.get("picture");//

        holder.myFood.setText(food);
        holder.myDescription.setText(description);
        holder.myPrice.setText(price);
        holder.myPicture.setImageResource(photo);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView myFood,myDescription, myPrice;
        ImageView myPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myFood = (TextView) itemView.findViewById(R.id.textView_name);
            myDescription = (TextView) itemView.findViewById(R.id.textView_detail);
            myPrice = (TextView) itemView.findViewById(R.id.textView_price);
            myPicture = (ImageView) itemView.findViewById(R.id.imageView_pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Map<String, Object> data = myList.get(index);
                    String name = data.get("food").toString();
                    int price = Integer.parseInt(data.get("price").toString());
                    String description = data.get("description").toString();
                    int picture = (int)data.get("picture");

                    Intent intent = new Intent(context,foodSelection.class);
                    intent.putExtra("food",name);
                    intent.putExtra("price",price);
                    intent.putExtra("description",description);
                    intent.putExtra("picture",picture);
                    intent.putExtra("index",index);
                    context.startActivity(intent);
                }
            });

        }
    }
}
