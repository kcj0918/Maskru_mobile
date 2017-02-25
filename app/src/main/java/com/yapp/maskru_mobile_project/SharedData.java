package com.yapp.maskru_mobile_project;

import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by 영준 on 2017-02-25.
 */

public class SharedData {
    public static SharedData getInstance(Context context){
        mContext = context;
        return sharedData;
    }

    static {
        sharedData = new SharedData();
    }

    private static SharedData sharedData;
    static Context mContext;

    private SharedData() {}

    // 값 불러오기
    public String getPreferences(String key, String default_value){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getString(key, default_value);
    }

    // 값 저장하기
    public void savePreferences(String key, String data){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, data);
        editor.commit();
    }
}
