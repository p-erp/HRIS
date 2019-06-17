package com.example.hr_payroll.hr.fragment;

import android.content.Context;
public class HRMainActivity_Presenter implements HRMainActivityMVP.Presenter {
    private HRMainActivityMVP.View view;
    public HRMainActivity_Presenter(HRMainActivityMVP.View view){
        this.view = view;
        this.view.initUI();
        this.view.initFragment();

    }
}

