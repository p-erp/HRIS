package com.example.hr_payroll.main_navigation.fragments.home_fragment;

import com.example.hr_payroll.model.Attendance;

import java.util.ArrayList;

public interface Home_FragmentMVP {
    interface Fragment{
        void onAttendanceClick();
        void onAttendanceTimedIn();
        void onAttendanceTimedOut();
        void showMessage(String title,String message);
        void hasTimeIn(boolean bool);
        void attendanceList(ArrayList<Attendance> attendanceArrayList);

    }
    interface Presenter{
        void onAttendance(Attendance attendance);
        void onAttendanceFailed(String title,String message);
        void onAttendanceSuccess(String title,String message);
        void onAttendanceTimeOutSuccess(String title,String message);
        void hasTimeIn(boolean bool);
        void getUserData(String user_id);
        void  getUserAttendace(String user_id,String month,String day,String year);
        void onAttendaceListSuccess(ArrayList<Attendance> attendanceArrayList);


    }
}
