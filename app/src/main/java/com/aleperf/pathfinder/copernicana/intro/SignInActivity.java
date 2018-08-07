package com.aleperf.pathfinder.copernicana.intro;

import android.content.Intent;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {


    private final static String LOGIN_MESSAGE ="COPERNICANA LOGIN MESSAGE";

    @BindView(R.id.button_sign_up)
    Button buttonSignUp;
    @BindView(R.id.login_message)
    TextView loginMessage;
    @BindView(R.id.login_screen)
    ConstraintLayout loginScreen;
    private FirebaseAuth firebaseAuth;
    private static final int RC_SIGN_IN = 123;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            setUpNotLoggedStartScreen();
        } else {
            startIntroScreen();
        }
        if(savedInstanceState != null){
            message = savedInstanceState.getString(LOGIN_MESSAGE);
            if(message != null && !message.isEmpty()){
                loginMessage.setVisibility(View.VISIBLE);
                loginMessage.setText(message);
            }
        }

    }

    private void startIntroScreen() {
        Intent intent = new Intent(SignInActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }

    private void startSignUpActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build()))
                        .build(),
                RC_SIGN_IN);
    }


    private void setUpNotLoggedStartScreen() {
        buttonSignUp.setVisibility(View.VISIBLE);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                loginMessage.setVisibility(View.INVISIBLE);
                startIntroScreen();
            } else {
                // Sign in failed
                setUpNotLoggedStartScreen();
                if (response == null) {
                    // User pressed back button
                    loginMessage.setVisibility(View.VISIBLE);
                    message = getString(R.string.sign_in_prompt);
                    loginMessage.setText(message);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    loginMessage.setVisibility(View.VISIBLE);
                    message = getString(R.string.sign_in_no_connection);
                    loginMessage.setText(message);
                    return;
                }
                message = getString(R.string.sign_in_unknown_error);
                loginMessage.setVisibility(View.VISIBLE);
                loginMessage.setText(message);

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOGIN_MESSAGE, message);
    }
}
