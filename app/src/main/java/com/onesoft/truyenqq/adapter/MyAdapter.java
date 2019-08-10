package com.onesoft.truyenqq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.truyenqq.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Manga;

public class MyAdapter extends ArrayAdapter<Manga> {

    List<Manga> mangaList;
    Context context;
    private LayoutInflater mInflater;

    //*8*//
    public MyAdapter(Context context, List<Manga> objects) {
        super( context, 0, objects );
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mangaList = objects;
    }

    @Override
    public Manga getItem(int position) {
        return mangaList.get(position);
    }
    private static class ViewHolder{
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView tvName;
        public final TextView tvCate;
        public final TextView tvDate;

        public ViewHolder(RelativeLayout rootView, ImageView imageView, TextView tvName, TextView tvCate, TextView tvDate) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.tvName = tvName;
            this.tvCate = tvCate;
            this.tvDate = tvDate;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById( R.id.ivManga );
            TextView tvName = (TextView) rootView.findViewById( R.id.tvName );
            TextView tvCate = (TextView) rootView.findViewById( R.id.tvCate );
            TextView tvDate = (TextView) rootView.findViewById( R.id.tvDateAdd );

            return new ViewHolder(rootView, imageView, tvName, tvCate,tvDate);
        }
    }
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

        Manga item = getItem( position );
        vh.tvName.setText( item.getName() );
        vh.tvCate.setText( item.getCategory() );
        vh.tvDate.setText( item.getDate_add() );


        Picasso.with( context ).load( item.getThumb() ).placeholder( R.mipmap.ic_launcher ).error( R.mipmap.ic_launcher ).into( vh.imageView );
        return vh.rootView;
    }

}
