package com.example.android_project;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedPreference {
    public SharedPreferences SP;
    private Context context;


    public sharedPreference(Context context){
        this.context=context;
        SP=context.getSharedPreferences("Save",Context.MODE_PRIVATE);
    }

    public void writeLoginState(String user,String type,boolean state){
        SharedPreferences.Editor SPE=SP.edit();
        SPE.putString("user",user);
        SPE.putString("type",type);
        SPE.putBoolean("state",state);
        SPE.commit();
        SPE.apply();

    }
    public boolean readLoginState(){
        boolean status =false;
        status=SP.getBoolean("state",false);
        return status;
    }
    public String readLoginType(){
        String state =null;
        return  SP.getString("type","");

    }


}