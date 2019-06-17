package com.example.hr_payroll.main_navigation.fragments.offset;

import com.example.hr_payroll.model.Offset;

import java.util.ArrayList;
import java.util.HashMap;

public interface Offset_FragmentMVP {
    interface Fragment{
        void SaveOffSetSuccess(String title,String message);
        void SaveOffSetFailed(String title,String message);
        void GetOffSetSuccess(ArrayList<Offset> offsets);
        void GetOffSetFailed(String title,String message);

    }
    interface Presenter{
        void onSaveOffSet(HashMap startOffSet, HashMap newOffset, String user_id);
        void onSaveOffSetSuccess(String title,String message);
        void onSaveOffSetFailed(String title,String message);
        void onGetOffSet(String user_id);
        void onGetOffSetSuccess(ArrayList<Offset> offsets);
        void onGetOffSetFailed(String title,String message);


    }
}
