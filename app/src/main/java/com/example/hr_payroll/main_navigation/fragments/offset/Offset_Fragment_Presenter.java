package com.example.hr_payroll.main_navigation.fragments.offset;

import android.content.Context;

import com.example.hr_payroll.admin.AdminMainActivityMVP;
import com.example.hr_payroll.main_navigation.fragments.attendance_fragment.Attendance_FragmentMVP;
import com.example.hr_payroll.model.Offset;

import java.util.ArrayList;
import java.util.HashMap;

public class Offset_Fragment_Presenter implements Offset_FragmentMVP.Presenter {
    private Offset_FragmentMVP.Fragment fragment;
    private Offset_Fragment_InterActor interActor;
    public Offset_Fragment_Presenter(Offset_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Offset_Fragment_InterActor(context);

    }

    @Override
    public void onSaveOffSet(HashMap startOffSet, HashMap newOffset, String user_id) {
        interActor.SaveOffset(this,startOffSet,newOffset,user_id);
    }

    @Override
    public void onSaveOffSetSuccess(String title, String message) {
        fragment.SaveOffSetSuccess(title,message);
    }

    @Override
    public void onSaveOffSetFailed(String title, String message) {
        fragment.SaveOffSetFailed(title,message);
    }

    @Override
    public void onGetOffSet(String user_id) {
        interActor.GetOffSet(this,user_id);
    }

    @Override
    public void onGetOffSetSuccess(ArrayList<Offset> offsets) {
        fragment.GetOffSetSuccess(offsets);
    }

    @Override
    public void onGetOffSetFailed(String title, String message) {
        fragment.GetOffSetFailed(title,message);
    }
}
