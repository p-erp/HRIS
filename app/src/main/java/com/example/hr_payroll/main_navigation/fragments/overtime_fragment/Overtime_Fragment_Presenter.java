package com.example.hr_payroll.main_navigation.fragments.overtime_fragment;

import android.content.Context;

public class Overtime_Fragment_Presenter implements Overtime_FragmentMVP.Presenter {
    private Overtime_FragmentMVP.Fragment fragment;
    private Overtime_Fragment_InterActor interActor;
    public Overtime_Fragment_Presenter(Overtime_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Overtime_Fragment_InterActor(context);

    }

}
