package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import model.ModelSearch;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created By JohnNguyen - Onesoft on 19/11/2018
 */
public class DetailCateActivity extends AppCompatActivity{
    public ArrayList<ModelSearch> data = new ArrayList<>();
    int pos;
    private Bitmap bm;
    ImageView imvCate, imvBack;
    TextView tvName, tvCate, tvDesc, tvDate;
    String img, name, cate, desc, date;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_new);

        imvBack = findViewById(R.id.imvBack);
        imvCate = findViewById(R.id.imgManga_detail);
        tvName = findViewById(R.id.tvName_detail);
        tvCate = findViewById(R.id.tvCate_detail);
        tvDesc = findViewById(R.id.tvDesc_detail);
        tvDate = findViewById(R.id.tvDate_detail);

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvBack.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.click_animation));
                getFragmentManager().popBackStackImmediate();
                finish();
            }
        });

        thingToDo();
    }


    @SuppressLint("ResourceType")
    public void thingToDo(){
        data = getIntent().getParcelableArrayListExtra("data");
        pos = getIntent().getIntExtra("pos", 0);

        img = data.get(pos).getThumb();
        name = data.get(pos).getName();
        cate = data.get(pos).getCategory();
        desc = data.get(pos).getDescription();
        date = data.get(pos).getDate_add();

        tvName.setText(name);
        tvCate.setText(cate);
        tvDesc.setText(desc);
        tvDate.setText(date);

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
                .into(imvCate);
//        ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GOTHAM);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStackImmediate();
        finish();
    }
}
