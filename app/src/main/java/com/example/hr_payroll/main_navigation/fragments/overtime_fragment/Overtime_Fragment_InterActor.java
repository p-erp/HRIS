package com.example.hr_payroll.main_navigation.fragments.overtime_fragment;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;

import java.util.HashMap;
import java.util.Map;

public class Overtime_Fragment_InterActor {
    private  RequestQueue requestQueue;
    public Overtime_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());

    }

}
