package com.mobileprogramming.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Stack;

public class ThirdAcitivity extends AppCompatActivity implements View.OnClickListener {
    Button[] btn_numpad;
    TextView screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        screen = findViewById(R.id.textview_screen);
        int[] btn_id = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_clear, R.id.btn_backspace, R.id.btn_add, R.id.btn_sub, R.id.btn_mul, R.id.btn_div, R.id.btn_run};
        btn_numpad = new Button[btn_id.length];
        for(int i=0; i<btn_numpad.length; i++) btn_numpad[i] = findViewById(btn_id[i]);
        for(int i=0; i<btn_numpad.length; i++) btn_numpad[i].setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = ((Button)v).getText().toString();
        if(str.equals(getResources().getString(R.string.numpadclear))){
            clearScreen();
        }
        else if(str.equals(getResources().getString(R.string.numpadbackspace))){
            deleteKey();
        }
        else if(str.equals(getResources().getString(R.string.numpadrun))){
            try {
                run();
            } catch (InvalidMathExpressionException e) {
                screen.setText("잘못된 수식");
            }
        }
        else{
            inputKey(str.charAt(0));
        }
    }

    private void inputKey(char ch){
        String s = screen.getText().toString();
        s+=ch;
        screen.setText(s);
    }

    private void deleteKey(){
        String s=screen.getText().toString();
        if(s.length()>0) s=s.substring(0, s.length()-1);
        screen.setText(s);
    }

    private void clearScreen(){
        screen.setText("");
    }

    private int getOpPriority(char op){
        if(op=='+' || op=='-') return 1;
        else return 0;
    }

    private double calcTwo(double a, double b, char op){
        if(op=='+') return a+b;
        else if(op=='-') return a-b;
        else if(op=='*') return a*b;
        else return a/b;
    }

    private void run() throws InvalidMathExpressionException{
        String s=screen.getText().toString();
        if(s.length()==0) throw new InvalidMathExpressionException();
        Stack<Double> numstack=new Stack<>();
        Stack<Character> opstack=new Stack<>();
        for(int i=0,j=0; i<=s.length(); i++){
            if(i==s.length() || s.charAt(i)=='+' || s.charAt(i)=='-' || s.charAt(i)=='*' || s.charAt(i)=='/'){
                String nums=s.substring(j,i);
                if(nums.length()==0) throw new InvalidMathExpressionException();
                int num=Integer.parseInt(nums);
                numstack.add((double)num);
                if(i!=s.length()){
                    int p=getOpPriority(s.charAt(i));
                    while(!opstack.isEmpty()){
                        int nowp=getOpPriority(opstack.peek());
                        if(nowp<=p) {
                            char op=opstack.pop();
                            double b=numstack.pop();
                            double a=numstack.pop();
                            numstack.add(calcTwo(a,b,op));
                        }
                        else break;
                    }
                    opstack.add(s.charAt(i));
                }
                j=i+1;
            }
            else if(!Character.isDigit(s.charAt(i))) throw new InvalidMathExpressionException();
        }
        while(!opstack.isEmpty()){
            char op=opstack.pop();
            int nowp=getOpPriority(op);
            double b=numstack.pop();
            double a=numstack.pop();
            numstack.add(calcTwo(a,b,op));
        }
        String res=String.valueOf(numstack.pop());
        screen.setText(res);
    }
}
