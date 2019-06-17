package com.example.hr_payroll.signin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.AdminMainActivity;
import com.example.hr_payroll.company.CompanyMainActivity;
import com.example.hr_payroll.hr.fragment.HRMainActivity;
import com.example.hr_payroll.main.MainActivityView;
import com.example.hr_payroll.main_navigation.MainNavigation;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;
import com.example.hr_payroll.utilities.TinyDB;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInView extends AppCompatActivity implements SignInMVP.View {
    private Functions functions;
    private TinyDB tinyDB;
    private SignInPresenter presenter;
    private AppSharedPref appSharedPref;
    @BindView(R.id.email)
    EditText tf_email;
    @BindView(R.id.password)
    EditText tf_password;
    @BindView(R.id.signIn)
    Button btn_signin;
    @OnClick(R.id.signIn)
    void signInClick(){
        if(isEmpty(tf_email)){
            functions.showMessage("Failed","Email is empty");
        }else if(isEmpty(tf_password)){
            functions.showMessage("Failed","Password is empty");
        }else {
            presenter.onLogin(tf_email.getText().toString(), tf_password.getText().toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_signin);
        ButterKnife.bind(this);
        presenter = new SignInPresenter(this);
    }

    @Override
    public void initUI() {
        functions  = new Functions(this);
        tinyDB = new TinyDB(this);
        appSharedPref = new AppSharedPref(this);
    }



    @Override
    public void showErrorMessage(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void loginSuccess(User user) {
        appSharedPref.setUserInfo(user.getKey(),user.getEmail(),user.getRole());
        switch (user.getRole()){
            case "company":
                intentActivity(CompanyMainActivity.class);
                break;
            case "hr":
                intentActivity(HRMainActivity.class);
                break;
            case "employee":
                intentActivity(MainNavigation.class);
                break;

            case "admin":
                intentActivity(AdminMainActivity.class);
                break;
        }
        //intentActivity(MainNavigation.class);
    }
    private void intentActivity(Class classname){
        startActivity(new Intent(this,classname));
        finish();
    }

    @Override
    public void showMessage(String title, String message) {

    }
    private  boolean isEmpty(EditText editText){
        return TextUtils.isEmpty(editText.getText()) ? true  : false;
    }
}
