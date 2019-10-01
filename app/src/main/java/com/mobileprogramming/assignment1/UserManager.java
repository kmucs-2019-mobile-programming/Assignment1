package com.mobileprogramming.assignment1;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class UserManager {
    private static final UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    HashMap<String, String> users;

    private UserManager() {
        users = new HashMap<>();
        addUser("test", "test");
    }

    // 유저 정보 검사
    public boolean checkUser(String id, String password){
        if(!users.containsKey(id)) return false;
        return users.get(id).equals(password);
    }

    // 신규유저 추가
    public boolean addUser(String id, String password){
        if(users.containsKey(id)) return false;
        users.put(id, password);
        return true;
    }

    // 가입회원정보 불러오기
    public void loadUserDB() {
        users.clear();
        String line = null;
        File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data"); // 저장 경로
        if(!saveFile.exists()){
            saveFile.mkdir();
        }
        try {
            BufferedReader buf = new BufferedReader(new FileReader(saveFile+"/UserDB.db"));
            while((line=buf.readLine())!=null){
                String[] token = line.split(" ");
                addUser(token[0], token[1]);
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
