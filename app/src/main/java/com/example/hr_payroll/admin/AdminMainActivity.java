package com.example.hr_payroll.admin;

import android.content.Intent;
import android.os.Bundle;

import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.admin_company_fragment.Admin_Company_Fragment;
import com.example.hr_payroll.admin.admin_holidays_fragment.Admin_Holidays_Fragment;
import com.example.hr_payroll.admin.admin_users_fragment.Admin_Users_Fragment;
import com.example.hr_payroll.main.MainActivityView;
import com.example.hr_payroll.signin.SignInView;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
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

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdminMainActivityMVP.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private FragmentManager fragmentManager;
    @Override
    public void initUI() {
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    private AdminMainActivityPresenter presenter;
    private AppSharedPref appSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        ButterKnife.bind(this);
        appSharedPref = new AppSharedPref(this);
        presenter = new AdminMainActivityPresenter(this);
        if(savedInstanceState==null){
            getSupportActionBar().setTitle("Company");
            changeFragment(admin_company_fragment);
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
        getMenuInflater().inflate(R.menu.admin_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = fragmentManager.findFragmentById(R.id.container);
        f.onActivityResult(requestCode,resultCode,data);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_company:
                toolbar.setTitle("Company");
                changeFragment(admin_company_fragment);
                break;
            case R.id.nav_holiday:
                toolbar.setTitle("Holidays");
                changeFragment(holidays_fragment);
                break;
            case R.id.nav_user:
                toolbar.setTitle("User");
                changeFragment(admin_users_fragment);
                break;
            case R.id.nav_logout:
                appSharedPref.clearData();
                startActivity(new Intent(AdminMainActivity.this, MainActivityView.class));
                finish();
                break;



        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private Admin_Company_Fragment admin_company_fragment;
    private Admin_Holidays_Fragment holidays_fragment;
    private Admin_Users_Fragment admin_users_fragment;

    @Override
    public void initFragment() {
        admin_company_fragment = new Admin_Company_Fragment();
        holidays_fragment = new Admin_Holidays_Fragment();
        admin_users_fragment = new Admin_Users_Fragment();

    }

    @Override
    public void changeFragment(Fragment fragment) {
        fragmentManager.
                beginTransaction()
                .replace(R.id.container,fragment)
                .commit();
    }
}
