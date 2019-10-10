package com.mobileprogramming.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

    // 회원 가입
    public void registerUser(User user) throws InvalidUserIDException, InvalidUserPasswordException, InvalidUserNameException, InvalidUserPhoneException, InvalidUserAddressException, DuplicateUserIDException {
        if(users.containsKey(user.getId())) throw new DuplicateUserIDException();
        if(isValidUser(user)) {
            users.put(user.getId(), user);
            saveUserDB(user);
        }
    }

    // 회원 추가
    public void addUser(User user) throws InvalidUserIDException, InvalidUserPasswordException, InvalidUserNameException, InvalidUserPhoneException, InvalidUserAddressException, DuplicateUserIDException {
        if(users.containsKey(user.getId())) throw new DuplicateUserIDException();
        if(isValidUser(user)) {
            users.put(user.getId(), user);
        }
    }

    private boolean isValidUser(User user) throws InvalidUserIDException, InvalidUserPasswordException, InvalidUserNameException, InvalidUserPhoneException, InvalidUserAddressException, DuplicateUserIDException{
        return isValidID(user.getId()) && isValidPassword(user.getPassword()) && isValidName(user.getName()) && isValidPhone(user.getPhone()) && isValidAddress(user.getAddress());
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

    // 이름 유효성 검사
    private boolean isValidName(String name) throws InvalidUserNameException{
        if(name.length() == 0) throw new InvalidUserNameException();
        for(int i=0; i<name.length(); i++) if(name.charAt(i)==' ' || name.charAt(i)=='\n') throw new InvalidUserNameException();
        return true;
    }

    // 전화번호 유효성 검사
    private boolean isValidPhone(String phone) throws InvalidUserPhoneException{
        if(phone.length() == 0) throw new InvalidUserPhoneException();
        for(int i=0; i<phone.length(); i++) if(phone.charAt(i)==' ' || phone.charAt(i)=='\n') throw new InvalidUserPhoneException();
        return true;
    }

    // 주소 유효성 검사
    private boolean isValidAddress(String address) throws InvalidUserAddressException{
        if(address.length() == 0) throw new InvalidUserAddressException();
        for(int i=0; i<address.length(); i++) if(address.charAt(i)==' ' || address.charAt(i)=='\n') throw new InvalidUserAddressException();
        return true;
    }

    // 회원정보 불러오기
    public void loadUserDB() throws InvalidUserDBException {
        users.clear();
        try {
            String userdata;
            FileInputStream fileInputStream = context.openFileInput(DB_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while((userdata = bufferedReader.readLine())!=null){
                String[] token = userdata.split(" ");
                addUser(new User(token[0], token[1], token[2], token[3], token[4]));
            }
            bufferedReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            saveAllUserDB();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InvalidUserException | DuplicateUserIDException | ArrayIndexOutOfBoundsException e) {
            clearUserDB();
            saveAllUserDB();
            throw new InvalidUserDBException();
        }
    }

    // 회원정보 저장
    public void saveUserDB(User user){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(DB_NAME, Context.MODE_APPEND);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(user.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 회원정보 전체 저장
    public void saveAllUserDB(){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(DB_NAME, Context.MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write("");
            for(HashMap.Entry<String, User> entry : users.entrySet()){
                User user = entry.getValue();
                bufferedWriter.write(user.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileOutputStream.close();
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
