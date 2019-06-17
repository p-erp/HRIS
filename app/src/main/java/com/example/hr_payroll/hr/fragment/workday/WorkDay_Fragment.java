package com.example.hr_payroll.hr.fragment.workday;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.hr.fragment.workday.adapter.WorkDays_Adapter;
import com.example.hr_payroll.hr.fragment.workday.adapter.WorkDays_Add_ListAdapter;
import com.example.hr_payroll.model.WorkDay;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkDay_Fragment extends Fragment implements OnCall, WorkDay_FragmentMVP.Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return LayoutInflater.from(getActivity()).inflate(R.layout.hr_workday_fragment,container,false);
    }
    @BindView(R.id.add_workday)
    FloatingActionButton floatingActionButton_add;
    @OnClick(R.id.add_workday)
    void addWorkDay(){
        showCustomDialog(getActivity());
    }

    private Functions functions;
    private WorkDay_Fragment_Presenter presenter;
    private WorkDays_Adapter workDays_adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        floatingActionButton_add = view.findViewById(R.id.add_workday);
        functions = new Functions(getActivity());
        presenter = new WorkDay_Fragment_Presenter(this,getActivity());
        workDays_adapter = new WorkDays_Adapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(workDays_adapter);

    }
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetWorkDays("1");
    }

    private Dialog dialog;
    private View customDialogView;
    private EditText tf_hr_day;
    private EditText tf_hr_start;
    private EditText tf_hr_end;
    private EditText tf_hr_year;

    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_hr_add_workday, null, false);
        tf_hr_day = customDialogView.findViewById(R.id.tf_hr_day);
        tf_hr_start = customDialogView.findViewById(R.id.tf_hr_start);
        tf_hr_end= customDialogView.findViewById(R.id.tf_hr_end);
        tf_hr_year = customDialogView.findViewById(R.id.tf_hr_year);
        Button work_day = customDialogView.findViewById(R.id.add_workday);
        work_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(functions.isEmpty(tf_hr_day)){
                    functions.showMessage("Failed","Empty Day");
                }else if(functions.isEmpty(tf_hr_start)){
                    functions.showMessage("Failed","Empty Start Time");

                }else if(functions.isEmpty(tf_hr_end)){
                    functions.showMessage("Failed","Empty End Time");

                }else if(functions.isEmpty(tf_hr_year)){

                    functions.showMessage("Failed","Empty Year");

                }else{

                    String sDay = tf_hr_day.getText().toString();
                    String sStart = tf_hr_start.getText().toString();
                    String sEnd = tf_hr_end.getText().toString();
                    String sYear = tf_hr_year.getText().toString();

                    DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY/MM/dd HH:mm aa");
                    DateTime dateTimeStart = formatter.parseDateTime("2019/06/08 "+sStart);
                    DateTime dateTimeEnd  = formatter.parseDateTime("2019/06/08 "+sEnd);
                    org.joda.time.Period period = new org.joda.time.Period(dateTimeStart,dateTimeEnd);
                    if(period.getHours() < 0 || period.getHours() < 0){
                      functions.showMessage("Failed","Starting time must be greater than end time");
                    }else {
                        presenter.onAddDay(sDay,sStart,sEnd,sYear,"1");

                    }


                }

            }
        });


        tf_hr_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                tf_hr_year.setText(""+selectedYear);
                            }
                        },year,month);
                        builder.setMinYear(2019)
                        .setActivatedYear(2019)
                        .setMaxYear(2050)
                        .setTitle("Select Year")
                        .showYearOnly()
                                .build().show();


            }
        });


        tf_hr_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                new TimePickerDialog(getActivity(),startListener,hour,minute,false).show();

            }
        });
        tf_hr_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                new TimePickerDialog(getActivity(),endListener,hour,minute,false).show();

            }
        });
        tf_hr_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialogShowDay(v.getContext());
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
    private Dialog dialog_days;
    public void showCustomDialogShowDay(final Context context) {
        dialog_days = new Dialog(context);
        dialog_days.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_hr_add_workday_day, null, false);
        RecyclerView recyclerView = customDialogView.findViewById(R.id.recycleview);
        recyclerView.setAdapter(new WorkDays_Add_ListAdapter(dialog.getContext(),this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog_days.setContentView(customDialogView);
        final Window window = dialog_days.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog_days.show();
    }

    @Override
    public void setDay(String day) {
        dialog_days.dismiss();
        tf_hr_day.setText(day);

    }

    private TimePickerDialog.OnTimeSetListener startListener  =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String AM_PM ;
                    if(hourOfDay < 12) {
                        AM_PM = "AM";

                    } else {
                        AM_PM = "PM";
                        hourOfDay=hourOfDay-12;
                    }
                    String hour = String.valueOf(hourOfDay);
                    String min = String.valueOf(minute);

                    tf_hr_start.setText( "" + functions.reFormatTime(hour) + ":" + functions.reFormatTimeMin(min)+" "+AM_PM);


                }
            };
    private TimePickerDialog.OnTimeSetListener endListener  =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String AM_PM ;
                    if(hourOfDay < 12) {
                        AM_PM = "AM";

                    } else {
                        AM_PM = "PM";
                        hourOfDay=hourOfDay-12;
                    }
                    String hour = String.valueOf(hourOfDay);
                    String min = String.valueOf(minute);

                    tf_hr_end.setText( "" + functions.reFormatTime(hour) + ":" + functions.reFormatTimeMin(min)+" "+AM_PM);

                }
            };

    @Override
    public void AddDaySuccess(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void AddDayFailed(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void WorkDayFailed(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void WorkDaySuccess(ArrayList<WorkDay> workDays) {
        Toast.makeText(getActivity(), ""+workDays.size(), Toast.LENGTH_SHORT).show();
        workDays_adapter.SetData(workDays);
        //presenter.onGetWorkDays("1");
      //  onResume();
    }
}
