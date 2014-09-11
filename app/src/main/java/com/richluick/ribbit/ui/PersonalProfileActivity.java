package com.richluick.ribbit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import com.parse.ParseUser;
import com.richluick.ribbit.utils.ParseConstants;
import com.richluick.ribbit.R;


public class PersonalProfileActivity extends Activity {

    protected String mUsername;
    protected String mFirstName;
    protected String mLastName;
    protected String mFullName;
    protected String mEmail;
    protected String mHometown;
    protected String mWebsite;
    protected TextView mUsernameField;
    protected TextView mFullNameField;
    protected TextView mEmailField;
    protected TextView mHometownField;
    protected TextView mWebsiteField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        mUsernameField = (TextView) findViewById(R.id.usernameSpace);
        mFullNameField = (TextView) findViewById(R.id.fullNameSpace);
        mEmailField = (TextView) findViewById(R.id.emailSpace);
        mHometownField = (TextView) findViewById(R.id.hometownSpace);
        mWebsiteField = (TextView) findViewById(R.id.websiteSpace);

        ParseUser currentUser = ParseUser.getCurrentUser();

        mUsername = currentUser.getUsername();
        mFirstName = currentUser.get(ParseConstants.KEY_FIRST_NAME).toString();
        mLastName = currentUser.get(ParseConstants.KEY_LAST_NAME).toString();
        mFullName = mFirstName + " " + mLastName;
        mEmail = currentUser.getEmail();
        mHometown = currentUser.get(ParseConstants.KEY_HOMETOWN).toString();
        mWebsite = currentUser.get(ParseConstants.KEY_WEBSITE).toString();

        mUsernameField.setText(mUsername);
        mFullNameField.setText("Name: " + mFullName);
        mEmailField.setText("Email: " + mEmail);
        mHometownField.setText("Hometown: " + mHometown);
        mWebsiteField.setText("Website: " + mWebsite);
        Linkify.addLinks(mWebsiteField, Linkify.ALL);


    }

}
