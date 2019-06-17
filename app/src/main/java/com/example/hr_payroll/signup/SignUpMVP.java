package com.example.hr_payroll.signup;

import com.example.hr_payroll.model.User;

public interface SignUpMVP {
    interface View{
        void initUI();
        void showErrorMessage(String title,String message);
        void showSuccessMessage(String title,String message);
    }
    interface Presenter{
        void onSignUp(User user);
        void onSignUpSuccess(String title,String message);
        void onSignUpFailed(String title,String message);

    }
}
