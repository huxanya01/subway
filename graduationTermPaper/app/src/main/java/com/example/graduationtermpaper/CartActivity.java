package com.example.graduationtermpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CartActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private Button buttonCheckOut;
    private CheckBox checkBoxUtensil;
    private EditText editTextNote;
    private Intent intent;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        context = this;
        TelNum telNum = new TelNum(context);
        setTitle("Subway(電話號碼:"+telNum.getTelNum());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        buttonCheckOut = (Button) findViewById(R.id.button_checkout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cart);

        checkBoxUtensil = (CheckBox) findViewById(R.id.checkBox_utensil);
        editTextNote = (EditText) findViewById(R.id.editText_note);

        LinearLayoutManager myLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(myLayoutManager);

        CartAdapter adapter = new CartAdapter(context,buttonCheckOut);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,LoginActivity.class);

                db = new DB(context);
                db.openDB();
                GlobalCount globalCount = new GlobalCount(context);

                //算總金額
                int total = 0;
                for (int i=0;i<globalCount.getCount();i++){
                    PrefData prefData = new PrefData(context,i);
                    if (prefData.getShowFlag()!=false){
                        total += prefData.getPrice();
                    }
                }
                //是否需要餐具
                String utensil = "無需餐具";
                if(checkBoxUtensil.isChecked()){
                    utensil = "需要餐具";
                }
                //備註
                String note = editTextNote.getText().toString();
                //準備
                for (int i=0;i<globalCount.getCount();i++){
                    PrefData prefData = new PrefData(context,i);
                    if(prefData.getShowFlag()){
                        //加料區
                        String[] addArray = prefData.getAdd();
                        String add = "";
                        if (addArray.length!=0){
                            for (int j=0; j<addArray.length;j++){
                                if (j != addArray.length-1){
                                    add += addArray[j] + ",";
                                }else {
                                    add += addArray[j];
                                }
                            }
                        }else {
                            add = "無需加料";
                        }
                        //蔬菜區
                        String[] veggieArray = prefData.getVeggie();
                        String veggie = "";
                        if (veggieArray.length!=0){
                            for (int j=0;j<veggieArray.length;j++){
                                if (j != veggieArray.length-1){
                                    veggie += veggieArray[j] + ",";
                                }else {
                                    veggie += veggieArray[j];
                                }
                            }
                        }else {
                            veggie = "無需蔬菜";
                        }
                        //醃製物
                        String[] pickleArray = prefData.getPickle();
                        String pickle = "";
                        if (pickleArray.length!=0){
                            for (int j=0;j<pickleArray.length;j++){
                                if (j != pickleArray.length-1){
                                    pickle += pickleArray[j] + ",";
                                }else {
                                    pickle += pickleArray[j];
                                }
                            }
                        }else {
                            pickle = "無需醃製物";
                        }
                        //醬汁
                        String[] sauceArray = prefData.getSauce();
                        String sauce = "";
                        if (sauceArray.length!=0){
                            for (int j=0;j<sauceArray.length;j++){
                                if (j != sauceArray.length-1){
                                    sauce += sauceArray[j] + ",";
                                }else {
                                    sauce += sauceArray[j];
                                }
                            }
                        }else {
                            sauce = "無需醬汁";
                        }
                        //寫入資料庫
                        db.insert(telNum.getTelNum(),total,utensil,note,
                                prefData.getIndex(),prefData.getFood(),prefData.getNumber(),prefData.getPrice(),prefData.getUnitPrice(),
                                prefData.getToast(),prefData.getBread(),prefData.getCheese(),
                                add,prefData.getAddPrice(),veggie,pickle,sauce,
                                prefData.getSide(),prefData.getSidePrice(),prefData.getDrink(),prefData.getDrinkPrice()
                                );
                    }
                }

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CartAdapter adapter = new CartAdapter(context,buttonCheckOut);
        recyclerView.setAdapter(adapter);
    }
}