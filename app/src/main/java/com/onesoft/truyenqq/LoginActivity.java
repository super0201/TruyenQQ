package com.onesoft.truyenqq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import model.ServerResponse;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText etUser, etPass;
    Button btnLogin, btnSignUp;
    CheckBox ckbRemember;
    private NetworkAPI api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        //register ServiceAPI and call getJSON from server
        api = ServiceAPI.createService(NetworkAPI.class);

        btnLogin = findViewById(R.id.btnLogin);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        ckbRemember = findViewById(R.id.ckbRemember);
        btnSignUp = findViewById(R.id.btnSignUp);

        ckbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String user = etUser.getText().toString();
                String pass = etPass.getText().toString();

                if (buttonView.isChecked()) {
                    session.createLoginSession(pass, user);
                }
                else
                {
                    // not checked
                }
            }
        });

        //button clickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
        }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login(){
        final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Authenticating",
                "Wait a bit mate!", true);
        dialog.show();


        //authentication from server
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (validate()){
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        // onLoginFailed();
                        dialog.dismiss();
                    }
                }, 3000);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginSuccess() {
        final String user = etUser.getText().toString();
        final String pass = etPass.getText().toString();

        Call<ServerResponse> call = api.checkLogin(user, pass);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT);
                if(response.body().getResult() == 1){
                    Toast.makeText(getBaseContext(), "Login Lookin Good!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getBaseContext(), "You should get it done right mate!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG," Response Error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+ t.getMessage());
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Username Or Password Isn't Correct!", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String user = etUser.getText().toString();
        String password = etPass.getText().toString();

        if (user.isEmpty()) {
            etUser.setError("User can't be empty!");
            valid = false;
        }
        if (password.isEmpty() || password.length() < 8) {
            etPass.setError("Password must from 8 characters!");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
