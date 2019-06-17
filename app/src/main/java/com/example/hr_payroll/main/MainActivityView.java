package com.example.hr_payroll.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.AdminMainActivity;
import com.example.hr_payroll.company.CompanyMainActivity;
import com.example.hr_payroll.hr.fragment.HRMainActivity;
import com.example.hr_payroll.main_navigation.MainNavigation;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.signin.SignInView;
import com.example.hr_payroll.signup.SignUpView;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.TinyDB;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityView extends AppCompatActivity implements MainActivityMVP.View {
    private MainActivityPresenter presenter;
    @BindView(R.id.have_account)
    Button btn_have_account;
    @BindView(R.id.signup)
    TextView tv_signup;
    @OnClick(R.id.signup)
    void signUp(){
        intentActivty(SignUpView.class);
    }

    @OnClick(R.id.have_account)
    void haveAccountClick(){
        intentActivty(SignInView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter(this);
    }

    @Override
    public void intentActivty(Class className) {
        startActivity(new Intent(this,className));
    }
    private AppSharedPref appSharedPref;
    @Override
    public void initUI() {
        appSharedPref  = new AppSharedPref(this);
        if(!appSharedPref.getUserInfo().equals("")){
            switch (appSharedPref.getUserRole()){
                case "company":
                    intentActivty(CompanyMainActivity.class);
                    break;
                case "hr":
                    intentActivty(HRMainActivity.class);
                    break;
                case "employee":
                    intentActivty(MainNavigation.class);
                    break;
                case "admin":
                    intentActivty(AdminMainActivity.class);
                    break;
            }
        }


    }



}
