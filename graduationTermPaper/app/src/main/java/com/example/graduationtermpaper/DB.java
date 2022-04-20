package com.example.graduationtermpaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DB {
    private static final String DATABASE_NAME = "subway.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "subway";
    private static final String NOTES_TABLE_CREATE =
            "CREATE TABLE subway(_id INTEGER PRIMARY KEY, " +
                    "phone TEXT, totalPrice INTEGER, utensil TEXT, note TEXT," +
                    "_index INTEGER, food TEXT, number INTEGER, price INTEGER, unitPrice INTEGER," +
                    "toast TEXT, bread TEXT, cheese TEXT," +
                    "_add TEXT, addPrice INTEGER, veggie TEXT, pickle TEXT, sauce TEXT," +
                    "side TEXT, sidePrice INTEGER, drink TEXT, drinkPrice INTEGER);";
    private static Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(NOTES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public DB(Context ctx){
        mCtx = ctx;
    }

    public void openDB(){
        dbHelper = new DatabaseHelper(mCtx);
        db = dbHelper.getWritableDatabase();
    }

    public Cursor readAll(){
        return db.rawQuery("SELECT * FROM "+DATABASE_TABLE,null);
    }

    public boolean insert(String phone, int totalPrice, String utensil, String note,
    int _index, String food, int number, int price, int unitPrice,
                          String toast, String bread, String cheese,
                          String _add, int addPrice, String veggie, String pickle, String sauce,
                          String side, int sidePrice, String drink, int drinkPrice) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("phone",phone);
        cv.put("totalPrice",totalPrice);
        cv.put("utensil",utensil);
        cv.put("note",note);
        cv.put("_index",_index);
        cv.put("food",food);
        cv.put("number",number);
        cv.put("price",price);
        cv.put("unitPrice",unitPrice);
        cv.put("toast",toast);
        cv.put("bread",bread);
        cv.put("cheese",cheese);
        cv.put("_add",_add);
        cv.put("addPrice",addPrice);
        cv.put("veggie",veggie);
        cv.put("pickle",pickle);
        cv.put("sauce",sauce);
        cv.put("side",side);
        cv.put("sidePrice",sidePrice);
        cv.put("drink",drink);
        cv.put("drinkPrice",drinkPrice);
        return db.insertOrThrow(DATABASE_TABLE,null,cv)>0;
    }

    public int delete(long id){
        return db.delete(DATABASE_TABLE,"_id = "+id,null);
    }

}
