package com.example.hr_payroll.signin;

import android.content.Context;

import com.example.hr_payroll.model.User;

public class SignInPresenter implements SignInMVP.Presenter {
    private SignInMVP.View view;
    private SigninInterActor signinInterActor;
    public SignInPresenter(SignInMVP.View view){
        this.view = view;
        signinInterActor = new SigninInterActor((Context) view);
        this.view.initUI();

    }
    @Override
    public void onLogin(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        signinInterActor.logInProcess(this,user);
    }

    @Override
    public void onLoginError(String title, String message) {
        view.showErrorMessage(title,message);
    }

    @Override
    public void onLoginSuccess(User user) {
        view.loginSuccess(user);
    }
}
