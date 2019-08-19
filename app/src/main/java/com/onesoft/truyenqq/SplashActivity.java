package com.onesoft.truyenqq;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.github.silvestrpredko.dotprogressbar.DotProgressBarBuilder;

import network.InternetConnection;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends Activity {
    private final int REQUEST_PERMISSION = 1;
    private static final int SPLASH_DURATION = 2000; // 3 seconds

    //PLEASE DON'T TOUCH ANYTHING! THIS HAS BEEN OPTIMIZED FOR THE BEST RUN!

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //dot progress dislog
        new DotProgressBarBuilder(this)
                .setDotAmount(4)
                .setStartColor(Color.BLACK)
                .setAnimationDirection(DotProgressBar.LEFT_DIRECTION)
                .build();

        //create SharePref for the notice confirm
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.contains("FirstTime")) {
            //show welcome dislog once
            new AlertDialog.Builder(this)
                    .setMessage(R.string.notice)
                    .show();

            //write in sharedpref
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FirstTime", true);
            editor.commit();
        }

        //check prefs than run the request permission for checking network and permission
        requestPermission();

    }

    // get values of permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_PERMISSION)
    public void requestPermission() {
        //check all permission & internet connection
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @SuppressLint("ResourceType")
                @Override
                public void run() {
                    //if all thing's good -> let it roll~!
                    if (InternetConnection.checkConnection(getBaseContext())) {
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else {
                        //if there's no internet connection -> you're sucks! I'll give you some toast for a warn
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast,
                                (ViewGroup) findViewById(R.id.custom_toast_container));

                        TextView text = layout.findViewById(R.id.text);
                        ImageView img = layout.findViewById(R.id.imgToast);
                        img.setImageResource(R.raw.no_internet);
                        text.setText(R.string.no_internet);

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 0, 180);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }

                }
            }, SPLASH_DURATION);
        } else {
            EasyPermissions.requestPermissions(this, "Please Grant Permission!", REQUEST_PERMISSION, perms);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}