package com.example.hr_payroll.admin.admin_users_fragment;

import android.content.Context;
import com.example.hr_payroll.model.User;
import java.util.ArrayList;


public class Admin_Users_Fragment_Presenter implements Admin_Users_FragmentMVP.Presenter {
    private Admin_Users_FragmentMVP.Fragment fragment;
    private Admin_Users_Fragment_InterActor interActor;
    public Admin_Users_Fragment_Presenter(Admin_Users_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Admin_Users_Fragment_InterActor(context);
    }
    @Override
    public void onGetUsers() {
        interActor.GetUsers(this);
    }

    @Override
    public void onGetUsersSuccess(ArrayList<User> users) {
        fragment.GetUsersSuccess(users);
    }

    @Override
    public void onGetUsersFailed(String title, String message) {
        fragment.GetUsersFailed(title,message);
    }
}
