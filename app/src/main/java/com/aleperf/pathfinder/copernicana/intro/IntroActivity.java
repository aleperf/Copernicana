package com.aleperf.pathfinder.copernicana.intro;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.aleperf.pathfinder.copernicana.CopernicanaApplication;
import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.apod.ApodActivity;
import com.aleperf.pathfinder.copernicana.database.UpdateService;
import com.aleperf.pathfinder.copernicana.epic.EpicActivity;
import com.aleperf.pathfinder.copernicana.iss.IssActivity;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IntroActivity extends AppCompatActivity implements SummaryFragment.SectionSelector {


    @BindView(R.id.toolbar_intro)
    Toolbar toolbar;
    boolean isDualPane;
    @Inject
    ViewModelProvider.Factory factory;
    private final static String UPDATE_SERVICE_JOB_TAG = "com.aleperf.pathfinder.copernicana.UPDATE_SERVICE";
    private final static int SIX_HOURS = 21600;
    private final static int SIX_HOURS_AND_A_MINUTE = 21660;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        isDualPane =  getResources().getBoolean(R.bool.is_dual_pane);
        ((CopernicanaApplication) this.getApplication()).getCopernicanaApplicationComponent().inject(this);
        IntroViewModel viewModel = ViewModelProviders.of(this, factory).get(IntroViewModel.class);
        viewModel.initializeRepository();
        scheduleUpdateJob();
    }


    private void scheduleUpdateJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this.getApplicationContext()));
        Job updateJob = dispatcher.newJobBuilder()
                .setService(UpdateService.class)
                .setTag(UPDATE_SERVICE_JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(SIX_HOURS, SIX_HOURS_AND_A_MINUTE))
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .build();
        dispatcher.mustSchedule(updateJob);
    }


    @Override
    public void selectSection(int position) {
        if (position == 0) {
            Intent intent = new Intent(this, ApodActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, EpicActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, IssActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.intro_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(IntroActivity.this, SignInActivity.class));
                                finish();
                            }
                        });

                return true;
            case R.id.info_activity:
                if(!isDualPane){
                    Intent intent = new Intent(this, IntroInfoActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
