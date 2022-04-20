package com.example.graduationtermpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin,buttonDB;
    private EditText editTextPhone;
    private Context context;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        setTitle("Subway");

        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setEnabled(false);

        buttonDB = (Button) findViewById(R.id.button_db);

        editTextPhone = (EditText) findViewById(R.id.editText_phone);
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    buttonLogin.setEnabled(false);
                }else {
                    buttonLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,MainActivity.class);

                TelNum telNum = new TelNum(context);
                telNum.setTelNum(editTextPhone.getText().toString());

                startActivity(intent);
            }
        });

        buttonDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,DBActivity.class);
                startActivity(intent);
            }
        });
    }
}