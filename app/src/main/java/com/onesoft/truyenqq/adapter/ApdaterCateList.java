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

import model.ModelSearch;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


public class ApdaterCateList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_DATA = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<ModelSearch> data;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */


    public ApdaterCateList(Context context, ArrayList<ModelSearch> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == TYPE_DATA){
            return new CateListHolder(inflater.inflate(R.layout.item_manga, parent,false));
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
            ((CateListHolder)holder).bindData(data.get(position));
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

    static class CateListHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvCate, tvDate;
        ImageView imvManga;

        public CateListHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCate = itemView.findViewById(R.id.tvCate);
            tvDate = itemView.findViewById(R.id.tvDate);
            imvManga = itemView.findViewById(R.id.imvManga);
        }

        @SuppressLint("ResourceType")
        void bindData(ModelSearch ModelCategoryDetail){
            //get value in model and set to textview
            tvName.setText(ModelCategoryDetail.getName());
            tvCate.setText(ModelCategoryDetail.getCategory());
            tvDate.setText(ModelCategoryDetail.getDate_add());

            //load image with glide
            Glide.with(context).load(ModelCategoryDetail.getThumb())
                    .apply(centerCropTransform()
                            .placeholder(R.raw.loading)
                            .error(R.raw.error)
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .transition(withCrossFade())
                    .thumbnail(0.5f)
                    .into(imvManga);
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

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
