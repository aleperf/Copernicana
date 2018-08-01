package com.aleperf.pathfinder.copernicana.intro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aleperf.pathfinder.copernicana.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.button_sign_up)
    Button buttonSignUp;
    @BindView(R.id.login_message)
    TextView loginMessage;
    @BindView(R.id.login_screen)
    ConstraintLayout loginScreen;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            setUpNotLoggedStartScreen();
        } else {
           startIntroScreen();
        }

        }

        private void startIntroScreen(){
            Intent intent = new Intent(SignInActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }

    private void startSignUpActivity(){
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



    private void setUpNotLoggedStartScreen(){
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
                startIntroScreen();
            } else {
                // Sign in failed
                setUpNotLoggedStartScreen();
                if (response == null) {
                    // User pressed back button
                    loginMessage.setVisibility(View.VISIBLE);
                    loginMessage.setText("You need to be signed up to start the app");
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    loginMessage.setVisibility(View.VISIBLE);
                    loginMessage.setText("No connection available");
                    return;
                }

                loginMessage.setVisibility(View.VISIBLE);
                loginMessage.setText("Unknown Sign-in error");
                Log.d("uffa", "Sign-in error: ", response.getError());
            }
        }
    }
}
