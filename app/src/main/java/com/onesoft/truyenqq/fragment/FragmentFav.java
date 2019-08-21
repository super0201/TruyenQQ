package com.onesoft.truyenqq.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.truyenqq.ActivityCateList;
import com.onesoft.truyenqq.DetailNewActivity;
import com.onesoft.truyenqq.R;
import com.onesoft.truyenqq.adapter.AdapterCategory;
import com.onesoft.truyenqq.adapter.AdapterFav;
import com.onesoft.truyenqq.adapter.ApdaterCateList;
import com.onesoft.truyenqq.adapter.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import model.ModelFav;
import model.ModelFav;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFav extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<ModelFav> list;
    private Integer index = 0;
    private NetworkAPI api;
    AdapterFav mAdapter;
    ImageView unFav;
    RecyclerView mRecyclerView;
    FragmentTransaction ft;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

//        dao = new FavDAO(getContext());
//        list = dao.viewAll();

        api = ServiceAPI.userService(NetworkAPI.class);

        list = new ArrayList<>();

        doThing(view);

        return view;
    }

    private void doThing(View view) {
        //swipeRefresh event
        mRecyclerView = view.findViewById(R.id.rvFav);
        swipeRefreshLayout = view.findViewById(R.id.refreshFav);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                mAdapter.notifyDataSetChanged();
                load(index);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //set data for adapter
        mRecyclerView = view.findViewById(R.id.cateList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new AdapterFav(getContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setLoadMoreListener(new AdapterFav.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int ind = ++index;
                loadMore(ind);
            }
        });

        //onItemRecycler click
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), ActivityCateList.class);
                        intent.putParcelableArrayListExtra("data", list);
                        intent.putExtra("pos", position);
                        startActivityForResult(intent, 10001);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                }));

    }

    private void load(final int index){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String user = prefs.getString("user", null);

                Call<List<ModelFav>> call = api.getBookmark(user, index);
                call.enqueue(new Callback<List<ModelFav>>() {
                    @Override
                    public void onResponse(Call<List<ModelFav>> call, Response<List<ModelFav>> response) {
                        if(response.isSuccessful()){
                            list.addAll(response.body());
                            mAdapter.notifyDataChanged();
                        }else{
//                    Log.e(TAG," Response Error "+ String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ModelFav>> call, Throwable t) {
//                Log.e(TAG," Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }

    private void loadMore(final int index){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //add loading progress view
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String user = prefs.getString("user", null);
                list.add(new ModelFav("user", "bookmark", "thumb"));
                mAdapter.notifyItemInserted(list.size() - 1);

                Call<List<ModelFav>> call = api.getBookmark(user, index);
                call.enqueue(new Callback<List<ModelFav>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<List<ModelFav>> call, Response<List<ModelFav>> response) {
                        if(response.isSuccessful()){
                            //remove loading view
                            list.remove(list.size() - 1);

                            List<ModelFav> result = response.body();
                            if(result.size() > 0){
                                //add loaded data
                                list.addAll(result);
                            }else{//result size 0 means there is no more data available at server
                                mAdapter.setMoreDataAvailable(false);
                                //telling adapter to stop calling load more as no more server data available
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) getView().findViewById(R.id.custom_toast_container));

                                TextView text = layout.findViewById(R.id.text);
                                ImageView img = layout.findViewById(R.id.imgToast);
                                img.setImageResource(R.raw.no_internet);
                                text.setText(R.string.no_more);

                                Toast toast = new Toast(getContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 180);
                                toast.setDuration(Toast.LENGTH_LONG);
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
                    public void onFailure(Call<List<ModelFav>> call, Throwable t) {
//                Log.e(TAG," Load More Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)){
            // recreate your fragment here
            ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
}
