package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.kobakei.ratethisapp.RateThisApp;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_new:
                    return true;
                case R.id.navigation_categories:
                    return true;
                case R.id.navigation_favorite:
                    return true;
                case R.id.navigation_user:
                    return true;
            }
            return false;
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack();
            transaction.replace(R.id.fragment, fragment);
            transaction.addToBackStack(null);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            transaction.commit();
            return true;
        }
        return false;
    }

    private void rateMe(){
        //config RateThisApp
        RateThisApp.Config config = new RateThisApp.Config(1, 1);
        RateThisApp.init(config);

        config = new RateThisApp.Config();
        config.setTitle(R.string.rate_1);
        config.setMessage(R.string.rate_2);
        config.setYesButtonText(R.string.rate_yes);
        config.setNoButtonText(R.string.rate_no);
        config.setCancelButtonText(R.string.rate_later);
        RateThisApp.init(config);

        //Create & check if needed show
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void onStart() {
        rateMe();
        super.onStart();
    }

    @Override
    protected void onResume() {
        rateMe();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        moveTaskToBack(true);
        finish();
        super.onDestroy();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.raw.no_internet)
                .setTitle(R.string.app_name)
                .setMessage(R.string.exit_app)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
