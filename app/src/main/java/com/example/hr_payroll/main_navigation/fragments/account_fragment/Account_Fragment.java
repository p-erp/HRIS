package com.example.hr_payroll.main_navigation.fragments.account_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Account_Fragment extends Fragment implements Account_FragmentMVP.Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Account_Fragment_Presenter(this,getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.main_navigation_acount_fragment,container,false);
    }

    private AppSharedPref appSharedPref;
    private Functions functions;


    @BindView(R.id.age)
    TextView tf_Age;
    @BindView(R.id.email)
    TextView tf_Email;
    @BindView(R.id.firstname)
    TextView tf_Firstname;
    @BindView(R.id.lastname)
    TextView tf_Lastname;
    @BindView(R.id.middle)
    TextView tf_Middle;
    @BindView(R.id.contact)
    TextView tf_Contact;

    private Account_Fragment_Presenter presenter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        appSharedPref = new AppSharedPref(getActivity());
        functions = new Functions(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getDetails(appSharedPref.getUserInfo());
    }

    @Override
    public void onPause() {
        super.onPause();
    }






    @Override
    public void initUI() {

    }



    @Override
    public void userDetails(User user) {
        tf_Email.setText(user.getEmail());
        tf_Firstname.setText(user.getFirstname());
        tf_Lastname.setText(user.getLastname());
        tf_Middle.setText(user.getMiddlename());
        tf_Age.setText(user.getAge());

        tf_Contact.setText(user.getMobile());
        if(user.getFirstname().equals(""))
            tf_Firstname.setText("No Detail");
        if(user.getMiddlename().equals(""))
            tf_Middle.setText("No Detail");
        if(user.getLastname().equals(""))
            tf_Lastname.setText("No Detail");
        if(user.getAge().equals(""))
            tf_Age.setText("No Detail");



    }

    @Override
    public void errorDetaile(String title, String message) {
        functions.showMessage(title,message);
    }
}
