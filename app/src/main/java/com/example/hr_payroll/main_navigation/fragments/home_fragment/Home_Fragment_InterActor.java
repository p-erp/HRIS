package com.example.hr_payroll.main_navigation.fragments.home_fragment;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class Home_Fragment_InterActor {
    private ArrayList<Attendance> attendanceArrayList;
    private RequestQueue requestQueue;
    private String ip ;
    public Home_Fragment_InterActor(Context context){
        attendanceArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        ip = Constant.URLProxy;
    }

    public void Attendace(Home_FragmentMVP.Presenter presenter, Attendance attendance){
        String url = ip+"/hr_app/processes/time_in_process.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("time_in_success")) {
                    presenter.onAttendanceSuccess("Success", "Time In");
                } else if (response.equals("time_out_success")){
                    presenter.onAttendanceTimeOutSuccess("Success", "Time Out");
                } else if (response.equals("time_in_failed")){
                    presenter.onAttendanceFailed("Failed","Time In");
                } else if (response.equals("time_out_failed")){
                    presenter.onAttendanceFailed("Failed","Time Out");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onAttendanceFailed("Failed",""+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id",attendance.getUser_id());

                hashMap.put("location_in_lat",attendance.getLocation_in_lat());
                hashMap.put("location_in_long",attendance.getLocation_in_long());


                hashMap.put("location_out_lat",attendance.getLocation_out_lat());
                hashMap.put("location_out_long",attendance.getLocation_out_long());
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);



    }
    public void GetAttendance(Home_FragmentMVP.Presenter presenter,String user_id){
            String url = ip+"/hr_app/processes/get_attendance_process.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("exist")) {
                        presenter.hasTimeIn(true);
                    } else if (response.equals("not")){
                        presenter.hasTimeIn(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    presenter.onAttendanceFailed("Failed",""+error);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap hashMap = new HashMap();
                    hashMap.put("user_id",user_id);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);


    }
    public void GetAttendanceInfo(Home_FragmentMVP.Presenter presenter,String user_id,String month_in,String day_in,
                String year_in){
        String url = ip+"/hr_app/processes/get_currentattendance_process.php";
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                presenter.onAttendanceFailed("qwe",response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    attendanceArrayList.clear();
                    for(int i = 0 ; i < jsonArray.length() ; i++) {
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
                    presenter.onAttendaceListSuccess(attendanceArrayList);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onAttendanceFailed("Failed",""+error);

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
