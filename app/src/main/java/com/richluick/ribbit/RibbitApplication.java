package com.richluick.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.richluick.ribbit.ui.MainActivity;
import com.richluick.ribbit.utils.ParseConstants;

public class RibbitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "8dyPKcpo2bs76SkkNb2EDlQumzjJx88xrPaSywxc", "m2P4RvOl7oMEEk9rnOFQKwTuo9E0q9I98Y8LyRzs");

        PushService.setDefaultPushCallback(this, MainActivity.class, R.drawable.ic_stat_ic_launcher);
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
        installation.saveInBackground();
    }
}
