package com.onesoft.truyenqq;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.User;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private Button _signup;
    private TextView _user, _pass,_re_pass, _name;
    private NetworkAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //register ServiceAPI and call getJSON from server
        api = ServiceAPI.createService(NetworkAPI.class);

        _user = findViewById(R.id.inputUser);
        _name = findViewById(R.id.inputName);
        _pass = findViewById(R.id.inputPass);
        _re_pass = findViewById(R.id.inputRePass);
        _signup = findViewById(R.id.btnSignUp);

        _signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }


    public void signup() {
        Log.d(TAG, "Signup");

        _signup.setEnabled(false);

        final ProgressDialog dialog = ProgressDialog.show(SignupActivity.this, "Creating account!",
                "Please wait...", true);
        dialog.show();

        // TODO: Implement your own signup logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        //validate input base on standard value
                        validate();

                        if(validate() == true){
                            //run on SignupSuccess
                            onSignupSuccess();
                        } else {
                            //run onSignupFailed
                            onSignupFailed();
                        }

                        dialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        final String user = _user.getText().toString();
        final String pass = _re_pass.getText().toString();
        final String name = _name.getText().toString();
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Call<List<model.Response>> call = api.register(user, pass, name, date);
//                call.enqueue(new Callback<List<Response>>() {
//                    @Override
//                    public void onResponse(Call<List<Response>> call, Response<List<Response>> response) {
//                        Toast.makeText(getBaseContext(), response.message(), Toa)
////                        if(response.isSuccessful()){
////                            list.addAll(response.body());
////                            mAdapter.notifyDataChanged();
////                        }else{
//////                    Log.e(TAG," Response Error "+ String.valueOf(response.code()));
////                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Response>> call, Throwable t) {
////                Log.e(TAG," Response Error "+ t.getMessage());
//                    }
//                });
//            }
//        });
////        Toast.makeText(getBaseContext(), "Signup Success!", Toast.LENGTH_SHORT).show();
////        Intent i = new Intent(getBaseContext(), MainActivity.class);
////        startActivity(i);

        setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup Failed!", Toast.LENGTH_LONG).show();

        _signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String user = _user.getText().toString();
        String pass = _pass.getText().toString();
        String name = _name.getText().toString();
        String re_pass = _re_pass.getText().toString();

        if (user.isEmpty()) {
            _user.setError("Username can't be empty!");
            valid = false;
        } else {
            _user.setError(null);
        }

        if (name.isEmpty()) {
            _name.setError("Your name can't be empty!");
            valid = false;
        } else {
            _name.setError(null);
        }
//        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            _email.setError("Email not valid!");
//            valid = false;
//        } else {
//            _email.setError(null);
//        }
//
//        if (email.isEmpty()) {
//            _email.setError("Email can't be empty!");
//            valid = false;
//        } else {
//            _email.setError(null);
//        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            _pass.setError("Password must be longer than 8 characters!");
            valid = false;
        } else {
            _pass.setError(null);
        }

        if (re_pass.isEmpty() || re_pass.length() < 4 || re_pass.length() > 10 || !(re_pass.equals(pass))) {
            _re_pass.setError("Re-enter password wrong!");
            valid = false;
        } else {
            _re_pass.setError(null);
        }
        return valid;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}