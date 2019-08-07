package com.onesoft.truyenqq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText etUser, etPass;
    Button btnLogin, btnSignUp;
    CheckBox ckbRemember;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etUser = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
//        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //extract data from input and Session
        final String user = etUser.getText().toString();
        final String pass = etPass.getText().toString();

        session = new SessionManager(this);

        //button clickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
        }

        });

//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
//                startActivity(intent);
//            }
//        });
        
    }

    public void login(){
        final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Authenticating",
                "Vui Lòng Chờ!", true);
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
        session.createLoginSession("pass", "user");
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
        Toast.makeText(getBaseContext(), "Đăng Nhập Thành Công!", Toast.LENGTH_SHORT).show();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Username Hoặc Password Không Đúng!", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = etUser.getText().toString();
        String password = etPass.getText().toString();

        if (email.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUser.setError("Email Không Đúng Hoặc Để Trống!");
            valid = false;
        }
        if (password.isEmpty() || password.length() < 8) {
            etPass.setError("Password Phải Từ 8 Kí Tự Trở Lên!");
            valid = false;
        }
//        if (userDAO.checkLoginStat(email, password) < 0) {
//            etUser.setError("Email Không Đúng!");
//            etPass.setError("Password Không Đúng!");
//            valid = false;
//        }
        else {
            etUser.setError(null);
            etPass.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
