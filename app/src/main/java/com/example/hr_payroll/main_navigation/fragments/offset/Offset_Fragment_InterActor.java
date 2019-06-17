package com.example.hr_payroll.main_navigation.fragments.offset;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.Offset;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Offset_Fragment_InterActor {
    private RequestQueue requestQueue;
    private ArrayList<Offset> offsets;
    public Offset_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        offsets = new ArrayList<>();
    }




    public void GetOffSet(Offset_Fragment_Presenter presenter,String user_id){
        String url = Constant.URLProxy+"/hr_app/processes/emp_get_offset.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("failed")){
                            presenter.onGetOffSetFailed("Failed","Cant Fetch Data");
                        }else if(response.equals("nodata")){
                            presenter.onGetOffSetFailed("Failed","No Data to be Fetch");

                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray =  jsonObject.getJSONArray("data");
                                offsets.clear();
                                for(int i = 0 ; i < jsonArray.length();i++){
                                    Offset offset = new Offset();
                                    JSONObject jsonRow = jsonArray.getJSONObject(i);
                                    offset.setKey(jsonRow.getString("offset_id"));
                                    offset.setDayoffset_new_day(jsonRow.getString("dayoffset_new_day"));
                                    offset.setDayoffset_new_month(jsonRow.getString("dayoffset_new_month"));
                                    offset.setDayoffset_new_year(jsonRow.getString("dayoffset_new_year"));
                                    offset.setDayoffset_day(jsonRow.getString("dayoffset_day"));
                                    offset.setDayoffset_month(jsonRow.getString("dayoffset_month"));
                                    offset.setDayoffset_year(jsonRow.getString("dayoffset_year"));
                                    offsets.add(offset);
                                }
                                presenter.onGetOffSetSuccess(offsets);

                            } catch (JSONException e) {
                                presenter.onGetOffSetFailed("Failed",e.getMessage());

                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onGetOffSetFailed("Failed",error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user",user_id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void SaveOffset(Offset_Fragment_Presenter presenter,HashMap startOffSet, HashMap newOffset, String user_id){
        String url = Constant.URLProxy+"/hr_app/processes/emp_save_offset.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("exist")) {
                    presenter.onSaveOffSetFailed("Failed","You have already offset with same date");
                }else if(response.equals("insertion_success")) {
                    presenter.onSaveOffSetSuccess("Success","Offset has been saved");

                }else if(response.equals("insertion_failed")) {
                    presenter.onSaveOffSetFailed("Failed","You have already offset with same date");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onSaveOffSetFailed("Failed",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("new_day",newOffset.get("day"));
                hashMap.put("new_month",newOffset.get("month"));
                hashMap.put("new_year",newOffset.get("year"));
                hashMap.put("day",startOffSet.get("day"));
                hashMap.put("month",startOffSet.get("month"));
                hashMap.put("year",startOffSet.get("year"));
                hashMap.put("user",user_id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
