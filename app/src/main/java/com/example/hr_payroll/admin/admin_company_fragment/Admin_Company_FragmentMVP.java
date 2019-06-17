package com.example.hr_payroll.admin.admin_company_fragment;

import android.net.Uri;

import com.example.hr_payroll.model.Company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface  Admin_Company_FragmentMVP {
    interface Fragment{
        void GetCompaniesSuccess(ArrayList<Company> companies);
        void GetCompaniesFailed(String title,String message);
        void SaveCompanySuccess(String title,String message);
        void SaveCompanyFailed(String title,String message);
    }
    interface Presenter{
        void onSaveCompany(Company company);
        void onSaveCompanySuccess(String title,String message);
        void onSaveCompanyFailed(String title,String message);

        void onGetCompanies();
        void onGetCompaniesSuccess(ArrayList<Company> companies);
        void onGetCompaniesFailed(String title,String message);
    }
}
