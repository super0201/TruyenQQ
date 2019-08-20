package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import model.ModelManga;
import utils.ImageHelper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class DetailNewActivity extends AppCompatActivity {
    private ArrayList<ModelManga> mangases;
    ImageView ivManga_detail;
    TextView tvName_detail,tvCate_detail,tvDate_detail,tvDesc_detail;
    ImageView imvBack;

    @SuppressLint({"ResourceType", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_detail_new);

        ivManga_detail = findViewById( R.id.imgManga_detail );
        tvName_detail = findViewById( R.id.tvName_detail );
        tvCate_detail = findViewById( R.id.tvCate_detail );
        tvDate_detail = findViewById( R.id.tvDate_detail );
        tvDesc_detail = findViewById( R.id.tvDesc_detail );

        imvBack = findViewById(R.id.imvBack);

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvBack.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.click_animation));
                getFragmentManager().popBackStackImmediate();
                finish();
            }
        });

        mangases = new ArrayList<>(  );
        Intent intent = new Intent(  );
//        intent.getStringArrayListExtra( "mangaName" );
        intent.getExtras();
        Bundle bundle = new Bundle(  );
        bundle = getIntent().getExtras();

        String mName = bundle.getString( "mangaName" );
        String mCate = bundle.getString( "mangaCate" );
        String mDate = bundle.getString( "mangaDate" );
        String mDesc = bundle.getString( "mangaDesc" );
        String img = bundle.getString( "img" );

        tvName_detail.setText( mName );
        tvCate_detail.setText( mCate );
        tvDate_detail.setText( mDate );
        tvDesc_detail.setText( mDesc );

        Glide.with(getBaseContext()).load(img)
                .apply(centerCropTransform()
                        .placeholder(R.raw.loading)
                        .error(R.raw.error)
                        .priority(Priority.HIGH)
                        //using cache strategy for caching image on first time load
                        //good for app loading performance
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(withCrossFade())
                .onlyRetrieveFromCache(true)
                .into(ivManga_detail);
    }
}
