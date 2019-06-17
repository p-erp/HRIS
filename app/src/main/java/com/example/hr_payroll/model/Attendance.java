package com.example.hr_payroll.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Attendance extends RealmObject {


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMonth_in() {
        return month_in;
    }

    public void setMonth_in(String month_in) {
        this.month_in = month_in;
    }

    public String getDay_in() {
        return day_in;
    }

    public void setDay_in(String day_in) {
        this.day_in = day_in;
    }

    public String getYear_in() {
        return year_in;
    }

    public void setYear_in(String year_in) {
        this.year_in = year_in;
    }

    public String getMonth_out() {
        return month_out;
    }

    public void setMonth_out(String month_out) {
        this.month_out = month_out;
    }

    public String getDay_out() {
        return day_out;
    }

    public void setDay_out(String day_out) {
        this.day_out = day_out;
    }

    public String getYear_out() {
        return year_out;
    }

    public void setYear_out(String year_out) {
        this.year_out = year_out;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLocation_in_lat() {
        return location_in_lat;
    }

    public void setLocation_in_lat(String location_in_lat) {
        this.location_in_lat = location_in_lat;
    }

    public String getLocation_in_long() {
        return location_in_long;
    }

    public void setLocation_in_long(String location_in_long) {
        this.location_in_long = location_in_long;
    }

    public String getLocation_out_lat() {
        return location_out_lat;
    }

    public void setLocation_out_lat(String location_out_lat) {
        this.location_out_lat = location_out_lat;
    }

    public String getLocation_out_long() {
        return location_out_long;
    }

    public void setLocation_out_long(String location_out_long) {
        this.location_out_long = location_out_long;
    }

    @PrimaryKey
    private String key;
    private String month_in;
    private String day_in;
    private String year_in;

    private String month_out;
    private String day_out;
    private String year_out;

    private String time_in;
    private String time_out;
    private String user_id;

    private String location_in_lat;
    private String location_in_long;

    private String location_out_lat;
    private String location_out_long;

    public int getIs_overtime() {
        return is_overtime;
    }

    public void setIs_overtime(int is_overtime) {
        this.is_overtime = is_overtime;
    }

    private int is_overtime;







}
