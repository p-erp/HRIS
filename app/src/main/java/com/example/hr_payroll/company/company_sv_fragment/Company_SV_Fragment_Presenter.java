package com.example.hr_payroll.company.company_sv_fragment;

import android.content.Context;

import com.example.hr_payroll.model.Employee;

import java.util.ArrayList;

public class Company_SV_Fragment_Presenter implements Company_SV_FragmentMVP.Presenter {
    private Company_SV_FragmentMVP.Fragment fragment;
    private Company_SV_Fragment_InterActor interActor;
    public  Company_SV_Fragment_Presenter(Company_SV_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Company_SV_Fragment_InterActor(context);
    }
    @Override
    public void GetEmployees(String company_id) {
        interActor.GetEmployees(this,company_id);
    }

    @Override
    public void onGetEmployeesSuccess(ArrayList<Employee> employees) {
        fragment.onGetEmployeesSuccess(employees);
    }

    @Override
    public void onGetEmployeesFailed(String title, String message) {
        fragment.onGetEmployeesFailed(title,message);
    }
}
