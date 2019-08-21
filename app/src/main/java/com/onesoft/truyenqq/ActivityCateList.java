package com.onesoft.truyenqq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.truyenqq.adapter.ApdaterCateList;
import com.onesoft.truyenqq.adapter.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import model.ModelCategory;
import model.ModelSearch;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCateList extends AppCompatActivity {
    private ArrayList<ModelSearch> list;
    public ArrayList<ModelCategory> data = new ArrayList<>();
    private Integer index = 0;
    private NetworkAPI api;
    int pos;

    ApdaterCateList mAdapter;
    RecyclerView mRecyclerView;
    String cateName, input;
    ImageView imvBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_category_list);

        list = new ArrayList<>();

        api = ServiceAPI.cateService(NetworkAPI.class);
        
        imvBack = findViewById(R.id.imvBack);

        toDo();
        
    }

    public void toDo(){
        //get data intent from Category fragment
        data = getIntent().getParcelableArrayListExtra("data");
        pos = getIntent().getIntExtra("pos", 0);

        //get data drom intetnt array with position
        cateName = data.get(pos).getName();

        //set data input equal with ipData
        input = cateName;

        mRecyclerView = findViewById(R.id.cateDetailList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ApdaterCateList(getBaseContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setLoadMoreListener(new ApdaterCateList.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int ind = ++index;
                        loadMore(ind, input);
                    }
                });
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getBaseContext(), DetailCateActivity.class);
                        intent.putParcelableArrayListExtra("data", list);
                        intent.putExtra("pos", position);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                }));


        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvBack.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.click_animation));
                finish();
            }
        });

        TextView tvTitleCate = findViewById(R.id.tvTitleCate);
        tvTitleCate.setText(input);

        load(index, input);
    }

    private void load(final int index, final String input){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Call<List<ModelSearch>> call = api.getComicByCate(input, index);
                call.enqueue(new Callback<List<ModelSearch>>() {
                    @Override
                    public void onResponse(Call<List<ModelSearch>> call, Response<List<ModelSearch>> response) {
                        if(response.isSuccessful()){
                            list.addAll(response.body());
                            mAdapter.notifyDataChanged();
                        }else{
//                    Log.e(TAG," Response Error "+ String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ModelSearch>> call, Throwable t) {
//                Log.e(TAG," Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }

    private void loadMore(final int index, final String input){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //add loading progress view
                list.add(new ModelSearch("category","thumb", "name", "desc", "date"));
                mAdapter.notifyItemInserted(list.size() - 1);

                Call<List<ModelSearch>> call = api.getComicByCate(input, index);
                call.enqueue(new Callback<List<ModelSearch>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<List<ModelSearch>> call, Response<List<ModelSearch>> response) {
                        if(response.isSuccessful()){

                            //remove loading view
                            list.remove(list.size() - 1);

                            List<ModelSearch> result = response.body();
                            if(result.size() > 0){
                                //add loaded data
                                list.addAll(result);
                            }else{//result size 0 means there is no more data available at server
                                mAdapter.setMoreDataAvailable(false);
                                //telling adapter to stop calling load more as no more server data available
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));

                                TextView text = layout.findViewById(R.id.text);
                                ImageView img = layout.findViewById(R.id.imgToast);
                                img.setImageResource(R.raw.no_internet);
                                text.setText(R.string.no_more);

                                Toast toast = new Toast(getBaseContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 180);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            mAdapter.notifyDataChanged();
                            //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                        }else{
//                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ModelSearch>> call, Throwable t) {
//                Log.e(TAG," Load More Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
