package com.example.osori.hopelinev1.Activities.UserActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osori.hopelinev1.R;

public class UserOptionsActivity extends AppCompatActivity {

    Button btnHome, btnPasswordError, btnEmailError;
    EditText edtName, edtEmail, edtBirthday, edtCurrentPassword, edtNewPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);
        
        btnHome = (Button)findViewById(R.id.btn_userOptionsReturn);
        btnEmailError = (Button)findViewById(R.id.btn_userEmailError);
        btnPasswordError = (Button)findViewById(R.id.btn_userPasswordError);

        edtCurrentPassword = (EditText)findViewById(R.id.edt_userCurrentPassword);
        edtNewPassword = (EditText)findViewById(R.id.edt_userNewPassword);
        edtEmail = (EditText)findViewById(R.id.edt_userEmail);

        edtEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setBackgroundColor(Color.WHITE);
                return false;
            }
        });

        edtCurrentPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setBackgroundColor(Color.WHITE);
                return false;
            }
        });
        edtNewPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setBackgroundColor(Color.WHITE);
                return false;
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("HERE", "onChanged");
                if(count >= 12){
                    edtNewPassword.setEnabled(false);
                    edtNewPassword.setClickable(false);
                }else {
                    edtNewPassword.setEnabled(true);
                    edtNewPassword.setClickable(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("HERE", "onAFterChanged");
                isValidEmail(s.toString());
            }
        });

        edtNewPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(UserOptionsActivity.this, "Typed", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        
        btnPasswordError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserOptionsActivity.this, "This is how the password error would look like", Toast.LENGTH_SHORT).show();
                edtCurrentPassword.setBackgroundColor(Color.RED);
                edtNewPassword.setBackgroundColor(Color.RED);
            }
        });
        
        btnEmailError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserOptionsActivity.this, "This is how the email error would look like", Toast.LENGTH_SHORT).show();
                edtEmail.setBackgroundColor(Color.RED);
            }
        });
        
        
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserOptionsActivity.this, "Going to home page", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean isValidEmail(CharSequence target) {
        if (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            edtEmail.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Not correct email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
