package com.example.hr_payroll.signup;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.hr_payroll.model.User;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUpInterActor {
    private Context context;
    private RequestQueue requestQueue;
    public SignUpInterActor(Context context){
        this.context = context;
        requestQueue=Volley.newRequestQueue(context,new ProxyHurlStack());

    }
    public void SignUp(SignUpPresenter presenter, User user){
        String url = Constant.URLProxy+"/hr_app/processes/signup_process.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        presenter.onSignUpFailed("Qwer",response);
                        switch (response){

                            case "email_used":
                                presenter.onSignUpFailed("Failed","Email already used");
                                break;
                            case "contact_used":
                                presenter.onSignUpFailed("Failed","Contact number already used");

                                break;
                            case "signup_success":
                                presenter.onSignUpFailed("Failed","Sing up success");

                                break;
                            case "signup_failed":
                                presenter.onSignUpFailed("Failed","Sign up failed");

                                break;
                            case "email_notvalid":
                                presenter.onSignUpFailed("Failed","Invalid Email");
                                break;
                            case "contact_notvalid":
                                presenter.onSignUpFailed("Failed","Invalid contact");
                                break;


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.onSignUpFailed("Failed",""+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                hashMap.put("email",user.getEmail());
                hashMap.put("contact",user.getMobile());
                hashMap.put("password",user.getPassword());
                hashMap.put("joined",timeStamp);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}
