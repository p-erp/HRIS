package com.example.hr_payroll.company;

public class CompanyMainActivity_Presenter implements CompanyMainActivityMVP.Presenter {
    private CompanyMainActivityMVP.View view;
    public CompanyMainActivity_Presenter(CompanyMainActivityMVP.View view){
        this.view = view;
        this.view.initUI();
        this.view.initFragment();
    }

}
