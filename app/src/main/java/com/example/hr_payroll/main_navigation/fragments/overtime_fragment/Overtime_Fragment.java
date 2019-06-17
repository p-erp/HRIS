package com.example.hr_payroll.main_navigation.fragments.overtime_fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.lang.reflect.Array;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Overtime_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.main_navigation_overtime_fragment,container,false);
    }
    @BindView(R.id.btn_addovertime)
    FloatingActionButton btn_addovertime;
    @OnClick(R.id.btn_addovertime)
    void AddOverTime(){
        showCustomDialog(getActivity());
    }
    private Functions functions;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        functions = new Functions(getActivity());
    }

    private DatePickerDialog.OnDateSetListener datepicker_listener_start =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    tf_StartDate.setText(functions.formatDate(year+"-"+month+"-"+dayOfMonth));
                }
            };
    private DatePickerDialog.OnDateSetListener datepicker_listener_end =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    tf_EndDate.setText(functions.formatDate(year+"-"+month+"-"+dayOfMonth));
                }
            };
    private TimePickerDialog.OnTimeSetListener timepicker_listener_starttime
            = new TimePickerDialog.OnTimeSetListener() {
             @Override
             public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                 String AM_PM ;
                 if(hourOfDay < 12) {
                     AM_PM = "AM";

                 } else {
                     AM_PM = "PM";
                     hourOfDay=hourOfDay-12;
                 }
                 tf_StartTime.setText( "" + hourOfDay + ":" + minute+" "+AM_PM);

        }
    };
    private TimePickerDialog.OnTimeSetListener timepicker_listener_endtime
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String AM_PM ;
            if(hourOfDay < 12) {
                AM_PM = "AM";

            } else {
                AM_PM = "PM";
                hourOfDay=hourOfDay-12;
            }
            tf_EndTime.setText( "" + hourOfDay + ":" + minute+" "+AM_PM);

        }
    };

    private Dialog dialog;
    private View customDialogView;
    private EditText tf_OvertimeReason;
    private EditText tf_StartDate;
    private EditText tf_StartTime;
    private EditText tf_EndDate;
    private EditText tf_EndTime;


    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_main_add_overtime, null, false);
        Button btn_add_holiday = customDialogView.findViewById(R.id.add_holiday);
        tf_OvertimeReason = customDialogView.findViewById(R.id.tf_overtime_reason);
        tf_StartDate = customDialogView.findViewById(R.id.tf_overtime_start);
        tf_StartTime = customDialogView.findViewById(R.id.tf_overtime_start_time);
        tf_EndTime = customDialogView.findViewById(R.id.tf_overtime_end_time);
        tf_EndDate = customDialogView.findViewById(R.id.tf_overtime_end);


        tf_StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                new TimePickerDialog(getActivity(),timepicker_listener_starttime,hour,minute,true).show();
            }
        });
        tf_EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                new TimePickerDialog(getActivity(),timepicker_listener_endtime,hour,minute,false).show();

            }
        });
        tf_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(),datepicker_listener_start,year,month,day).show();
            }
        });

        tf_EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(),datepicker_listener_end,year,month,day).show();
            }
        });

    /*
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
        */


        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(customDialogView);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();


         try {
            Attendance attendance = new Attendance();


            DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY/MM/dd HH:mm:ss aa");
            DateTime dateTimeStart = formatter.parseDateTime("2019/06/07 12:15:00 PM");
            DateTime dateTimeStop = formatter.parseDateTime("2019/06/08 12:30:43 AM");
            org.joda.time.Period period = new org.joda.time.Period(dateTimeStop,dateTimeStart);


            PeriodFormatter periodFormatter = PeriodFormat.getDefault();
            String days = ""+period.getDays();
            String hours = ""+period.getHours();
            String minutes = ""+period.getMinutes();

            Toast.makeText(getActivity(), "Days :"+days+" , Hours :"+hours+" , Minutes :"+minutes , Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
