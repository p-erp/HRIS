package com.example.hr_payroll.company.company_holiday_fragment;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.utilities.ProxyHurlStack;

public class Company_Holiday_Fragment_InterActor {
    private RequestQueue requestQueue;
    public Company_Holiday_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
    }
}
