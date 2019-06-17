package com.example.hr_payroll.hr.fragment.employees;

import com.example.hr_payroll.model.Employee;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface Employees_FragmentMVP {
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
