package com.example.hr_payroll.main;

public interface  MainActivityMVP {
    interface View{
        void intentActivty(Class className);
        void initUI();
    }
    interface Presenter{

    }

}
