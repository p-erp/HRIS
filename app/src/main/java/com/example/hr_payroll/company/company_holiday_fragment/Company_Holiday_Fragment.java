package com.example.hr_payroll.company.company_holiday_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hr_payroll.model.Holiday;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class Company_Holiday_Fragment extends Fragment implements Company_Holiday_FragmentMVP.Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void onSaveSuccess(String title, String message) {

    }

    @Override
    public void onSaveFailed(String title, String message) {

    }

    @Override
    public void onHolidayFailed(String title, String message) {

    }

    @Override
    public void onHolidaySuccess(ArrayList<Holiday> holidays) {

    }
}
