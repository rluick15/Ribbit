package com.richluick.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

//This activity is called if the user selects that they have forgotten their password
//User can reset their password from this screen
public class ForgotPasswordActivity extends Activity {

    protected EditText mEmail;
    protected Button mResetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_forgot_password);

        mEmail = (EditText) findViewById(R.id.emailField);
        mResetButton = (Button) findViewById(R.id.resetButton);

        //User clicks button to reset email
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                email = email.trim();

                if (email.isEmpty()) {
                    //Checks if the user left a field blank and displays an alert message
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                    builder.setTitle(getString(R.string.signup_error_title))
                            .setMessage(getString(R.string.forgot_password_error_message))
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    //If email is valid, parse.com begins resetting email process
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser.requestPasswordResetInBackground(email,new RequestPasswordResetCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                setProgressBarIndeterminateVisibility(false);

                                //valid email was typed. Email was successfully sent to user for password reset
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                builder.setTitle(getString(R.string.forgot_password_success_title))
                                        .setMessage(getString(R.string.forgot_password_success_message))
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //Now send the user back to the Login Screen
                                                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else {
                                setProgressBarIndeterminateVisibility(false);

                                //error with email(invalid email), error message is displayed
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                builder.setTitle(R.string.login_error_title)
                                        .setMessage(e.getMessage())
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.forgot_password, menu);
//        return true;
//    }
}
