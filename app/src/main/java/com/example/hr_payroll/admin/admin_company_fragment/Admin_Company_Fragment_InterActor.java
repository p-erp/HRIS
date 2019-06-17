package com.example.hr_payroll.admin.admin_company_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.Company;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Admin_Company_Fragment_InterActor {

    private RequestQueue requestQueue;
    private ArrayList<Company> companies;
    private Context context;
    public Admin_Company_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        companies = new ArrayList<>();
        this.context = context;
    }
    public void GetCompanies(Admin_Company_Fragment_Presenter presenter){
        String url  = Constant.URLProxy+"/hr_app/processes/admin_get_companies.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("nodata")){
                    presenter.onGetCompaniesFailed("Failed","No data");
                }else if(response.equals("failed")){
                    presenter.onGetCompaniesFailed("Failed","Cant Fet Data");
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        companies.clear();
                        for(int i = 0 ; i < jsonArray.length() ;i++){
                            JSONObject jsonRow = jsonArray.getJSONObject(i);
                            Company company = new Company();
                            company.setKey(jsonRow.getString("company_id"));
                            company.setAddress(jsonRow.getString("address"));
                            company.setEmail(jsonRow.getString("email"));
                            company.setLandline(jsonRow.getString("landline"));
                            company.setMobile(jsonRow.getString("mobile"));
                            company.setName(jsonRow.getString("name"));
                            company.setImage(jsonRow.getString("image"));
                            companies.add(company);

                        }
                        presenter.onGetCompaniesSuccess(companies);
                    } catch (JSONException e) {
                        presenter.onGetCompaniesFailed("Failed",e.getMessage());

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onGetCompaniesFailed("Failed",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);

    }
    public void SaveCompany(Admin_Company_Fragment_Presenter presenter,Company company){
        String url = Constant.URLProxy+"/hr_app/processes/admin_save_company.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("exist")){
                    presenter.onSaveCompanyFailed("Failed","Company already exist");
                }else if(response.equals("success")){
                    presenter.onSaveCompanyFailed("Success","Company has been saved");
                }else if(response.equals("failed")){
                    presenter.onSaveCompanyFailed("Failed","Saving company failed");
                }else{
                    presenter.onSaveCompanyFailed("Failed",response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                presenter.onSaveCompanyFailed("Failed",error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("name",company.getName());
                hashMap.put("address",company.getAddress());
                hashMap.put("password",company.getPassword());
                hashMap.put("mobile",company.getMobile());
                hashMap.put("landline",company.getLandline());
                hashMap.put("image",company.getImage());
                hashMap.put("email",company.getEmail());

                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }

}
