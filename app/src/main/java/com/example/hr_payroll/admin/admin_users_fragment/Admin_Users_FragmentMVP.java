package com.example.hr_payroll.admin.admin_users_fragment;

import com.example.hr_payroll.model.User;

import java.util.ArrayList;

public interface  Admin_Users_FragmentMVP {
    interface Fragment{
        void GetUsersSuccess(ArrayList<User> users);
        void GetUsersFailed(String title,String message);

    }
    interface Presenter{
        void onGetUsers();
        void onGetUsersSuccess(ArrayList<User> users);
        void onGetUsersFailed(String title,String message);

    }
}
