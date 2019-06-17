package com.example.hr_payroll.hr.fragment.workday;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.WorkDay;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkDay_Fragment_InterActor {
    private RequestQueue requestQueue;
    private ArrayList<WorkDay> workDays;
    public WorkDay_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        workDays  = new ArrayList<>();
    }
    public void AddWorkDay(WorkDay_Fragment_Presenter presenter, String day,String start,String end,String year,String user){
        String url = Constant.URLProxy+ "/hr_app/processes/hr_save_workday.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success")){
                    presenter.onAddDaySuccess("","");
                }else if(response.equals("exist")){
                    presenter.onAddDayFailed("Failed","Day Already exist");
                }else if(response.equals("failed")){
                    presenter.onAddDayFailed("Failed","Cant insert data");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onAddDayFailed("",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("day",day);
                hashMap.put("start",start);
                hashMap.put("end",end);
                hashMap.put("year",year);
                hashMap.put("user",user);

                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void GetAllWorkDays(WorkDay_Fragment_Presenter presenter,String userid){
        String url = Constant.URLProxy+"/hr_app/processes/get_hr_workdays.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("nodata")){
                    presenter.onGetWorkDayFailed("Failed","No Data");
                }else if(response.equals("failed")){
                    presenter.onGetWorkDayFailed("Failed","Cant Get Workdays");

                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        workDays.clear();
                        for(int i = 0 ; i < jsonArray.length() ; i ++){
                            WorkDay workDay = new WorkDay();
                            JSONObject jsonRow = jsonArray.getJSONObject(i);

                            workDay.setKey(jsonRow.getString("dayofwork_id"));
                            workDay.setWork_days(jsonRow.getString("work_day"));
                            workDay.setTime_start(jsonRow.getString("time_start"));
                            workDay.setTime_end(jsonRow.getString("time_end"));
                            workDay.setYear(jsonRow.getString("year"));
                            workDays.add(workDay);
                        }
                        presenter.onGetWorkDaySuccess(workDays);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        presenter.onGetWorkDayFailed("Failed",e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onGetWorkDayFailed("Failed",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("user",userid);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
