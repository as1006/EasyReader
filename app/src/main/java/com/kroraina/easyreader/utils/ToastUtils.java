package com.kroraina.easyreader.utils;

import android.widget.Toast;

import com.kroraina.easyreader.App;



public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
