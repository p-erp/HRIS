package com.example.hr_payroll.main_navigation.fragments.home_fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.hr_payroll.R;
import com.example.hr_payroll.main_navigation.fragments.home_fragment.adapter.Attendance_Adapter;
import com.example.hr_payroll.manager.AttendanceManager;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class Home_Fragment extends Fragment implements Home_FragmentMVP.Fragment {

    private Functions functions;
    private AttendanceManager attendanceManager;
    private AppSharedPref appSharedPref;

    /* */
    private String TAG = "";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback locationCallback;
    private LatLng currentLocation;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    private Home_Fragment_Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        functions = new Functions(getActivity());
        attendanceManager = new AttendanceManager(getActivity());
        appSharedPref = new AppSharedPref(getActivity());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mSettingsClient = LocationServices.getSettingsClient(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.main_navigation_home_fragment, container, false);
    }
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Attendance_Adapter attendance_adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter  = new Home_Fragment_Presenter(this,getActivity());
        recyclerView = view.findViewById(R.id.recycleview);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        attendance_adapter = new Attendance_Adapter(getActivity());
        recyclerView.setAdapter(attendance_adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                presenter.getUserAttendace(appSharedPref.getUserInfo(),""+(month+1),""+day,""+year);

            }
        });
    }
    @BindView(R.id.btn_attendance)
    Button btn_attendace;
    @OnClick(R.id.btn_attendance)
    void attendanceClick() {

        if(currentLocation!=null) {
            TimeIn();
         }else{
            Toast.makeText(getActivity(), "Dont have", Toast.LENGTH_SHORT).show();
        }

    }
    private void TimeIn(){

        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        Attendance attendance = new Attendance();
        attendance.setKey(timeStamp);

        attendance.setLocation_in_lat(""+currentLocation.latitude);
        attendance.setLocation_in_long(""+currentLocation.longitude);
        attendance.setUser_id(appSharedPref.getUserInfo());

        attendance.setLocation_out_long(""+currentLocation.longitude);
        attendance.setLocation_out_lat(""+currentLocation.latitude);
        presenter.onAttendance(attendance);

}


    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        if(mFusedLocationClient!=null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }

    }
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

              Log.d(TAG, "== Error On onConnected() Permission not granted");
             return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }

    @Override
    public void onResume() {
        super.onResume();
        startStep1();


        presenter.getUserData(appSharedPref.getUserInfo());
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        presenter.getUserAttendace(appSharedPref.getUserInfo(),""+(month+1),""+day,""+year);
    }
    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {
    if (isGooglePlayServicesAvailable()) {

           if (checkPermissions()) {

               createLocationCallback();
               createLocationRequest();
               buildLocationSettingsRequest();
               startLocationUpdates();

            } else {
               requestPermissions();
            }


        } else {
            Toast.makeText(getActivity(),"no google services", Toast.LENGTH_LONG).show();
        }
    }
    private LocationCallback mLocationCallback;
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                currentLocation = new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude());
                //    updateLocationUI();
           /*     if(mRequestingLocationUpdates){
                    updateLocationUI();
                }
*/
            }
        };

    }
    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(getActivity(), status, 2404).show();
            }
            return false;
        }
        return true;
    }
    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {

        Snackbar.make(
                getActivity().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onAttendanceClick() {

    }

    @Override
    public void onAttendanceTimedIn() {
        btn_attendace.setBackgroundResource(R.drawable.circular_btn_timeout);
        btn_attendace.setText("Time Out");
    }

    @Override
    public void onAttendanceTimedOut() {
        btn_attendace.setBackgroundResource(R.drawable.circular_btn);
        btn_attendace.setText("Time In");
    }

    @Override
    public void showMessage(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void hasTimeIn(boolean isNo) {



        //Toast.makeText(getActivity(), ""+isNo, Toast.LENGTH_SHORT).show();

        if(isNo){
            btn_attendace.setBackgroundResource(R.drawable.circular_btn_timeout);
            btn_attendace.setText("Time Out");
        }else{
            btn_attendace.setBackgroundResource(R.drawable.circular_btn);
            btn_attendace.setText("Time In");

        }
    }

    @Override
    public void attendanceList(ArrayList<Attendance> attendanceArrayList) {
        attendance_adapter.SetData(attendanceArrayList);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }






    /*private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService) {

            Log.i(TAG,"started");

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }*/

    /*@BindView(R.id.btn_attendance)
    Button btn_attendace;
    @OnClick(R.id.btn_attendance)
    void onAttendanceClick() {
        Toast.makeText(getActivity(), ""+currentLocation, Toast.LENGTH_SHORT).show();
        if(currentLocation!=null){

            ArrayList<Attendance> attendances = Lists.newArrayList(attendanceManager.GetAttendace().iterator());
            for(int i = 0 ; i < attendances.size();i++){
                Attendance attendance = attendances.get(i);
                if(attendance.getTime_out() == null){
                    Toast.makeText(getActivity(), "There is", Toast.LENGTH_SHORT).show();
                    btn_attendace.setBackgroundColor(Color.parseColor("#008577"));

                }
            }

            Calendar currnetDateTime = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
            String  currentTime = df.format(currnetDateTime.getTime());
            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            Attendance attendance = new Attendance();
            attendance.setLocation_in_lat(""+currentLocation.latitude);
            attendance.setLocation_in_long(""+currentLocation.longitude);
            attendance.setUser_id(appSharedPref.getUserInfo());
            attendance.setTime_in(currentTime);
            attendance.setDay(""+day);
            attendance.setYear(""+year);
            attendance.setMonth(""+month);
         *//*   if(attendanceManager.Attendance(attendance)){
                functions.showMessage("Success","Timed in");
                btn_attendace.setBackgroundColor(Color.parseColor(String.valueOf(R.color.colorPrimary)));
            }else{
                functions.showMessage("Failed","Timed in");

            };*//*


        }else{
            if(currentLocation==null) {
                functions.showMessage("Failed","Cant get Location");
            }else if(!mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .isSuccessful()){
                createLocationRequest();
                buildLocationSettingsRequest();
                startLocationUpdates();
                buildLocationCallBack();
            }

        }
    }

    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        //   mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());

                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                functions.showMessage("Error", errorMessage);

                        }

                        //    updateUI();
                    }
                });
    }




    @Override
    public void onResume() {
        super.onResume();
*//*

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mSettingsClient = LocationServices.getSettingsClient(getActivity());
        createLocationRequest();
        buildLocationSettingsRequest();
        startLocationUpdates();
        buildLocationCallBack();
        ArrayList<Attendance> attendances = Lists.newArrayList(attendanceManager.GetAttendace().iterator());
        for(int i = 0 ; i < attendances.size();i++){
            Attendance attendance = attendances.get(i);
            if(attendance.getTime_out() == null){
                Toast.makeText(getActivity(), "There is", Toast.LENGTH_SHORT).show();
                btn_attendace.setBackgroundColor(Color.parseColor("#008577"));
            }
        }
*//*

    }

    @Override
    public void onPause() {
        super.onPause();
        //mFusedLocationClient.removeLocationUpdates(locationCallback);


    }



    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private String TAG = "";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private LocationCallback locationCallback;
    private LatLng currentLocation;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        *//*
            try
        {

        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        Date date1 = format.parse("08:00:12 pm");
        Date date2 = format.parse("05:30:12 pm");
        long mills = date1.getTime() - date2.getTime();
        Log.v("Data1", ""+date1.getTime());
        Log.v("Data2", ""+date2.getTime());
        int hours = (int) (mills/(1000 * 60 * 60));
        int mins = (int) (mills/(1000*60)) % 60;
            Toast.makeText(getActivity(), "Hours "+hours+" mins :"+mins, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*//*

    }


    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for(Location location:locationResult.getLocations()){
                    currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                }
            }
        };
    }

*/
}
