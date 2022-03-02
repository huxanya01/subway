package com.example.graduationtermpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SecondActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;

    private String[] secondVeggie,secondPickle,secondSauce;
    private List<String> secondVeggieList,secondPickleList,secondSauceList;
    private String[] secondTitleArray;
    private List<String> secondTitleList;
    private List<List<String>> secondOptionList;
    private Intent intent;

    private String secondTitle;
    private String[] preSecondData;
    private int index;

    private TextView textViewSecondTitle;
    private ImageButton imageButton;
    private TempData dataVeggie,dataPickle,dataSauce;
    private List<TempData> tempDataList;

    private Button buttonSave;
    private SecondAdapter adapter;
    private final int SecondCode=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        context = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Subway");

        intent = getIntent();
        secondTitle = intent.getStringExtra("title");
        index = intent.getIntExtra("index",0);
        preSecondData = intent.getStringArrayExtra("message");

        textViewSecondTitle = (TextView)findViewById(R.id.textView_secondTitle);
        textViewSecondTitle.setText(secondTitle);

//        Toast.makeText(context, "index="+index, Toast.LENGTH_SHORT).show();

        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setEnabled(false);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_second);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //選項list

        secondVeggie = getResources().getStringArray(R.array.secondVeggie);
        secondVeggieList = new ArrayList<>();
        secondVeggieList = Arrays.asList(secondVeggie);

        secondPickle = getResources().getStringArray(R.array.secondPickle);
        secondPickleList = new ArrayList<>();
        secondPickleList = Arrays.asList(secondPickle);

        secondSauce = getResources().getStringArray(R.array.secondSauce);
        secondSauceList = new ArrayList<>();
        secondSauceList = Arrays.asList(secondSauce);

        secondOptionList = new ArrayList<>();

        secondOptionList.add(secondVeggieList);
        secondOptionList.add(secondPickleList);
        secondOptionList.add(secondSauceList);

//        if(index>=17&&index<=20){ //沙拉有
            //選後title
            secondTitleArray = getResources().getStringArray(R.array.secondTitle);
            secondTitleList = new ArrayList<>();
            secondTitleList = Arrays.asList(secondTitleArray);

            dataVeggie = new TempData();
            dataPickle = new TempData();
            dataSauce = new TempData();
            tempDataList = new ArrayList<>();
            tempDataList.add(dataVeggie);
            tempDataList.add(dataPickle);
            tempDataList.add(dataSauce);

//        }

        for (int i=0;i<secondTitleList.size();i++){
            if(secondTitle.equals(secondTitleList.get(i))){
                if(preSecondData !=null){
                    tempDataList.get(i).setArrayToSet(preSecondData);
                }
                adapter = new SecondAdapter(context,secondOptionList.get(i),tempDataList.get(i),buttonSave,secondTitle,recyclerView);
            }
        }

        recyclerView.setAdapter(adapter);

        imageButton = (ImageButton)findViewById(R.id.imageButton_secondShow);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility()==View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
                    imageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    imageButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            Set<String> set;

            @Override
            public void onClick(View v) {
                for (int i=0;i<secondTitleList.size();i++){
                    if(secondTitle.equals(secondTitleList.get(i))) {
                        set = tempDataList.get(i).getData();
                        intent.putExtra("title",secondTitleList.get(i));
                    }
                }

                String[] objArray = set.toArray(new String[0]);
                //array=(String[])objArray;

                intent.putExtra("item",objArray);
                setResult(SecondCode,intent);

                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            for (int i=0;i<secondTitleList.size();i++) {
                if (secondTitle.equals(secondTitleList.get(i))) {
                    intent.putExtra("title", secondTitleList.get(i));
                }
            }
            if(preSecondData ==null){
                intent.putExtra("item",new String[]{});
            }else{
                intent.putExtra("item", preSecondData);
            }

            setResult(SecondCode,intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            for (int i=0;i<secondTitleList.size();i++) {
                if (secondTitle.equals(secondTitleList.get(i))) {
                    intent.putExtra("title", secondTitleList.get(i));
                }
            }
            if(preSecondData ==null){
                intent.putExtra("item",new String[]{});
            }else{
                intent.putExtra("item", preSecondData);
            }

            setResult(SecondCode,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}