package com.example.hr_payroll.admin.admin_holidays_fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.adapter.Admin_Holidays_Adapter;
import com.example.hr_payroll.model.Holiday;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Admin_Holidays_Fragment extends Fragment implements Admin_Holidays_FragmentMVP.Fragment {
    private Functions functions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.admin_holidays_fragment,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetHoliday();
    }

    @BindView(R.id.add_holiday)
    FloatingActionButton btn_addHoliday;
    @OnClick(R.id.add_holiday)
    void AddHoliday(){
        String month = "";
        String day = "";
        String year = "";
        String holiday_name = "";
        showCustomDialog(getActivity());

    }
    private Admin_Holidays_Fragment_Presenter presenter;


    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        presenter = new Admin_Holidays_Fragment_Presenter(this,getActivity());
        functions = new Functions(getActivity());
        super.onViewCreated(view, savedInstanceState);
    }
    private String formatDate(String deliveryDate){
        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormatprev.parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        String changedDate = dateFormat.format(d);

        return changedDate;
    }

    private HashMap<String,String> hashMapDate = new HashMap();
    DatePickerDialog.OnDateSetListener datepicker_listener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String Syear = ""+year;
                    String Smonth = ""+(month+1);
                    String Sday = ""+dayOfMonth;
                    hashMapDate.put("year",Syear);
                    hashMapDate.put("month",Smonth);
                    hashMapDate.put("day",Sday);
                    String dateOut =Syear+"-"+Smonth+"-"+Sday;
                    tf_admin_add_hint_date.setText(formatDate(dateOut));

                }
            };

    private Dialog dialog;
    private View customDialogView;
    private EditText tf_admin_add_hint_date,tf_admin_add_hint_holidayname;

    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_admin_add_holiday, null, false);
        Button btn_add_holiday = customDialogView.findViewById(R.id.add_holiday);
        tf_admin_add_hint_date = customDialogView.findViewById(R.id.tf_holiday_date);
        tf_admin_add_hint_holidayname = customDialogView.findViewById(R.id.tf_holiday_name);
        tf_admin_add_hint_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(),datepicker_listener,year,month,day).show();

            }
        });
        btn_add_holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(tf_admin_add_hint_date)){
                    functions.showMessage("Failed","Empty Holiday Date");
                }else if(isEmpty(tf_admin_add_hint_holidayname)){

                    functions.showMessage("Failed","Empty Holiday Name");
                }else{
                    presenter.onSave(hashMapDate,tf_admin_add_hint_holidayname.getText().toString());

                }
            }

        });
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(customDialogView);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    private boolean isEmpty(EditText editText){
        boolean isEmpty = false;
        if(TextUtils.isEmpty(editText.getText().toString())){
            isEmpty = true;
        }else{
            isEmpty = false;
        }
        return isEmpty;
    }

    private Admin_Holidays_Adapter holidays_adapter;
    @Override
    public void initUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        holidays_adapter = new Admin_Holidays_Adapter(getActivity());
        recyclerView.setAdapter(holidays_adapter);
    }

    @Override
    public void onSaveSuccess(String title, String message) {
        dialog.dismiss();
        functions.showMessage(title,message);
        onResume();
    }

    @Override
    public void onSaveFailed(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void onHolidayFailed(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void onHolidaySuccess(ArrayList<Holiday> holidays) {
        holidays_adapter.SetData(holidays);
    }
}
