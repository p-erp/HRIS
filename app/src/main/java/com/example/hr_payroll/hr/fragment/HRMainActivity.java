package com.example.hr_payroll.hr.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hr_payroll.R;
import com.example.hr_payroll.company.CompanyMainActivity;
import com.example.hr_payroll.hr.fragment.employees.Employees_Fragment;
import com.example.hr_payroll.hr.fragment.overtime.Overtime_Fragment;
import com.example.hr_payroll.hr.fragment.salarycutoff.SalaryCutOff_Fragment;
import com.example.hr_payroll.hr.fragment.workday.WorkDay_Fragment;
import com.example.hr_payroll.main.MainActivityView;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Constant;
import com.example.hr_payroll.utilities.Functions;
import com.example.hr_payroll.utilities.ProxyHurlStack;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class HRMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HRMainActivityMVP.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private HRMainActivity_Presenter presenter;
    private FragmentManager fragmentManager;
    private AppSharedPref appSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrmain);
        ButterKnife.bind(this);
        appSharedPref = new AppSharedPref(this);
        presenter = new HRMainActivity_Presenter(this);
        if(savedInstanceState==null){
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
        getMenuInflater().inflate(R.menu.hrmain, menu);
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

        if (id == R.id.nav_workday) {
            toolbar.setTitle("WorkDays");
            changeFragment(workDay_fragment);
        } else if (id == R.id.nav_overtime) {
            toolbar.setTitle("Overtime");
            changeFragment(overtime_fragment);
        } else if (id == R.id.nav_cutoff) {
            toolbar.setTitle("Cut Off");
            changeFragment(salaryCutOff_fragment);
        } else if (id == R.id.nav_employees) {
            toolbar.setTitle("Employees");
            changeFragment(employees_fragment);
        } else if (id == R.id.nav_logout) {
            appSharedPref.clearData();
            startActivity(new Intent(HRMainActivity.this, MainActivityView.class));
            finish();
            changeFragment(employees_fragment);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager.
                beginTransaction().
                replace(R.id.container,fragment)
                .commit();
    }
    private SalaryCutOff_Fragment salaryCutOff_fragment;
    private WorkDay_Fragment workDay_fragment;
    private com.example.hr_payroll.hr.fragment.overtime.Overtime_Fragment overtime_fragment;
    private Employees_Fragment employees_fragment;
    @Override
    public void initFragment() {
        salaryCutOff_fragment = new SalaryCutOff_Fragment();
        workDay_fragment = new WorkDay_Fragment();
        overtime_fragment = new Overtime_Fragment();
        employees_fragment = new Employees_Fragment();
    }

    @Override
    public void initUI() {
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
}
