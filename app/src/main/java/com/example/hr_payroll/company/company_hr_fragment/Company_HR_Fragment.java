package com.example.hr_payroll.company.company_hr_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hr_payroll.R;
import com.example.hr_payroll.company.company_hr_fragment.adapter.Company_HR_Fragment_Adapter;
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

public class Company_HR_Fragment extends Fragment implements Company_HR_FragmentMVP.Fragment {
    private AppSharedPref appSharedPref;
    private Company_HR_Fragment_Adapter hr_fragment_adapter;
    private Company_HR_Fragment_Presenter presenter;
    private Functions functions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appSharedPref = new AppSharedPref(getActivity());
        hr_fragment_adapter = new Company_HR_Fragment_Adapter(getActivity());
        presenter = new Company_HR_Fragment_Presenter(this,getActivity());
        functions = new Functions(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.company_hr_fragment,container,false);
    }
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(hr_fragment_adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.GetEmployees(appSharedPref.getUserInfo());
    }

    @Override
    public void onGetEmployeesSuccess(ArrayList<Employee> employees) {
        hr_fragment_adapter.SetData(employees);
    }

    @Override
    public void onGetEmployeesFailed(String title, String message) {
        functions.showMessage(title,message);
    }
}
