package com.example.hr_payroll.admin.admin_company_fragment;

import android.content.Context;
import com.example.hr_payroll.model.Company;
import java.util.ArrayList;
public class Admin_Company_Fragment_Presenter implements Admin_Company_FragmentMVP.Presenter {
    private Admin_Company_FragmentMVP.Fragment fragment;
    private Admin_Company_Fragment_InterActor interActor;
    public Admin_Company_Fragment_Presenter(Admin_Company_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new Admin_Company_Fragment_InterActor(context);
    }

    @Override
    public void onSaveCompany(Company company) {
        interActor.SaveCompany(this,company);

    }

    @Override
    public void onSaveCompanySuccess(String title, String message) {
        fragment.SaveCompanySuccess(title,message);
    }

    @Override
    public void onSaveCompanyFailed(String title, String message) {
        fragment.SaveCompanyFailed(title,message);

    }

    @Override
    public void onGetCompanies() {
        interActor.GetCompanies(this);

    }

    @Override
    public void onGetCompaniesSuccess(ArrayList<Company> companies) {
        fragment.GetCompaniesSuccess(companies);
    }

    @Override
    public void onGetCompaniesFailed(String title, String message) {
        fragment.GetCompaniesFailed(title,message);
    }
}
