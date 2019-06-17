package com.example.hr_payroll.hr.fragment.employees;

import android.content.Context;

import com.example.hr_payroll.model.Employee;

import java.util.ArrayList;

public class Employees_Fragment_Presenter implements Employees_FragmentMVP.Presenter {
    private Employees_FragmentMVP.Fragment fragment;
    private Employees_Fragment_InterActor interActor;
    public Employees_Fragment_Presenter(Employees_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Employees_Fragment_InterActor(context);
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
