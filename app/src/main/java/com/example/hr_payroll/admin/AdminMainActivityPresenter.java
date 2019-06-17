package com.example.hr_payroll.admin;

public class AdminMainActivityPresenter implements AdminMainActivityMVP.Presenter {
    private AdminMainActivityMVP.View view;
    public AdminMainActivityPresenter(AdminMainActivityMVP.View view){
        this.view = view;
        this.view.initUI();
        this.view.initFragment();
    }
}
