package com.onesoft.truyenqq.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onesoft.truyenqq.R;

import java.util.ArrayList;
import java.util.List;

import model.ModelFav;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created By JohnNguyen - Onesoft on 19/11/2018
 */
public class AdapterFav extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_DATA = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<ModelFav> data;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */

    public AdapterFav(Context context, ArrayList<ModelFav> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == TYPE_DATA){
            return new FavListHolder(inflater.inflate(R.layout.item_manga, parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.item_load, parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position >= getItemCount() -1 && isMoreDataAvailable && !isLoading && loadMoreListener != null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position) == TYPE_DATA){
            ((FavListHolder)holder).bindData(data.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(data.size() > position){
            return TYPE_DATA;
        }
        return TYPE_LOAD;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /* VIEW HOLDERS */

    static class FavListHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView imvFav, imvHeart;

        public FavListHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName1);
            imvFav = itemView.findViewById(R.id.item_img1);
            imvHeart = itemView.findViewById(R.id.unFavList);
        }

        @SuppressLint("ResourceType")
        void bindData(ModelFav modelFav){
            //get value in model and set to textview
            tvName.setText(modelFav.getBookmark());
            //load image with glide
            Glide.with(context).load(modelFav.getThumb())
                    .apply(centerCropTransform()
                            .placeholder(R.raw.loading)
                            .error(R.raw.error)
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .transition(withCrossFade())
                    .thumbnail(0.8f)
                    .into(imvFav);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after updateUser the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(AdapterFav.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
