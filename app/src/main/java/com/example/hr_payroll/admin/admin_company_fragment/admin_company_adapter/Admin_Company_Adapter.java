package com.example.hr_payroll.admin.admin_company_fragment.admin_company_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Company;
import com.example.hr_payroll.utilities.Constant;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Company_Adapter extends RecyclerView.Adapter<Admin_Company_Adapter.ViewHolder> {
    private ArrayList<Company> companies;
    private Context context;
    public Admin_Company_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<Company> companies){
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_admin_company_fragment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.name.setText(company.getName());
        holder.address.setText(company.getAddress());
        holder.laneline.setText(company.getLandline());
        holder.mobile.setText(company.getMobile());

        if(company.getImage()!=null) {
            Glide.with(context).load("http://localhost:8012/hr_app/"+ company.getImage()).into(holder.pic);
        }
    }

    @Override
    public int getItemCount() {
        return companies!=null? companies.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pic)
        public  CircleImageView pic;
        @BindView(R.id.name)
        public TextView name;
        @BindView(R.id.address)
        public TextView address;
        @BindView(R.id.landline)
        public TextView laneline;
        @BindView(R.id.mobile)
        public TextView mobile;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
