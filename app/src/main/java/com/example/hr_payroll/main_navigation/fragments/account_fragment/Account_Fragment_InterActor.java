package com.example.hr_payroll.main_navigation.fragments.account_fragment;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Account_Fragment_InterActor  {
    private RequestQueue requestQueue;
    private String ip;
    public Account_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        ip = Constant.URLProxy;
    }
    public void getUserDetails(Account_FragmentMVP.Presenter presenter,String user_id){
        String url = ip+"/hr_app/processes/get_user_profile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("cantfetch")){
                    presenter.getDetailFailed("Failed","Cant fetch data");

                }else if(response.equals("nodata")){
                    presenter.getDetailFailed("Failed","No data to be shown");

                }else{
                    presenter.getDetailFailed("Failed",response);
                   try {
                        JSONObject jsonObject = new JSONObject(response);
                        String firstname = jsonObject .getString("firstname");
                        String lastname = jsonObject .getString("lastname");
                        String middlename = jsonObject .getString("middlename");
                        String age = jsonObject .getString("age");
                        String mobile = jsonObject .getString("mobile");
                        String email= jsonObject .getString("email");
                        User user = new User();
                        user.setEmail(email);
                        user.setFirstname(firstname);
                        user.setLastname(lastname);
                        user.setMiddlename(middlename);
                        user.setAge(age);
                        user.setMobile(mobile);
                        presenter.getDetailSuccess(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.getDetailFailed("Failed",""+error);
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
}
