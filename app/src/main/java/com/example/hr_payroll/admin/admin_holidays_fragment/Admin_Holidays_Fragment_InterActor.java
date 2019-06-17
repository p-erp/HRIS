package com.example.hr_payroll.admin.admin_holidays_fragment;

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

public class Admin_Holidays_Fragment_InterActor {
    private Context context;
    private String ip;
    private RequestQueue requestQueue;
    private ArrayList<Holiday> holidays;
    public Admin_Holidays_Fragment_InterActor(Context context){
         this.context = context;
         holidays = new ArrayList<>();
         ip = Constant.URLProxy;
         requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
    }
    public void SaveDate(Admin_Holidays_FragmentMVP.Presenter presenter ,HashMap<String, String> date, String name){
        String url = ip+"/hr_app/processes/save_holiday_process.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("insertion_success")){
                    presenter.onSaveSuccess("Success","Holiday has been saved");
                }else if(response.equals("insertion_failed")){
                    presenter.onSaveSuccess("Success","Holiday saving failed");

                }else if(response.equals("exist")){
                    presenter.onSaveSuccess("Success","Holiday has already added");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onSaveFailed("Failed",""+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("month",date.get("month"));
                hashMap.put("day",date.get("day"));
                hashMap.put("year",date.get("year"));
                hashMap.put("name",name);

                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getAllHoliday(Admin_Holidays_FragmentMVP.Presenter presenter){
        String url = ip+"/hr_app/processes/get_holiday_process.php";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 if(response.equals("nodata")){
                     presenter.onGetHolidayFailed("Failed","No Data");
                 }else if(response.equals("failed")){
                     presenter.onGetHolidayFailed("Failed","Cant Fetch Data");

                 }else {
                     try {
                         JSONObject jsonObject = new JSONObject(response);
                         JSONArray jsonArray = jsonObject.getJSONArray("data");
                         holidays.clear();
                         for (int i = 0; i < jsonArray.length(); i++) {
                             JSONObject jsonRow = jsonArray.getJSONObject(i);
                             Holiday holiday = new Holiday();
                             holiday.setKey(String.valueOf(jsonRow.getString("holiday_id")));
                             holiday.setDay(String.valueOf(jsonRow.getString("day")));
                             holiday.setMonth(String.valueOf(jsonRow.getString("month")));
                             holiday.setYear(String.valueOf(jsonRow.getString("year")));
                             holiday.setName(String.valueOf(jsonRow.getString("name")));
                             holidays.add(holiday);
                         }
                         presenter.onGetHolidaySuccess(holidays);
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
                 return super.getParams();
             }
         };
         requestQueue.add(stringRequest);
    }

}
