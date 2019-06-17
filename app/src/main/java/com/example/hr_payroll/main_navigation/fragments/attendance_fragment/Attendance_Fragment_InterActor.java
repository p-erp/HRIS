package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.model.Holiday;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Attendance_Fragment_InterActor {
   private RequestQueue requestQueue;
   private String ip = "";
   private ArrayList<Holiday> holidayArrayList;
    private ArrayList<Attendance> attendanceArrayList;
    public Attendance_Fragment_InterActor(Context context){
      requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
      ip = Constant.URLProxy;
       attendanceArrayList = new ArrayList<>();
       holidayArrayList= new ArrayList<>();
   }
   public void GetHolidays(Attendance_FragmentMVP.Presenter presenter,String month,String year){
     String url = ip+"/hr_app/processes/get_holidaymonth_process.php";
     StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
     @Override
     public void onResponse(String response) {

         if(response.equals("failed")){
                presenter.onGetHolidayFailed("Failed","Cant fetch holiday");
            }else if(response.equals("nodata")){
                presenter.onGetHolidayFailed("Failed","nodata");

            }else{
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    holidayArrayList.clear();
                    for(int i = 0 ; i  < jsonArray.length();i++){
                        JSONObject jsonRow = jsonArray.getJSONObject(i);
                        Holiday holiday = new Holiday();
                        holiday.setKey(jsonRow.getString("holiday_id"));
                        holiday.setDay(jsonRow.getString("day"));
                        holiday.setYear(jsonRow.getString("year"));
                        holiday.setMonth(jsonRow.getString("month"));
                        holiday.setName(jsonRow.
                                getString("name"));
                        holidayArrayList.add(holiday);
                    }
                    presenter.onGetHolidaySuccess(holidayArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
     }
    }, new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {
         presenter.onGetHolidayFailed("Failed",""+error);

     }
    }){
     @Override
     protected Map<String, String> getParams() throws AuthFailureError {
         HashMap hashMap = new HashMap();
         hashMap.put("month",month);
         hashMap.put("year",year);

         return hashMap;
     }
    };
    requestQueue.add(stringRequest);
   }
    public void GetAttendanceInfo(Attendance_FragmentMVP.Presenter presenter, String user_id, String month_in, String day_in,
                                  String year_in){
        String url = ip+"/hr_app/processes/get_currentattendance_processinput.php";
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("nodata")){
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("month",month_in);
                    hashMap.put("year",year_in);
                    hashMap.put("day",day_in);
                    presenter.onAttendanceFailed(hashMap,"nodata");
                }else {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        attendanceArrayList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonRow = jsonArray.getJSONObject(i);
                            Attendance attendance = new Attendance();
                            attendance.setMonth_in(String.valueOf(jsonRow.getString("month_in")));
                            attendance.setDay_in(String.valueOf(jsonRow.getString("day_in")));
                            attendance.setYear_in(String.valueOf(jsonRow.getString("year_in")));
                            attendance.setLocation_in_lat(String.valueOf(jsonRow.getString("location_in_lat")));
                            attendance.setLocation_in_long(String.valueOf(jsonRow.getString("location_in_long")));
                            attendance.setTime_in(String.valueOf(jsonRow.getString("time_in")));
                            attendance.setMonth_out(String.valueOf(jsonRow.getString("month_out")));
                            attendance.setDay_out(String.valueOf(jsonRow.getString("day_out")));
                            attendance.setYear_out(String.valueOf(jsonRow.getString("year_out")));
                            attendance.setLocation_out_lat(String.valueOf(jsonRow.getString("location_out_lat")));
                            attendance.setLocation_out_long(String.valueOf(jsonRow.getString("location_out_long")));
                            attendance.setTime_out(String.valueOf(jsonRow.getString("time_out")));
                            attendanceArrayList.add(attendance);

                        }
                        presenter.onAttendanceListSuccess(attendanceArrayList);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("title","Failed");

                presenter.onAttendanceFailed(hashMap,""+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id",user_id);
                hashMap.put("month_in",month_in);
                hashMap.put("day_in",day_in);
                hashMap.put("year_in",year_in);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
