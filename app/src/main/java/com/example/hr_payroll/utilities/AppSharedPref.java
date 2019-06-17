package com.example.hr_payroll.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hr_payroll.model.User;

public class AppSharedPref {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public AppSharedPref(Context context){
        this.context = context;
        sharedPreferences =context.getSharedPreferences("apppref",Context.MODE_PRIVATE);

    }
    public void setUserInfo(String key,String email,String role){
        editor = sharedPreferences.edit();
        editor.putString("user_id",key);
        editor.putString("email",email);
        editor.putString("role",role);
        editor.commit();
    }
    public void clearData(){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public String getUserInfo(){
        return sharedPreferences.getString("user_id","");

    }
    public String getUserRole(){
        return sharedPreferences.getString("role","");
    }
}
