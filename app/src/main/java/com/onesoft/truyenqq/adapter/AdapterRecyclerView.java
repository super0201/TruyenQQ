package com.onesoft.truyenqq.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onesoft.truyenqq.R;

import java.util.List;

import model.ModelManga;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {
    List<ModelManga> modelMangaList;
    Context context;
    //EVENT CLICK ITEM..//

    public AdapterRecyclerView(List<ModelManga> modelMangaList, Context c){
        this.modelMangaList = modelMangaList;
        this.context = c ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_manga,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelManga item = modelMangaList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvCate.setText(item.getCategory());
        holder.tvDate.setText(item.getDate_add());
        //.. LOADING ..//
        Glide.with( context ).load( item.getThumb() ).placeholder( R.mipmap.ic_launcher ).into( holder.imageView );

    }

    @Override
    public int getItemCount() {
        return modelMangaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView tvName;
        public TextView tvCate;
        public TextView tvDate;

        public ViewHolder(View itemView) {
            super( itemView );
            imageView = itemView.findViewById( R.id.imvManga );
            tvName = itemView.findViewById( R.id.tvName );
            tvCate = itemView.findViewById( R.id.tvCate );
            tvDate = itemView.findViewById( R.id.tvDate );

        }
    }
}
