package com.mobileprogramming.assignment1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;

public class FirstActivity extends AppCompatActivity {
    UserManager userManager;

    Button btn_login, btn_register;
    EditText edit_id, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        UserManager.setContext(getApplicationContext());
        userManager = UserManager.getInstance();
        try {
            userManager.loadUserDB();
        } catch (InvalidUserDBException e) {
            e.printStackTrace();
        }

        if(userManager.getUsers().isEmpty()){
            try{
                userManager.registerUser(new User("test", "aA1!", "test", "test", "test"));
                Toast.makeText(getApplicationContext(), "Add User", Toast.LENGTH_SHORT).show();
            } catch (InvalidUserException | DuplicateUserIDException e) {
                e.printStackTrace();
            }
        }

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        edit_id = findViewById(R.id.edit_id);
        edit_password = findViewById(R.id.edit_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edit_id.getText().toString();
                String password = edit_password.getText().toString();

                User user = userManager.logIn(id, password);

                if (user != null) {
                    Toast.makeText(getApplicationContext(), "안녕하세요 " + user.getId() + "님", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ThirdAcitivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
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
