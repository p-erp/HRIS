package com.example.hr_payroll.main_navigation;



public class MainNavigationPresenter implements MainNavigationMVP.Presenter {
    MainNavigationMVP.View view;
    public MainNavigationPresenter(MainNavigationMVP.View view){
        this.view = view;
        this.view.initUI();
        this.view.initFragment();
    }
}
