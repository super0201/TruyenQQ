package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import model.ServerResponse;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText etUser, etPass;
    LoginButton loginButton;
    Button btnLogin;
    TextView tvReg;
    CheckBox ckbRemember;
    private NetworkAPI api;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_activity);

        //registerUser ServiceAPI and call getJSON from server
        api = ServiceAPI.userService(NetworkAPI.class);

        //create callback manager
        callbackManager = CallbackManager.Factory.create();

        btnLogin = findViewById(R.id.btnLogin);
        loginButton = findViewById(R.id.login_button);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        ckbRemember = findViewById(R.id.ckbRemember);
        tvReg = findViewById(R.id.tvReg);

        ckbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String user = etUser.getText().toString();
                String pass = etPass.getText().toString();

                if (buttonView.isChecked()) {
                    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    //write in sharedpref
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("user", user);
                    editor.putString("pass", pass);
                    editor.commit();
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

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        //facebook login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                getUserProfile(AccessToken.getCurrentAccessToken());

                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code

                            }
                        });
            }
        });

    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            final String email = object.getString("email");
                            final String id = object.getString("id");
                            final String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            final String name = first_name + last_name;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Call<ServerResponse> call = api.registerUser(email, id, name, image_url, date);
                                    call.enqueue(new Callback<ServerResponse>() {
                                        @SuppressLint("ResourceType")
                                        @Override
                                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                            Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT);
                                            if(response.body().getResult() == 1){
                                                LayoutInflater inflater = getLayoutInflater();
                                                View layout = inflater.inflate(R.layout.custom_toast,
                                                        (ViewGroup) findViewById(R.id.custom_toast_container));

                                                TextView text = layout.findViewById(R.id.text);
                                                ImageView img = layout.findViewById(R.id.imgToast);
                                                img.setImageResource(R.raw.thumbs_up);
                                                text.setText(R.string.reg_success);

                                                Toast toast = new Toast(getApplicationContext());
                                                toast.setGravity(Gravity.BOTTOM, 0, 180);
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.setView(layout);
                                                toast.show();

                                            } else {
                                                LayoutInflater inflater = getLayoutInflater();
                                                View layout = inflater.inflate(R.layout.custom_toast,
                                                        (ViewGroup) findViewById(R.id.custom_toast_container));

                                                TextView text = layout.findViewById(R.id.text);
                                                ImageView img = layout.findViewById(R.id.imgToast);
                                                img.setImageResource(R.raw.no_internet);
                                                text.setText(R.string.reg_failed);

                                                Toast toast = new Toast(getApplicationContext());
                                                toast.setGravity(Gravity.BOTTOM, 0, 180);
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.setView(layout);
                                                toast.show();

                                                Log.e(TAG," Response Error "+ response.code());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                                            Log.e(TAG," Response Error "+ t.getMessage());
                                        }
                                    });
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

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
                }, 2000);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginSuccess() {
        final String user = etUser.getText().toString();
        final String pass = etPass.getText().toString();

        Call<ServerResponse> call = api.checkLogin(user, pass);
        call.enqueue(new Callback<ServerResponse>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.body().getResult() == 1){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast,
                            (ViewGroup) findViewById(R.id.custom_toast_container));

                    TextView text = layout.findViewById(R.id.text);
                    ImageView img = layout.findViewById(R.id.imgToast);
                    img.setImageResource(R.raw.thumbs_up);
                    text.setText(R.string.login_success);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 180);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();

                    //write in sharedpref
                    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isTempLoggedIn", true);
                    editor.putString("user", user);
                    editor.putString("pass", pass);
                    editor.commit();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);

                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast,
                            (ViewGroup) findViewById(R.id.custom_toast_container));

                    TextView text = layout.findViewById(R.id.text);
                    ImageView img = layout.findViewById(R.id.imgToast);
                    img.setImageResource(R.raw.no_internet);
                    text.setText(R.string.login_failed);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 0, 180);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();

                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
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
            etUser.setError("ModelUser can't be empty!");
            valid = false;
        }
        if (password.isEmpty()) {
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
