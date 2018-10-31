package com.example.osori.hopelinev1.Presenter;

import com.example.osori.hopelinev1.Model.User;
import com.example.osori.hopelinev1.View.IUserLoginView;

public class LoginPresenter implements ILoginPresenter {

    IUserLoginView loginView;

    public LoginPresenter(IUserLoginView iUserLoginView) {
        this.loginView = iUserLoginView;
    }

    @Override
    public void onLogin(String email, String password) {
        User user  = new User(email, password);
        boolean isLoginSuccess = user.isValidData();

        if(isLoginSuccess)
            loginView.onLoginResult("Login Success");
        else
            loginView.onLoginResult("Login Error");
    }
}
