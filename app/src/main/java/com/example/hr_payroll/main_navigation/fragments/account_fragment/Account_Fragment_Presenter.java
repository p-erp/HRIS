package com.example.hr_payroll.main_navigation.fragments.account_fragment;

import android.content.Context;
import com.example.hr_payroll.model.User;

public class Account_Fragment_Presenter implements Account_FragmentMVP.Presenter {
    private Account_FragmentMVP.Fragment fragment;
    private Account_Fragment_InterActor fragment_interActor;
    public Account_Fragment_Presenter(Account_FragmentMVP.Fragment fragment,Context context){
        this.fragment = fragment;
        fragment_interActor = new Account_Fragment_InterActor(context);


    }
    @Override
    public void getDetails(String user_id) {
        fragment_interActor.getUserDetails(this,user_id);

    }

    @Override
    public void getDetailSuccess(User user) {
        fragment.userDetails(user);

    }

    @Override
    public void getDetailFailed(String title, String message) {

    }
}
