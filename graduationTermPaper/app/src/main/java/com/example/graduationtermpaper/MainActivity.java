package com.example.graduationtermpaper;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private String[] food, description, price;
    private TypedArray picture;
    private List<Map<String, Object>> myList;
    private ActivityResultLauncher<Intent> getResult;
    private String[] bigTitle;
    private int[] bigTitlePosition;
    private List<String> myBigTitle;
    private List<Integer> myBigTitlePosition;
    private GlobalCount globalCount;
    private Button buttonShowCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        TelNum telNum = new TelNum(context);
        setTitle("Subway(電話號碼:"+telNum.getTelNum());

        globalCount = new GlobalCount(context);
        globalCount.createOrRestoreCount();
//        Toast.makeText(context, "count:"+globalCount.getCount(), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        food = getResources().getStringArray(R.array.food);
        description = getResources().getStringArray(R.array.detailed_description);
        price = getResources().getStringArray(R.array.price);
        picture = getResources().obtainTypedArray(R.array.picture);

        myList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < food.length; i++) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("food", food[i]);
            data.put("description", description[i]);
            data.put("price", price[i]);
            data.put("picture", picture.getResourceId(i, 0));
            myList.add(data);
        }
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(myLayoutManager);

        MyAdapter adapter = new MyAdapter(MainActivity.this, myList);
        //MyAdapter2 adapter = new MyAdapter2(MainActivity.this, myList);
        recyclerView.setAdapter(adapter);

        bigTitle = getResources().getStringArray(R.array.bigTitle);
        bigTitlePosition = getResources().getIntArray(R.array.bigTitlePosition);

        myBigTitle = new ArrayList<String>();
        for(int i=0;i<bigTitle.length;i++){
            myBigTitle.add(bigTitle[i]);
        }
        myBigTitlePosition = new ArrayList<Integer>();
        for(int i=0;i<bigTitlePosition.length;i++){
            myBigTitlePosition.add(bigTitlePosition[i]);
        }

        recyclerView.addItemDecoration(new MyItemDecoration(myLayoutManager,myBigTitle,myBigTitlePosition));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (myLayoutManager.findFirstVisibleItemPosition() < 13) {
                    tabLayout.getTabAt(0).select();
                } else if (myLayoutManager.findFirstVisibleItemPosition() < 17) {
                    tabLayout.getTabAt(1).select();
                } else if (myLayoutManager.findFirstVisibleItemPosition() < 21) {
                    tabLayout.getTabAt(2).select();
                } else if (myLayoutManager.findFirstVisibleItemPosition() < 23) {
                    tabLayout.getTabAt(3).select();
                } else if (myLayoutManager.findFirstVisibleItemPosition() < 31) {
                    tabLayout.getTabAt(4).select();
                } else {
                    tabLayout.getTabAt(5).select();
                }

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0 && myLayoutManager.findFirstVisibleItemPosition() >= 13) {
                    myLayoutManager.scrollToPositionWithOffset(0, 0);
                } else if (tab.getPosition() == 1 && (myLayoutManager.findFirstVisibleItemPosition() < 13 ||
                        myLayoutManager.findFirstVisibleItemPosition() >= 17)) {
                    myLayoutManager.scrollToPositionWithOffset(13, 0);
                } else if (tab.getPosition() == 2 && (myLayoutManager.findFirstVisibleItemPosition() < 17 ||
                        myLayoutManager.findFirstVisibleItemPosition() >= 21)) {
                    myLayoutManager.scrollToPositionWithOffset(17, 0);
                } else if (tab.getPosition() == 3 && (myLayoutManager.findFirstVisibleItemPosition() < 21 ||
                        myLayoutManager.findFirstVisibleItemPosition() >= 23)) {
                    myLayoutManager.scrollToPositionWithOffset(21, 0);
                } else if (tab.getPosition() == 4 && (myLayoutManager.findFirstVisibleItemPosition() < 23 ||
                        myLayoutManager.findFirstVisibleItemPosition() >= 31)) {
                    myLayoutManager.scrollToPositionWithOffset(23, 0);
                } else if (tab.getPosition() == 5 && myLayoutManager.findFirstVisibleItemPosition() < 31) {
                    myLayoutManager.scrollToPositionWithOffset(31, 0);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonShowCart = (Button) findViewById(R.id.button_showCart);
        buttonShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CartActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Toast.makeText(context, "count:"+globalCount.getCount(), Toast.LENGTH_SHORT).show();
        if (globalCount.getCount()>=1){
            buttonShowCart.setVisibility(View.VISIBLE);
        }else{
            buttonShowCart.setVisibility(View.GONE);
        }
    }
}






