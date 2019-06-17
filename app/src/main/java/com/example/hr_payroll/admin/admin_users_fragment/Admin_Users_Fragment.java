package com.example.hr_payroll.admin.admin_users_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.admin_users_fragment.adapter.Admin_Users_Adapter;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.Functions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_Users_Fragment extends Fragment implements Admin_Users_FragmentMVP.Fragment {

    private Admin_Users_Adapter users_adapter;
    private Functions functions;
    private Admin_Users_Fragment_Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        users_adapter = new Admin_Users_Adapter(getActivity());
        functions  = new Functions(getActivity());
        presenter = new Admin_Users_Fragment_Presenter(this,getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.admin_users_fragment,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetUsers();
    }

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(users_adapter);
    }

    @Override
    public void GetUsersSuccess(ArrayList<User> users) {
        users_adapter.SetData(users);
    }

    @Override
    public void GetUsersFailed(String title, String message) {
        functions.showMessage(title,message);

    }
}
