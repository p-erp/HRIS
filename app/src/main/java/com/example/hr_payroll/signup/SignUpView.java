package com.example.hr_payroll.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.Functions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpView extends AppCompatActivity implements SignUpMVP.View {
    @BindView(R.id.email)
    EditText tf_email;
    @BindView(R.id.contact)
    EditText tf_contact;
    @BindView(R.id.password)
    EditText tf_password;
    @BindView(R.id.verifypassword)
    EditText tf_verifypassword;
    @BindView(R.id.signup)
    Button btn_signup;
    @OnClick(R.id.signup)
    void signUp(){
        User user = new User();
        String email = tf_email.getText().toString();
        String password = tf_password.getText().toString();
        String verifypassword  = tf_verifypassword.getText().toString();
        String contact = tf_contact.getText().toString();
        if(isEmpty(email)){
            functions.showMessage("Empty","Email is empty");
        }else if(isEmpty(contact)){
            functions.showMessage("Empty","Contact is empty");
        }else if(isEmpty(password)){
            functions.showMessage("Empty","Password is empty");
        }else if(isEmpty(verifypassword)){
            functions.showMessage("Empty","Verify password is empty");
        }else if(!password.equals(verifypassword)){
            functions.showMessage("Empty","Password does not match");
        }else {
             user.setEmail(email);
             user.setPassword(password);
             user.setMobile(contact);
              signUpPresenter.onSignUp(user);
        }
    }
    private boolean isEmpty(String field){
        return TextUtils.isEmpty(field) ? true : false;
    }

    private SignUpPresenter signUpPresenter;
    private Functions functions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_signup);
        ButterKnife.bind(this);
        signUpPresenter = new SignUpPresenter(this);
    }

    @Override
    public void initUI() {
        functions = new Functions(this);

    }

    @Override
    public void showErrorMessage(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void showSuccessMessage(String title, String message) {
        functions.showMessage(title,message);
    }
}
