package com.example.hr_payroll.main_navigation.fragments.home_fragment;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.example.hr_payroll.model.Attendance;

import java.util.ArrayList;


public class Home_Fragment_Presenter implements Home_FragmentMVP.Presenter {
    Home_FragmentMVP.Fragment fragment;
    private Home_Fragment_InterActor fragment_interActor;
    public Home_Fragment_Presenter(Home_FragmentMVP.Fragment fragment,Context context){
        this.fragment = fragment;
       fragment_interActor = new Home_Fragment_InterActor(context);

    }
    @Override
    public void onAttendance(Attendance attendance) {
        fragment_interActor.Attendace(this,attendance);
    }

    @Override
    public void onAttendanceFailed(String title, String message) {
            fragment.showMessage(title,message);
    }

    @Override
    public void onAttendanceSuccess(String title, String message) {
            fragment.onAttendanceTimedIn();
    }

    @Override
    public void onAttendanceTimeOutSuccess(String title, String message) {
            fragment.onAttendanceTimedOut();
    }

    @Override
    public void getUserData(String user_id) {
            fragment_interActor.GetAttendance(this,user_id);
    }

    @Override
    public void getUserAttendace(String user_id, String month, String day, String year) {
        fragment_interActor.GetAttendanceInfo(this,user_id,month,day,year);
    }

    @Override
    public void onAttendaceListSuccess(ArrayList<Attendance> attendanceArrayList) {
        fragment.attendanceList(attendanceArrayList);
    }

    @Override
    public void hasTimeIn(boolean bool){
        fragment.hasTimeIn(bool);
    }
}
