package com.example.hr_payroll.company.company_employees_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hr_payroll.R;
import com.example.hr_payroll.company.company_employees_fragment.adapter.Employees_Fragment_Adapter;
import com.example.hr_payroll.model.Employee;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Company_Employees_Fragment extends Fragment implements Company_Employees_FragmentMVP.Fragment {
    private Company_Employees_Fragment_Presenter presenter;
    //private Employees_Fragment_Adapter employees_fragment_adapter;
    private Employees_Fragment_Adapter employees_fragment_adapter;
    private Functions functions;
    private AppSharedPref appSharedPref;
    @Override
    public void onResume() {
        super.onResume();
        presenter.GetEmployees(appSharedPref.getUserInfo());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Company_Employees_Fragment_Presenter(this,getActivity());
        employees_fragment_adapter = new Employees_Fragment_Adapter(getActivity());
        functions = new Functions(getActivity());
        appSharedPref = new AppSharedPref(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.hr_employees_fragment,container,false);
    }
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(employees_fragment_adapter);
    }

    @Override
    public void onGetEmployeesSuccess(ArrayList<Employee> employees) {
        employees_fragment_adapter.SetData(employees);

    }

    @Override
    public void onGetEmployeesFailed(String title, String message) {
        functions.showMessage(title,message);

    }
}
