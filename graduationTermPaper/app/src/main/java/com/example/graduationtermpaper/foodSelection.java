package com.example.graduationtermpaper;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class foodSelection extends AppCompatActivity {

    private Context context;
    private TextView textViewSectionTitle,textViewSectionDescription,textViewNumber;
    private ImageView imageViewSection;
    private ImageButton imageButtonSub,imageButtonAdd;
    private Button buttonAddToCart;
    private RecyclerView recyclerView;

    private String[] toast,bread,cheese,add,veggie,pickle,sauce,side,drink;
    private String[] meal,salad,light;
    private List<String> toastList,breadList,cheeseList,addList,veggieList,pickleList,sauceList,sideList,drinkList;
    private List<String> mealList,saladList,lightList,titleList;
    private List<List<String>> optionList,toCheckList;
    private ActivityResultLauncher<Intent> getResult;
    private SectionAdapter adapter;
    private final int SecondCode=2;
    private int number=1;
    private PrefData prefData;
    private OldPrefData oldPrefData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);

        context = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Subway");
        Intent intent = getIntent();

        GlobalCount globalCount = new GlobalCount(context);

        if (intent.getIntExtra("itemNo",-1)==-1){
            prefData = new PrefData(context,globalCount.getCount());
        }else{
            prefData = new PrefData(context,intent.getIntExtra("itemNo",0));
            oldPrefData = new OldPrefData(prefData);
        }

        prefData.setIndex( intent.getIntExtra("index",0) );
        if (!prefData.getShowFlag()){
            prefData.setUnitPrice( intent.getIntExtra("price",0) );
            prefData.setPrice(prefData.getUnitPrice());
            prefData.setNumber(number);
        }
        prefData.setFood( intent.getStringExtra("food") );
        prefData.setDescription( intent.getStringExtra("description") );
        prefData.setPicture( intent.getIntExtra("picture",0) );

//        Toast.makeText(context,"index="+prefData.getIndex(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, "price="+ prefData.getPrice(), Toast.LENGTH_SHORT).show();

        textViewSectionTitle = (TextView)findViewById(R.id.textView_sectionTitle);
        textViewSectionDescription = (TextView)findViewById(R.id.textView_sectionDescription);
        imageViewSection = (ImageView)findViewById(R.id.imageView_section);

        textViewSectionTitle.setText(prefData.getFood());
        textViewSectionDescription.setText(prefData.getDescription());
        imageViewSection.setImageResource(prefData.getPicture());
        if (prefData.getPicture()==0){
            imageViewSection.setVisibility(View.GONE);
        }

        buttonAddToCart = (Button) findViewById(R.id.button_addToCart);
        buttonAddToCart.setEnabled(false);
        if (prefData.getIndex()>=23&&prefData.getIndex()<=30){
            buttonAddToCart.setEnabled(true);
        }
        if (prefData.getShowFlag()){
            buttonAddToCart.setEnabled(true);
            buttonAddToCart.setText("更新購物車");
        }
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefData.getShowFlag()){
                    prefData.setShowFlag(true);
                    globalCount.addCount();
                }
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_section);
        recyclerView.setItemViewCacheSize(10);//設定不更新的項目範圍

        //選項list
        toast = getResources().getStringArray(R.array.toast);
        toastList = Arrays.asList(toast);

        bread = getResources().getStringArray(R.array.bread);
        breadList = Arrays.asList(bread);

        cheese = getResources().getStringArray(R.array.cheese);
        cheeseList = Arrays.asList(cheese);

        add = getResources().getStringArray(R.array.add);
        addList = Arrays.asList(add);

        veggie = getResources().getStringArray(R.array.veggie);
        veggieList = Arrays.asList(veggie);

        pickle = getResources().getStringArray(R.array.pickle);
        pickleList = Arrays.asList(pickle);

        sauce = getResources().getStringArray(R.array.sauce);
        sauceList = Arrays.asList(sauce);

        side = getResources().getStringArray(R.array.side);
        sideList = Arrays.asList(side);

        drink = getResources().getStringArray(R.array.drink);
        drinkList = Arrays.asList(drink);

        optionList = new ArrayList<>();
        toCheckList = new ArrayList<>();

        //Section title list
        meal = getResources().getStringArray(R.array.meal);
        mealList = new ArrayList<>();
        mealList = Arrays.asList(meal);

        salad = getResources().getStringArray(R.array.salad);
        saladList = new ArrayList<>();
        saladList = Arrays.asList(salad);

        light = getResources().getStringArray(R.array.light);
        lightList = new ArrayList<>();
        lightList = Arrays.asList(light);

        //項目檢查，且分類
        toCheckList.add(toastList);
        toCheckList.add(breadList);
        toCheckList.add(cheeseList);
        toCheckList.add(addList);
        toCheckList.add(veggieList);
        toCheckList.add(pickleList);
        toCheckList.add(sauceList);
        toCheckList.add(sideList);
        toCheckList.add(drinkList);

        //將傳回的index寫條件在分類的section
        if(prefData.getIndex()>=0&&prefData.getIndex()<=16){
            optionList.add(toastList);
            optionList.add(breadList);
            optionList.add(cheeseList);
            optionList.add(addList);
            optionList.add(veggieList);
            optionList.add(pickleList);
            optionList.add(sauceList);
            optionList.add(sideList);
            optionList.add(drinkList);

            titleList = mealList;
        }

        if(prefData.getIndex()>=17&&prefData.getIndex()<=20){ //是沙拉
            optionList.add(cheeseList);
            optionList.add(addList);
            optionList.add(veggieList);
            optionList.add(pickleList);
            optionList.add(sauceList);
            optionList.add(sideList);
            optionList.add(drinkList);

            titleList = saladList;
        }
        if(prefData.getIndex()>=21&&prefData.getIndex()<=22){//是輕食
            optionList.add(sideList);
            optionList.add(drinkList);

            titleList = lightList;
        }

        getResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int code = result.getResultCode();
                if(code==SecondCode) {
                    Intent intent = result.getData();
                    String[] message = intent.getStringArrayExtra("item");
                    String title = intent.getStringExtra("title");
                    adapter.setButton(title, message);
                }
            }
        });

        if(!(prefData.getIndex()>=23&&prefData.getIndex()<=44)){ //除了飲料和產品標示不用recyclerview

            LinearLayoutManager myLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(myLayoutManager);

            adapter = new SectionAdapter(context,titleList,optionList,toCheckList,buttonAddToCart,getResult,prefData);
            recyclerView.setAdapter(adapter);
        }

        imageButtonSub = (ImageButton) findViewById(R.id.imageButton_sub);
        imageButtonAdd = (ImageButton) findViewById(R.id.imageButton_add);
        textViewNumber = (TextView) findViewById(R.id.textView_number);
        if (prefData.getShowFlag()){
            textViewNumber.setText(Integer.toString(prefData.getNumber()));
            number = prefData.getNumber();
        }

        imageButtonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number>=2&&number<=10){
                    number--;
                    textViewNumber.setText(Integer.toString(number));
                    prefData.setNumber(number);
                    int calculatePrice;
                    calculatePrice = (prefData.getUnitPrice() + prefData.getAddPrice()+prefData.getSidePrice()+prefData.getDrinkPrice()) * number;
                    prefData.setPrice(calculatePrice);
//                    Toast.makeText(context, "getPrice:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number>=1&&number<=9){
                    number++;
                    textViewNumber.setText(String.valueOf(number));
                    prefData.setNumber(number);
                    int calculatePrice =(prefData.getUnitPrice() + prefData.getAddPrice()+prefData.getSidePrice()+prefData.getDrinkPrice()) * number;
                    prefData.setPrice(calculatePrice);
//                    Toast.makeText(context, "getPrice:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if (!prefData.getShowFlag()){
                prefData.clear();
            }else{
                oldPrefData.restorePrefData();
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if (!prefData.getShowFlag()) {
                prefData.clear();
            } else {
                oldPrefData.restorePrefData();
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}