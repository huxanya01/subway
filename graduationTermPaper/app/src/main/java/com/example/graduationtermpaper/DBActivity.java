package com.example.graduationtermpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DBActivity extends AppCompatActivity {

    private Context context;
    private DB db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);

        context = this;
        setTitle("subway");

        listView = (ListView) findViewById(R.id.listView_db);

        db = new DB(this);
        db.openDB();
        setAdapter();

        //listView按下
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(context)
                        .setTitle("刪除資料")
                        .setMessage("您想要刪除資料嗎?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete(id);
                                setAdapter();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    private void setAdapter(){
        //listView設定資料
        Cursor cursor = db.readAll();
        cursor.moveToFirst();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.dbitem_layout,
                cursor,new String[]{"_id", "phone", "totalPrice", "utensil", "note",
                "_index", "food", "number", "price", "unitPrice",
                "toast", "bread", "cheese",
                "_add", "addPrice", "veggie", "pickle", "sauce",
                "side", "sidePrice", "drink", "drinkPrice"},new int[]{
                R.id.textView_dbId,R.id.textView_dbPhone,R.id.textView_dbTotalPrice,R.id.textView_dbUtensil,R.id.textView_dbNote,
                R.id.textView_dbIndex,R.id.textView_dbFood,R.id.textView_dbNumber,R.id.textView_dbPrice,R.id.textView_dbUnitPrice,
                R.id.textView_dbToast,R.id.textView_dbBread,R.id.textView_dbCheese,
                R.id.textView_dbAdd,R.id.textView_dbAddPrice,R.id.textView_dbVeggie,R.id.textView_dbPickle,R.id.textView_dbSauce,
                R.id.textView_dbSide,R.id.textView_dbSidePrice,R.id.textView_dbDrink,R.id.textView_dbDrinkPrice
        },SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);
    }
}