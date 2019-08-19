package com.onesoft.truyenqq.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onesoft.truyenqq.R;

import java.util.List;

import model.ModelManga;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class AdapterManga extends ArrayAdapter<ModelManga> {

    List<ModelManga> modelMangaList;
    Context context;
    private LayoutInflater mInflater;

    //*8*//
    public AdapterManga(Context context, List<ModelManga> objects) {
        super( context, 0, objects );
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelMangaList = objects;
    }

    @Override
    public ModelManga getItem(int position) {
        return modelMangaList.get(position);
    }
    private static class ViewHolder{
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView tvName, tvCate, tvDate;


        public ViewHolder(RelativeLayout rootView, ImageView imageView, TextView tvName, TextView tvCate, TextView tvDate) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.tvName = tvName;
            this.tvCate = tvCate;
            this.tvDate = tvDate;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = rootView.findViewById( R.id.imvManga );
            TextView tvName = rootView.findViewById( R.id.tvName );
            TextView tvCate = rootView.findViewById( R.id.tvCate );
            TextView tvDate = rootView.findViewById( R.id.tvDate );

            return new ViewHolder(rootView, imageView, tvName, tvCate,tvDate);
        }
    }
    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate( R.layout.item_manga, parent, false );
            vh = ViewHolder.create( (RelativeLayout) view );
            view.setTag( vh );
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        ModelManga item = getItem(position);
        vh.tvName.setText(item.getName());
        vh.tvCate.setText(item.getCategory());
        vh.tvDate.setText(item.getDate_add());

        //.. LOADING ..//
        Glide.with(getContext()).load(item.getThumb())
                .apply(centerCropTransform()
                        .placeholder(R.raw.loading)
                        .error(R.raw.error)
                        .priority(Priority.HIGH)
                        //using cache strategy for caching image on first time load
                        //good for app loading performance
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(withCrossFade())
                .thumbnail(0.5f)
                .into(vh.imageView);

        return vh.rootView;

    }

}
