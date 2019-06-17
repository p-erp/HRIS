package com.example.hr_payroll.main_navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.hr_payroll.R;
import com.example.hr_payroll.main.MainActivityView;
import com.example.hr_payroll.main_navigation.fragments.account_fragment.Account_Fragment;
import com.example.hr_payroll.main_navigation.fragments.attendance_fragment.Attendance_Fragment;
import com.example.hr_payroll.main_navigation.fragments.expense_fragment.Expense_Fragment;
import com.example.hr_payroll.main_navigation.fragments.home_fragment.Home_Fragment;
import com.example.hr_payroll.main_navigation.fragments.offset.Offset_Fragment;
import com.example.hr_payroll.main_navigation.fragments.overtime_fragment.Overtime_Fragment;
import com.example.hr_payroll.main_navigation.fragments.payroll_fragment.Payroll_Fragment;
import com.example.hr_payroll.main_navigation.fragments.resignation.Resignation_Fragment;
import com.example.hr_payroll.signin.SignInView;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.TinyDB;

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
import android.widget.TextView;
import android.widget.Toast;

public class MainNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainNavigationMVP.View {

    private TinyDB tinyDB;
    private MainNavigationPresenter mainNavigationPresenter;
    private FragmentManager fragmentManager;
    //
    private Account_Fragment account_fragment;
    private Attendance_Fragment attendance_fragment;
    private Overtime_Fragment overtime_fragment;
    private Resignation_Fragment resignation_fragment;
    private Expense_Fragment expense_fragment;
    private Payroll_Fragment payroll_fragment;
    private Offset_Fragment offset_fragment;

    private Home_Fragment home_fragment;
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
        setContentView(R.layout.activity_main_navigation);
        ButterKnife.bind(this);
        mainNavigationPresenter = new MainNavigationPresenter(this);
        if(savedInstanceState==null){
            toolbar.setTitle("Home");
            changeFragment(home_fragment);
        }
    }

    @Override
    public void initUI() {
        appSharedPref = new AppSharedPref(this);
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);

        if(appSharedPref.getUserInfo().equals("")){
            startActivity(new Intent(this, SignInView.class));
            finishAffinity();
        }
    }

    @Override
    public void initFragment() {
        account_fragment = new Account_Fragment();
        attendance_fragment = new Attendance_Fragment();
        home_fragment = new Home_Fragment();
        overtime_fragment = new Overtime_Fragment();
        resignation_fragment = new Resignation_Fragment();
        expense_fragment = new Expense_Fragment();
        payroll_fragment = new Payroll_Fragment();
        offset_fragment = new Offset_Fragment();
    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager.
                beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
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
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("Home");
            changeFragment(home_fragment);
        } else if (id == R.id.nav_account) {
            toolbar.setTitle("Account");
            changeFragment(account_fragment);
        } else if (id == R.id.nav_attendance) {
            toolbar.setTitle("Attendance");
            changeFragment(attendance_fragment);
        }else if(id == R.id.nav_overtime) {
            toolbar.setTitle("Overtime");
            changeFragment(overtime_fragment);
        }else if(id == R.id.nav_expense) {
            toolbar.setTitle("Expense");
            changeFragment(expense_fragment);
        }else if(id == R.id.nav_offset) {
            toolbar.setTitle("Offset");
            changeFragment(offset_fragment);
        } else if (id == R.id.nav_logout) {
            appSharedPref.clearData();
            startActivity(new Intent(this,MainActivityView.class));
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
