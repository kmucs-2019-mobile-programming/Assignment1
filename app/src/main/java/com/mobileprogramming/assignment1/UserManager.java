package com.mobileprogramming.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class UserManager {
    private static final String DB_NAME = "UserDB.db";

    private static final int MIN_ID_LENGTH = 4;
    private static final int MAX_ID_LENGTH = 12;

    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private static final char[] SPECIAL_CHARACTER = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};

    private static final UserManager ourInstance = new UserManager();
    private static Context context = null;

    public static UserManager getInstance() {
        return ourInstance;
    }

    public static void setContext(Context c){
        context = c;
    }

    private HashMap<String, User> users;

    private UserManager() {
        users = new HashMap<>();
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    // 회원 정보 검사
    public User logIn(String id, String password){
        User user = users.get(id);
        if(user != null && user.getPassword().equals(password)) return user;
        return null;
    }

    // 회원 추가
    public void addUser(User user) throws InvalidUserIDException, InvalidUserPasswordException, DuplicateUserIDException {
        if(users.containsKey(user.getId())) throw new DuplicateUserIDException();
        if(isValidID(user.getId()) && isValidPassword(user.getPassword())) {
            users.put(user.getId(), user);
            saveUserDB(user);
        }
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

    // 회원정보 불러오기
    public void loadUserDB() throws InvalidUserDBException {
        users.clear();
        String line = null;
        try {
            BufferedReader buf = new BufferedReader(new FileReader(DB_NAME));
            while((line = buf.readLine())!=null){
                String[] token = line.split(" ");
                addUser(new User(token[0], token[1], token[2], token[3], token[4]));
            }
            buf.close();
        } catch (FileNotFoundException e) {
            saveAllUserDB();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidUserException | DuplicateUserIDException e) {
            clearUserDB();
            saveAllUserDB();
            throw new InvalidUserDBException();
        }
    }

    // 회원정보 저장
    public void saveUserDB(User user){
        String line = null;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(DB_NAME, true));
            buf.write(users.toString() + " ");
            buf.newLine();
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 회원정보 전체 저장
    public void saveAllUserDB(){
        String line = null;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(DB_NAME));
            for(HashMap.Entry<String, User> entry : users.entrySet()){
                User user = entry.getValue();
                buf.write(users.toString() + " ");
                buf.newLine();
            }
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 회원정보 초기화
    public void clearUserDB(){
        users.clear();
    }
}
