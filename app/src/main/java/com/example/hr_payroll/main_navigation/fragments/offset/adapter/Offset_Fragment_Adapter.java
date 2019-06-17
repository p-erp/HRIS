package com.example.hr_payroll.main_navigation.fragments.offset.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Offset;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Offset_Fragment_Adapter  extends RecyclerView.Adapter<Offset_Fragment_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Offset> offsets;
    public Offset_Fragment_Adapter(Context context){
        this.context = context;
        offsets = new ArrayList<>();
    }
    public void SetData(ArrayList<Offset> offsets){
        this.offsets = offsets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fragment_offset,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return offsets!=null?offsets.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
