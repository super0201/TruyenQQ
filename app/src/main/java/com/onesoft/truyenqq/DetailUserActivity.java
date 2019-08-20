package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.ServerResponse;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class DetailUserActivity extends AppCompatActivity {
    private static final String TAG = "DetailUserActivity";
    private NetworkAPI api;
    Button btnUpdate, btnUpdate1;
    ImageView imvThumb, imvBack;
    EditText email, name;
    TextView date, user;
    String user1, email1, name1, thumb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_detail);

        //create service for updateUser
        api = ServiceAPI.userService(NetworkAPI.class);

        imvThumb = findViewById(R.id.imvThumb);
        imvBack = findViewById(R.id.imvBack);
        user = findViewById(R.id.tvUser);
        email = findViewById(R.id.etMail);
        name = findViewById(R.id.etName);
        date = findViewById(R.id.tvDate);
        btnUpdate = findViewById(R.id.btnUpdateUser);
        btnUpdate1 = findViewById(R.id.btnUpdatePass);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });

        btnUpdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
            }
        });

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                getFragmentManager().popBackStackImmediate();
                finish();
                overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
            }
        });

        doThings();

    }

    @SuppressLint("ResourceType")
    public void doThings(){
        Bundle extras = getIntent().getExtras();

        //get intent data
        user1 = extras.getString("user");
        email1 = extras.getString("email");
        name1 = extras.getString("name");
        thumb1 = extras.getString("thumb");

        //set data into edittext
        user.setText(user1);
        email.setText(email1);
        name.setText(name1);
        user.setEnabled(false);

        //load image with Glide
        Glide.with(getBaseContext()).load(thumb1)
                .apply(centerCropTransform()
                        .placeholder(R.raw.loading)
                        .error(R.raw.error)
                        .priority(Priority.HIGH)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(withCrossFade())
                .onlyRetrieveFromCache(true)
                .into(imvThumb);
    }

    public void doUpdate(){
        final String update1 = user.getText().toString();
        final String update2 = email.getText().toString();
        final String update3 = name.getText().toString();
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Call<ServerResponse> call = api.updateUser(update1, update2, update3, date);
                call.enqueue(new Callback<ServerResponse>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT);
                        if(response.isSuccessful()){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast,
                                    (ViewGroup) findViewById(R.id.custom_toast_container));

                            TextView text = layout.findViewById(R.id.text);
                            ImageView img = layout.findViewById(R.id.imgToast);
                            img.setImageResource(R.raw.thumbs_up);
                            text.setText(R.string.update_success);

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.BOTTOM, 0, 180);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();

                            setResult(Activity.RESULT_OK);

                        } else {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast,
                                    (ViewGroup) findViewById(R.id.custom_toast_container));

                            TextView text = layout.findViewById(R.id.text);
                            ImageView img = layout.findViewById(R.id.imgToast);
                            img.setImageResource(R.raw.no_internet);
                            text.setText(R.string.update_failed);

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.BOTTOM, 0, 180);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();

//                            Log.e(TAG," Response Error "+ response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        Log.e(TAG," Response Error "+ t.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
        getFragmentManager().popBackStackImmediate();
        finish();
        overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
    }
}
