package com.mobileprogramming.assignment1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    EditText edit_id, edit_password, edit_name, edit_phone, edit_address;
    RadioButton radio_terms;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edit_id = findViewById(R.id.edit_id);
        edit_password = findViewById(R.id.edit_password);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        edit_address = findViewById(R.id.edit_address);

        radio_terms = findViewById(R.id.radio_agree);

        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_terms.isChecked()){

                }
                else Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_terms_agree_exception), Toast.LENGTH_SHORT).show();
            }
        });
    }
}