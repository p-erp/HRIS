package com.example.hr_payroll.company.company_sv_fragment;

import com.example.hr_payroll.model.Employee;

import java.util.ArrayList;

public interface Company_SV_FragmentMVP {
    interface Fragment{
        void onGetEmployeesSuccess(ArrayList<Employee> employees);
        void onGetEmployeesFailed(String title,String message);
    }
    interface Presenter{
        void GetEmployees(String company_id);
        void onGetEmployeesSuccess(ArrayList<Employee> employees);
        void onGetEmployeesFailed(String title,String message);
    }
}
