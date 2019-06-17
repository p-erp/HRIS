package com.example.hr_payroll.admin.admin_users_fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_Users_Adapter extends RecyclerView.Adapter<Admin_Users_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<User> users;
    public Admin_Users_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_admin_users_fragment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.fullname.setText(user.getFirstname().concat(" , ").concat(user.getMiddlename().concat(" . ").concat(user.getLastname())));
        holder.mobile.setText(user.getMobile());
        holder.role.setText(user.getRole());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return users!=null ? users.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.fullname)
        TextView fullname;
        @BindView(R.id.email)
        TextView email;
        @BindView(R.id.role)
        TextView role;
        @BindView(R.id.mobile)
        TextView mobile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

