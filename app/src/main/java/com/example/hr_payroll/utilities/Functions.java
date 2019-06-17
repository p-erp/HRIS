package com.example.hr_payroll.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.MatchesPattern;

public class Functions {
    private Context context;
    public Functions(Context context){
        this.context = context;
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }
    public String formatDate(String deliveryDate){
        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormatprev.parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        String changedDate = dateFormat.format(d);

        return changedDate;
    }
    public String reFormatTime(String time){
        if(time.length() == 1){
            if(time.equals("0")){
                time = "12";
            }else{
                time = "0"+time;

            }
        }
        return time;
    }

    public String reFormatTimeMin(String time){
        if(time.length() == 1){
            if(time.equals("0")){
                time = "00";
            }else{
                time = "0"+time;

            }
        }
        return time;
    }
    public boolean isEmpty(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString()) ? true : false;
    }
    public boolean isValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() ?true:false;
    }
    public boolean isMatch(String one,String two){
        return one.equals(two)?true:false;
    }
}
