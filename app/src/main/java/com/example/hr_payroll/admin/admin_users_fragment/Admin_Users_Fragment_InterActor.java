package com.example.hr_payroll.admin.admin_users_fragment;

import android.content.Context;

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

import java.util.ArrayList;

public class Admin_Users_Fragment_InterActor {
    private RequestQueue requestQueue;
    private ArrayList<User> users;
    public Admin_Users_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        users = new ArrayList<>();
    }
    public void GetUsers(Admin_Users_Fragment_Presenter presenter){
        String url = Constant.URLProxy+"/hr_app/processes/admin_get_users.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("nodata")){

                    presenter.onGetUsersFailed("Failed","No data");
                }else if(response.equals("failed")){

                    presenter.onGetUsersFailed("Failed","Cant Fetch Data");
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        users.clear();
                        for(int i = 0 ; i < jsonArray.length();i++){
                            JSONObject jsonRow = jsonArray.getJSONObject(i);
                            User user = new User();
                            user.setKey(jsonRow.getString("id"));
                            user.setFirstname(jsonRow.getString("firstname"));
                            user.setLastname(jsonRow.getString("lastname"));
                            user.setMiddlename(jsonRow.getString("middlename"));
                            user.setAge(jsonRow.getString("age"));
                            user.setRole(jsonRow.getString("role"));
                            user.setTimestamp(jsonRow.getString("joined"));
                            user.setEmail(jsonRow.getString("email"));
                            user.setMobile(jsonRow.getString("mobile"));


                            users.add(user);

                        }
                        presenter.onGetUsersSuccess(users);
                    } catch (JSONException e) {
                        presenter.onGetUsersFailed("Failed",e.getMessage());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onGetUsersFailed("Failed",error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }
}
