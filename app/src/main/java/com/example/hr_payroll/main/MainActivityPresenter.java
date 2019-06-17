package com.example.hr_payroll.main;

public class MainActivityPresenter implements MainActivityMVP.Presenter {
    private MainActivityMVP.View view;
    public MainActivityPresenter(MainActivityMVP.View view){
        this.view = view;
        this.view.initUI();
    }
}
