package com.example.hr_payroll.main_navigation.fragments.offset;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.hr_payroll.hr.fragment.workday.adapter.WorkDays_Add_ListAdapter;
import com.example.hr_payroll.main_navigation.fragments.offset.adapter.Offset_Fragment_Adapter;
import com.example.hr_payroll.model.Offset;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Offset_Fragment extends Fragment implements Offset_FragmentMVP.Fragment {
    private Offset_Fragment_Presenter presenter;
    @BindView(R.id.btn_addoffset)
    FloatingActionButton add_offset;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @OnClick(R.id.btn_addoffset)
    void addOffSet(){
        showCustomDialog(getActivity());
    }
    private Functions functions;
    private HashMap map_startOffset,map_exchangeOffset;
    private AppSharedPref appSharedPref;
    private DatePickerDialog.OnDateSetListener offset_start = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String Syear = ""+year;
            String Smonth = ""+(month+1);
            String Sday = ""+dayOfMonth;

            if(Smonth.length() == 1){
                Smonth = "0"+Smonth;
            }
            if(Sday.length() == 1){
                Sday= "0"+Sday;
            }
            map_startOffset.put("day",Sday);
            map_startOffset.put("month",Smonth);
            map_startOffset.put("year",Syear);

            tf_offset_start.setText(functions.formatDate(Syear+"-"+Smonth+"-"+Sday));
        }
    };

    private DatePickerDialog.OnDateSetListener offset_end= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String Syear = ""+year;
            String Smonth = ""+(month+1);
            String Sday = ""+dayOfMonth;


            if(Smonth.length() == 1){
                Smonth = "0"+Smonth;
            }
            if(Sday.length() == 1){
                Sday= "0"+Sday;
            }
            map_exchangeOffset.put("day",Sday);
            map_exchangeOffset.put("month",Smonth);
            map_exchangeOffset.put("year",Syear);
            tf_offset_exchange.setText(functions.formatDate(Syear+"-"+Smonth+"-"+Sday));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetOffSet("1");
    }
    private Offset_Fragment_Adapter offset_fragment_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new Offset_Fragment_Presenter(this,getActivity());
        functions = new Functions(getActivity());
        map_startOffset = new HashMap();
        map_exchangeOffset = new HashMap();
        offset_fragment_adapter = new Offset_Fragment_Adapter(getActivity());
        appSharedPref = new AppSharedPref(getActivity());

        return LayoutInflater.from(getActivity()).inflate(R.layout.main_navigation_offset_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(offset_fragment_adapter);

    }
    private Dialog dialog;
    private View customDialogView;
    private EditText tf_offset_start;
    private EditText tf_offset_exchange;
    private Button btn_add_offset;
    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_main_add_offset, null, false);


        tf_offset_start = customDialogView.findViewById(R.id.tf_offset_start);
        tf_offset_exchange = customDialogView.findViewById(R.id.tf_offset_exchange);
        btn_add_offset = customDialogView.findViewById(R.id.add_offset);
        btn_add_offset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(functions.isEmpty(tf_offset_start)){
                    functions.showMessage("Failed","Empty Offset Start");
                }else if(functions.isEmpty(tf_offset_exchange)){
                    functions.showMessage("Failed","Empty Offset Exchange");
                }else{
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY/MM/dd");
                    String start = map_startOffset.get("year").toString().concat("/").concat(map_startOffset.get("month").toString()).concat("/").concat(map_startOffset.get("day").toString());
                    String end = map_exchangeOffset.get("year").toString().concat("/").concat(map_exchangeOffset.get("month").toString()).concat("/").concat(map_exchangeOffset.get("day").toString());
                    DateTime dateTimeStart = formatter.parseDateTime(start);
                    DateTime dateTimeStop = formatter.parseDateTime(end);
                    org.joda.time.Period period = new org.joda.time.Period(dateTimeStart,dateTimeStop);
                    if(period.getDays() < 1 ){
                        functions.showMessage("Failed","Date must be greater");
                    }else{
                        presenter.onSaveOffSet(map_startOffset,map_exchangeOffset,appSharedPref.getUserInfo());
                    }

                }
            }
        });

        customDialogView.findViewById(R.id.add_offset);

        tf_offset_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                new DatePickerDialog(getActivity(),offset_start,year,month,day).show();

            }
        });

        tf_offset_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                new DatePickerDialog(getActivity(),offset_end,year,month,day).show();

            }
        });
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(customDialogView);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    @Override
    public void SaveOffSetSuccess(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void SaveOffSetFailed(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void GetOffSetSuccess(ArrayList<Offset> offsets) {
        offset_fragment_adapter.SetData(offsets);
    }

    @Override
    public void GetOffSetFailed(String title, String message) {

    }
}
