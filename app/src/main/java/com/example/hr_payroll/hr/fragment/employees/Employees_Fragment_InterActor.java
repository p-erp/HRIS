package com.example.hr_payroll.hr.fragment.employees;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.model.Employee;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.ProxyHurlStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employees_Fragment_InterActor {
    private RequestQueue requestQueue;
    private ArrayList<Employee> employees;
    public Employees_Fragment_InterActor(Context context){
        requestQueue = Volley.newRequestQueue(context,new ProxyHurlStack());
        employees = new ArrayList<>();
    }
    public void GetEmployees(Employees_Fragment_Presenter presenter,String company_id){
        String url = Constant.URLProxy+"/hr_app/processes/get_employees.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("no_data")){
                    presenter.onGetEmployeesFailed("Failed","No Data");

                }else if(response.equals("failed")){
                    presenter.onGetEmployeesFailed("Failed","Cant fetch data");
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        employees.clear();
                        for(int i = 0 ; i < jsonArray.length();i++) {
                            JSONObject jsonRow = jsonArray.getJSONObject(i);
                            Employee employee = new Employee();
                            employee.setKey(jsonRow.getString("id"));
                            employee.setFirstname(jsonRow.getString("firstname"));
                            employee.setLastname(jsonRow.getString("lastname"));
                            employee.setMiddlename(jsonRow.getString("middlename"));
                            employee.setEmail(jsonRow.getString("email"));
                            employee.setAge(jsonRow.getString("age"));
                            employee.setRole(jsonRow.getString("role"));
                            employee.setMobile(jsonRow.getString("mobile"));

                            employees.add(employee);

                        }
                        presenter.onGetEmployeesSuccess(employees);
                    } catch (JSONException e) {
                        presenter.onGetEmployeesFailed("Failed",e.getMessage());

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                presenter.onGetEmployeesFailed("Failed",error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("company_id",company_id);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
