package com.example.hr_payroll.company;

import android.content.Intent;
import android.os.Bundle;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.AdminMainActivity;
import com.example.hr_payroll.company.company_employees_fragment.Company_Employees_Fragment;
import com.example.hr_payroll.company.company_holiday_fragment.Company_Holiday_Fragment;
import com.example.hr_payroll.company.company_hr_fragment.Company_HR_Fragment;
import com.example.hr_payroll.company.company_sv_fragment.Company_SV_Fragment;
import com.example.hr_payroll.main.MainActivityView;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.Menu;
import android.view.MenuItem;

public class CompanyMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompanyMainActivityMVP.View {

    private Company_Employees_Fragment employees_fragment;
    private Company_Holiday_Fragment holiday_fragment;
    private Company_HR_Fragment company_hr_fragment;
    private Company_SV_Fragment company_sv_fragment;
    private FragmentManager fragmentManager;
    private CompanyMainActivity_Presenter presenter;
    private AppSharedPref appSharedPref;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
        presenter = new CompanyMainActivity_Presenter(this);
        appSharedPref = new AppSharedPref(this);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            getSupportActionBar().setTitle("Employees");
            changeFragment(employees_fragment);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_employees:
                toolbar.setTitle("Employees");
                changeFragment(employees_fragment);
                break;
            case R.id.nav_hr:
                toolbar.setTitle("Human Resource");
                changeFragment(company_hr_fragment);
                break;
            case R.id.nav_supervisor:
                toolbar.setTitle("Supervisor");
                changeFragment(company_sv_fragment);
                break;
            case R.id.nav_holiday:
                toolbar.setTitle("Holiday");
                changeFragment(holiday_fragment);
                break;
            case R.id.nav_logout:
                appSharedPref.clearData();
                startActivity(new Intent(CompanyMainActivity.this, MainActivityView.class));
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager.
                beginTransaction().
                replace(R.id.container,fragment)
                .commit();
    }

    @Override
    public void initFragment() {
        employees_fragment = new Company_Employees_Fragment();
        holiday_fragment = new Company_Holiday_Fragment();
        company_hr_fragment = new Company_HR_Fragment();
        company_sv_fragment = new Company_SV_Fragment();

    }

    @Override
    public void initUI() {
        fragmentManager = getSupportFragmentManager();
    }
}
