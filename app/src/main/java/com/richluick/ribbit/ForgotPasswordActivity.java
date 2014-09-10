package com.richluick.ribbit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

//This activity is called if the user selects that they have forgotten their password
//User can reset their password from this screen
public class ForgotPasswordActivity extends Activity {

    protected EditText mEmail;
    protected Button mResetButton;
    protected Button mCancelButton;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_forgot_password);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar); //Get progress bar
        mProgressBar.setVisibility(View.INVISIBLE);

        //Hide the action bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        //Returns to Login Screen
        mCancelButton = (Button) findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mEmail = (EditText) findViewById(R.id.emailField);
        mResetButton = (Button) findViewById(R.id.resetButton);

        //User clicks button to reset email
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                String email = mEmail.getText().toString();
                email = email.trim();

                isEmailValid(email); //checks if the email is valid/not empty
            }
        });
    }

    private void isEmailValid(String email) {
        mProgressBar.setVisibility(View.INVISIBLE);

        if (email.isEmpty()) {
            emptyEmailFieldMessage(); //Checks if the user left a field blank and displays an alert message
        }
        else {
            //If email is valid, parse.com begins resetting email process
            setProgressBarIndeterminateVisibility(true);
            resetPassword(email); //if email field isn't empty, it gets reset
        }
    }

    //This method resets the password or displays and error if there is an exception
    private void resetPassword(String email) {
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    setProgressBarIndeterminateVisibility(false);
                    validEmailMessage(); //valid email is entered. reset email is sent and success message displayed
                }
                else {
                    setProgressBarIndeterminateVisibility(false);
                    emailExceptionMethod(e); //exception thrown, error message is displayed
                }
            }
        });
    }

    //This displays a message if an exception is thrown when the user clicks the reset email button(i.e. invalid email)
    private void emailExceptionMethod(ParseException e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.login_error_title)
                .setMessage(e.getMessage())
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //valid email was typed. Email was successfully sent to user for password reset.
    private void validEmailMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    //Error message if the email field is empty and the user clicks the reset button
    private void emptyEmailFieldMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.signup_error_title))
                .setMessage(getString(R.string.forgot_password_error_message))
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
