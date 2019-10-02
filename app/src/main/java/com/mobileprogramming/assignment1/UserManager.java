package com.mobileprogramming.assignment1;

import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class UserManager {
    private static final int MIN_ID_LENGTH = 4;
    private static final int MAX_ID_LENGTH = 12;

    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private static final char[] SPECIAL_CHARACTER = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};

    private static final UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        return ourInstance;
    }

    HashMap<String, String> users;

    private UserManager() {
        users = new HashMap<>();
        try {
            addUser("test", "test");
        } catch (InvalidUserException e) {
            e.printStackTrace();
        } catch (DuplicateUserIDException e) {
            e.printStackTrace();
        }
    }

    // 회원 정보 검사
    public boolean checkUser(String id, String password){
        String pw = users.get(id);
        if(pw != null) return pw.equals(password);
        return false;
    }

    // 신규회원 추가
    public boolean addUser(String id, String password) throws InvalidUserIDException, InvalidUserPasswordException, DuplicateUserIDException {
        if(users.containsKey(id)) throw new DuplicateUserIDException();
        if(isValidID(id) && isValidPassword(password)) {
            users.put(id, password);
            return true;
        }
        return false;
    }

    // 아이디 유효성 검사
    private boolean isValidID(String id) throws InvalidUserIDException {
        if(id.length() < MIN_ID_LENGTH || id.length() > MAX_ID_LENGTH) throw new InvalidUserIDException();
        for(int i = 0; i < id.length(); i++){
            if(!Character.isLetterOrDigit(id.charAt(i))) throw new InvalidUserIDException();
        }
        return true;
    }

    private boolean isSpecial(char ch){
        for(char c : SPECIAL_CHARACTER) if(c == ch) return true;
        return false;
    }

    // 비밀번호 유효성 검사
    private boolean isValidPassword(String password) throws InvalidUserPasswordException {
        if(password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) throw new InvalidUserPasswordException();
        boolean upper = false, lower = false, special = false, digit = false;
        for(int i = 0; i < password.length(); i++){
            if(!Character.isLetterOrDigit(password.charAt(i)) && !isSpecial(password.charAt(i))) throw new InvalidUserPasswordException();
            if(Character.isUpperCase(password.charAt(i))) upper = true;
            else if(Character.isLowerCase(password.charAt(i))) lower = true;
            else if(isSpecial(password.charAt(i))) special = true;
            else if(Character.isDigit(password.charAt(i))) digit = true;
        }
        if(upper && lower && special && digit) return true;
        throw new InvalidUserPasswordException();
    }

    // 가입회원정보 불러오기
    public void loadUserDB() throws InvalidUserDBException, DuplicateUserIDException {
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
        } catch (InvalidUserException e) {
            throw new InvalidUserDBException();
        } catch (DuplicateUserIDException e) {
            throw e;
        }
    }

    // 회원정보 초기화
    public void clearUserDB(){
        users.clear();
    }
}
