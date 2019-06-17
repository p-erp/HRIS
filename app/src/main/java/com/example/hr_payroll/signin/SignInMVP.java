package com.example.hr_payroll.signin;

import com.example.hr_payroll.model.User;

public interface  SignInMVP  {
    interface View{
        void initUI();
        void showErrorMessage(String title,String message);
        void loginSuccess(User user);
        void showMessage(String title,String message);
    }
    interface Presenter{
        void onLogin(String email,String password);
        void onLoginError(String title,String message);
        void onLoginSuccess(User user);

    }

}
