package com.example.hr_payroll.hr.fragment.employees;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.hr.fragment.employees.adapter.Employees_Fragment_Adapter;
import com.example.hr_payroll.model.Employee;
import com.example.hr_payroll.utilities.Functions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Employees_Fragment extends Fragment implements Employees_FragmentMVP.Fragment {
    private Employees_FragmentMVP.Presenter presenter;
    private Employees_Fragment_Adapter employees_fragment_adapter;
    private Functions functions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Employees_Fragment_Presenter(this,getActivity());
        employees_fragment_adapter = new Employees_Fragment_Adapter(getActivity());
        functions = new Functions(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.hr_employees_fragment,container,false);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.GetEmployees("1");
    }

    private RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(employees_fragment_adapter);

    }

    @Override
    public void onGetEmployeesSuccess(ArrayList<Employee> employees) {
        Toast.makeText(getActivity(), ""+employees.size(), Toast.LENGTH_SHORT).show();
        employees_fragment_adapter.SetData(employees);
    }

    @Override
    public void onGetEmployeesFailed(String title, String message) {
        functions.showMessage(title,message);
    }
}
