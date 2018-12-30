package com.kroraina.easyreader;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.kroraina.easyreader.service.DownloadService;
import com.xincubate.lego.generate.LegoRegisterUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        startService(new Intent(this, DownloadService.class));

        LegoRegisterUtils.init();
    }
}