package com.example.hr_payroll.signup;

import android.content.Context;

import com.example.hr_payroll.model.User;

public class SignUpPresenter implements SignUpMVP.Presenter {
    private  SignUpMVP.View view;
    private SignUpInterActor signUpInterActor;
    public SignUpPresenter(SignUpMVP.View view){
        this.view = view;
        this.view.initUI();
        signUpInterActor = new SignUpInterActor((Context) view);
    }
    @Override
    public void onSignUp(User user) {
        signUpInterActor.SignUp(this,user);


    }

    @Override
    public void onSignUpSuccess(String title, String message) {
        view.showSuccessMessage(title,message);
    }

    @Override
    public void onSignUpFailed(String title, String message) {
        view.showErrorMessage(title,message);

    }
}
