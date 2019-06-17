package com.example.hr_payroll.manager;

import android.content.Context;

import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
public class AttendanceManager {
    private Context context;
    private AppSharedPref appSharedPref;
    private Realm realm;
    public AttendanceManager(Context context){
        this.context = context;
        appSharedPref = new AppSharedPref(context);
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        configuration.shouldDeleteRealmIfMigrationNeeded();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();
    }
    private boolean isSuccess = false;
    public boolean Attendance(Attendance attendance){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    isSuccess = true;
                    realm.copyToRealmOrUpdate(attendance);

                }catch (Exception e){
                    isSuccess = false;
                }

            }
        });
        return  isSuccess;

    }
    public void UpdateAttendace(String timeout, String lat,String lang){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                   /* RealmResults<Attendance> attendanceOld =realm.where(Attendance.class)
                            .equalTo("user_id",appSharedPref.getUserInfo()).findAll();
                    ArrayList<Attendance> attendances = Lists.newArrayList(attendanceOld.iterator());
                    for(int i = 0 ; i < attendances.size();i++){
                        Attendance attend = attendances.get(i);
                        if(attend.getTime_out() == null){
                            Attendance attendanceUpdate = realm.where(Attendance.class).equalTo("id",attend.getKey()).findFirst();
                            attendanceUpdate.setTime_out(timeout);
                            attendanceUpdate.setLocation_out_lat(lat);
                            attendanceUpdate.setLocation_out_long(lang);
                            realm.copyToRealmOrUpdate(attendanceUpdate);
                            return;
                        }
                    }*/
                }
            });
    }
    public void SetAttendace(ArrayList<Attendance> attendanceArrayList){
        ArrayList<Attendance> attendances = new ArrayList<>();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(attendances);
            }
        });
    }
    public RealmResults<Attendance> GetAttendace(){
        return realm.where(Attendance.class).equalTo("user_id",appSharedPref.getUserInfo())
                .findAll();
    }
    public RealmResults<Attendance> GetAttendace(String day,String month,String year){
        return realm.where(Attendance.class).equalTo("user_id",appSharedPref.getUserInfo())
                .and().equalTo("month_in",month)
                .and().equalTo("day_in",day)
                .and().equalTo("year_in",year)
                .findAll();
    }
}
