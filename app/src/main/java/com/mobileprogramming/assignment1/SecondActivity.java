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
    UserManager userManager;

    EditText edit_id, edit_password, edit_name, edit_phone, edit_address;
    RadioButton radio_terms;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        userManager = UserManager.getInstance();

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
                    User user = new User(edit_id.getText().toString(), edit_password.getText().toString(), edit_name.getText().toString(), edit_phone.getText().toString(), edit_address.getText().toString());
                    try {
                        userManager.registerUser(user);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_user_register), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (InvalidUserIDException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_user_id_exception), Toast.LENGTH_SHORT).show();
                    } catch (InvalidUserPasswordException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_user_password_exception), Toast.LENGTH_SHORT).show();
                    } catch (DuplicateUserIDException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.duplicate_user_id_exception), Toast.LENGTH_SHORT).show();
                    } catch (InvalidUserAddressException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_user_address_exception), Toast.LENGTH_SHORT).show();
                    } catch (InvalidUserPhoneException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_user_phone_exception), Toast.LENGTH_SHORT).show();
                    } catch (InvalidUserNameException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_user_name_exception), Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_terms_agree_exception), Toast.LENGTH_SHORT).show();
            }
        });
    }
}