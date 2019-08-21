package com.onesoft.truyenqq.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onesoft.truyenqq.R;

import java.util.ArrayList;

public class FragmentFav extends Fragment {

//    private ArrayList<ModelFav> list = new ArrayList<>();
//    FavDAO dao;
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

        unFav = view.findViewById(R.id.unFavList);
        mRecyclerView = view.findViewById(R.id.listFav);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AdapterFav(getContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), DetailFavActivity.class);
                        intent.putParcelableArrayListExtra("data", list);
                        intent.putExtra("pos", position);
                        startActivityForResult(intent, 10001);
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    }
                }));

        //check list.size
        if (list.size() > 0){
            mAdapter.notifyDataSetChanged();
        }

        return view;
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
