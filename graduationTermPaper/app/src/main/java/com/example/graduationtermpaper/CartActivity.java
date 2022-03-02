package com.example.graduationtermpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class CartActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private Button buttonCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        context = this;
        setTitle("Subway");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        buttonCheckOut = (Button) findViewById(R.id.button_checkout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cart);

        LinearLayoutManager myLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(myLayoutManager);

        CartAdapter adapter = new CartAdapter(context,buttonCheckOut);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
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