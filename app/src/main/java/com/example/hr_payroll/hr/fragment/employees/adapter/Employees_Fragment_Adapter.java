package com.example.hr_payroll.hr.fragment.employees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Employee;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Employees_Fragment_Adapter extends RecyclerView.Adapter<Employees_Fragment_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Employee> employees;
    public Employees_Fragment_Adapter(Context context){
        this.context = context;
        employees = new ArrayList<>();
    }
    public void SetData(ArrayList<Employee> employees){
        this.employees = employees;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_hr_employees_fragment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.email.setText(employee.getEmail());
        holder.fullname.setText(employee.getFirstname().concat(" , ").concat(employee.getLastname()));
        holder.mobile.setText(employee.getMobile());
        holder.role.setText(employee.getRole());


    }



    @Override
    public int getItemCount() {
        return  employees != null ? employees.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.view_details)
        public Button btn_view_details;
        @BindView(R.id.fullname)
        public TextView fullname;
        @BindView(R.id.email)
        public TextView email;
        @BindView(R.id.role)
        public TextView role;
        @BindView(R.id.mobile)
        public TextView mobile;






        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            btn_view_details = itemView.findViewById(R.id.view_details);
        }
    }
}
