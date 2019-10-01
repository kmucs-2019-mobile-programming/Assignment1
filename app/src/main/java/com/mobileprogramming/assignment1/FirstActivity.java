package com.mobileprogramming.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    UserManager userManager;

    Button btn_login, btn_register;
    EditText edit_id, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        userManager = UserManager.getInstance();
        //userManager.loadUserDB();

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        edit_id = findViewById(R.id.edit_id);
        edit_password = findViewById(R.id.edit_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edit_id.getText().toString();
                String password = edit_password.getText().toString();

                if(userManager.checkUser(id, password)){
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ThirdAcitivity.class);
                    startActivity(intent);
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
