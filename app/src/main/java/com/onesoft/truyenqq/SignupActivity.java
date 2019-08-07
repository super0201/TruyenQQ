package com.onesoft.truyenqq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private Button _signup;
    private TextView _user, _email, _pass,_re_pass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _user = findViewById(R.id.inputUser);
        _email = findViewById(R.id.inputEmail);
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
//
//        String name = _name.getText().toString();
//        String address = _addr.getText().toString();
//        String email = _email.getText().toString();
//        String phone = _phone.getText().toString();
//        String room = _room.getText().toString();
//        String services = _services.getText().toString();
//        String date = _date.getText().toString();
//        String note = _note.getText().toString();
//        String id = _id.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        dialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        String name = _user.getText().toString();
        String pass = _pass.getText().toString();
        String email = _email.getText().toString();
        String re_pass = _re_pass.getText().toString();

        Toast.makeText(getBaseContext(), "Đăng Kí Thành Công!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);

        _signup.setEnabled(true);
        setResult(RESULT_OK, null);

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Đăng Ký Thất Bại!", Toast.LENGTH_LONG).show();

        _signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String name = _name.getText().toString();
        String address = _addr.getText().toString();
        String email = _email.getText().toString();
        String id = _id.getText().toString();

//        if (name.isEmpty() || name.length() < 3) {
//            _name.setError("Tên Phải Trên 3 Kí Tự!");
//            valid = false;
//        } else {
//            _name.setError(null);
//        }
//
//        if (address.isEmpty()) {
//            _addressText.setError("Địa Chỉ Không Được Để Trống!");
//            valid = false;
//        } else {
//            _addressText.setError(null);
//        }
//
//        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            _emailText.setError("Email Không Hợp Lệ!");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if(userDAO.checkEmailExists(email) > 0){
//            _emailText.setError("Đã Có Người Sử Dụng Email Này!");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (email.isEmpty()) {
//            _emailText.setError("Email Không Được Để Trống!");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
//
//        if (phone.isEmpty() || phone.length() != 10) {
//            _mobileText.setError("Không Được Để Trống Và Phải Trên 9 Kí Tự!");
//            valid = false;
//        } else {
//            _mobileText.setError(null);
//        }
//
//        if(userDAO.checkPhoneExists(phone) > 0){
//            _mobileText.setError("SĐT Này Đã Được Sử Dụng!");
//            valid = false;
//        } else {
//            _mobileText.setError(null);
//        }
//
//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            _passwordText.setError("Password Phải Từ 4 Hoặc 8 Kí Tự");
//            valid = false;
//        } else {
//            _passwordText.setError(null);
//        }
//
//        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
//            _reEnterPasswordText.setError("Password Không Giống!");
//            valid = false;
//        } else {
//            _reEnterPasswordText.setError(null);
//        }
        return valid;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
    }
}