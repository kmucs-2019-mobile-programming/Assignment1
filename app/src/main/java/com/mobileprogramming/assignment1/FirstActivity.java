package com.mobileprogramming.assignment1;

import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        loadUserDB();
    }

    // 가입회원 정보 불러오기
    void loadUserDB(){
        String line = null;
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data"); // 저장 경로
        if(!saveFile.exists()){
            saveFile.mkdir();
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader(saveFile+"/UserDB.db"));
            while((line=buf.readLine())!=null){
                String[] token = line.split(" ");
                users.add(new User(token[0], token[1]));
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
