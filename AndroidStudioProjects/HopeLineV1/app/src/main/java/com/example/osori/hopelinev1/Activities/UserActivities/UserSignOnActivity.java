package com.example.osori.hopelinev1.Activities.UserActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osori.hopelinev1.Presenter.ILoginPresenter;
import com.example.osori.hopelinev1.Presenter.LoginPresenter;
import com.example.osori.hopelinev1.R;
import com.example.osori.hopelinev1.View.IUserLoginView;

public class UserSignOnActivity extends AppCompatActivity implements IUserLoginView {
    ILoginPresenter loginPresenter;
    Button btnLogin, btnSignup;
    EditText edt_email, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init View
        btnLogin = (Button)findViewById(R.id.btn_login);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_password = (EditText)findViewById(R.id.edt_password);


        //Init
        loginPresenter = new LoginPresenter(this);

        //Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.onLogin(edt_email.getText().toString(), edt_password.getText().toString());
            }
        });
    }

    @Override
    public void onLoginResult(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
