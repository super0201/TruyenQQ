package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Date;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class DetailUserActivity extends AppCompatActivity {
    ImageView imvThumb;
    EditText user;
    EditText email;
    EditText name;
    TextView date;
    String user1, email1, name1, thumb1;
//    Date date1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_detail);

        imvThumb = findViewById(R.id.imvThumb);
        user = findViewById(R.id.etUser);
        email = findViewById(R.id.etMail);
        name = findViewById(R.id.etName);
        date = findViewById(R.id.tvDate);

        doThings();

    }

    @SuppressLint("ResourceType")
    public void doThings(){
        Bundle extras = getIntent().getExtras();

        user1 = extras.getString("user");
        email1 = extras.getString("email");
        name1 = extras.getString("name");
        thumb1 = extras.getString("thumb");
//        date1 = (Date) extras.get("date");

        user.setText(user1);
        email.setText(email1);
        name.setText(name1);
//        date.setText((CharSequence) date1);

        Glide.with(getBaseContext()).load(thumb1)
                .apply(centerCropTransform()
                        .placeholder(R.raw.loading)
                        .error(R.raw.error)
                        .priority(Priority.HIGH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(withCrossFade())
                .thumbnail(0.1f)
                .into(imvThumb);
    }
}
