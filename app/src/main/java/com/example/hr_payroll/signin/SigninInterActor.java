package com.example.hr_payroll.signin;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.ProxyHurlStack;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninInterActor {
    private Context context;
    private RequestQueue requestQueue;
    public SigninInterActor(Context context){
        this.context = context;
        requestQueue  = Volley.newRequestQueue(context,new ProxyHurlStack());

    }
    public void logInProcess(SignInMVP.Presenter presenter, User user){
        String url = "http://192.168.12.43/hr_app/processes/signin_process.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("wrong_pass")) {
                    presenter.onLoginError("Failed","Wrong password");
                } else if (response.equals("not_registered")) {
                    presenter.onLoginError("Failed","We dont recognized this account");
                } else if (response.equals("email_notvalid")) {
                    presenter.onLoginError("Failed","Invalid email");
                }else{

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String role = jsonObject .getString("role");
                        String id = jsonObject .getString("id");
                        String email = jsonObject .getString("email" +
                                "");
                        User userData = new User();
                        userData.setKey(id);
                        userData.setEmail(email);
                        userData.setRole(role);
                        presenter.onLoginSuccess(userData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email",user.getEmail());
                hashMap.put("password",user.getPassword());
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);

    }
}
