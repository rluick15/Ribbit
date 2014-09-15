package com.richluick.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.PushService;
import com.richluick.ribbit.ui.MainActivity;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "8dyPKcpo2bs76SkkNb2EDlQumzjJx88xrPaSywxc", "m2P4RvOl7oMEEk9rnOFQKwTuo9E0q9I98Y8LyRzs");

        PushService.setDefaultPushCallback(this, MainActivity.class);
    }


}
