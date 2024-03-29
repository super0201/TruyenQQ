package com.onesoft.truyenqq.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.github.silvestrpredko.dotprogressbar.DotProgressBarBuilder;
import com.onesoft.truyenqq.R;
import com.onesoft.truyenqq.DetailNewActivity;
import com.onesoft.truyenqq.adapter.RecyclerItemClickListener;
import com.onesoft.truyenqq.adapter.AdapterRecyclerView;

import java.util.ArrayList;

import model.ModelListManga;
import model.ModelManga;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class FragmentNew extends Fragment {
    private RecyclerView rvManga;
    private Integer index = 0;
    AdapterRecyclerView adapter;
    private ArrayList<ModelManga> modelMangaArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.layout_fragment_new, container, false);

        rvManga = view.findViewById(R.id.rvManga);

        ShowView(view);

        return view;
    }

    private void ShowView(View view){
        view.getApplicationWindowToken();

        //Dot progress bar with nice view - should use this often
        new DotProgressBarBuilder(getContext())
                .setDotAmount(4)
                .setStartColor(Color.BLUE)
                .setEndColor(Color.CYAN)
                .setAnimationDirection(DotProgressBar.LEFT_DIRECTION)
                .build();

        //This is how you create thread in a fragment - high loading speed baby!
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Creating an object of our api interface
                NetworkAPI api = ServiceAPI.getDataComic();

                // Calling JSON
                Call<ModelListManga> call = api.getDataComic(index);

                // Enqueue Callback will be call when get response...
                call.enqueue(new Callback<ModelListManga>() {
                    @Override
                    public void onResponse(Call<ModelListManga> call, Response<ModelListManga> response) {
                        if(response.isSuccessful()) {
                            // Got Successfully
                            modelMangaArrayList = response.body().getMangases();

                            // Binding that List to Adapter
                            adapter = new AdapterRecyclerView(modelMangaArrayList,getContext());
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

                            rvManga.setLayoutManager( manager );
                            rvManga.setAdapter( adapter );
                        } else {
                            Snackbar.make(getView(), "There is something wrong...hmm?!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelListManga> call, Throwable t) {

                    }
                });
            }
        });

        rvManga.addOnItemTouchListener( new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(View view, int position) {
                String name = modelMangaArrayList.get( position ).getName();
                final TextView tvName_dia,tvCate_dia,tvDate_dia,tvDescription_dia;
                final ImageView imageView;

                LayoutInflater inflater = getLayoutInflater();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext() );
                view = inflater.inflate( R.layout.item_dialog_manga,null );
                mBuilder.setView( view ).setCancelable( true ).create().show();
                for(position=0; position< modelMangaArrayList.size(); position++){
                    if (name.equals( modelMangaArrayList.get( position ).getName() )){
                        tvName_dia = view.findViewById( R.id.tvName_dia );
                        tvCate_dia = view.findViewById( R.id.tvCate_dia );
                        tvDate_dia = view.findViewById( R.id.tvDate_dia );
                        tvDescription_dia = view.findViewById( R.id.tvDescription_dia );
                        imageView = view.findViewById( R.id.ivManga_dia );

                        Button btnGoto_dia = view.findViewById( R.id.btnGoto_dia );

                        //With Picasso - Slow request and process image
                        //Let's practice with Glide

//                        Picasso.with(view.getContext())
//                                  .load(modelMangaArrayList.get(i).getThumb())
//                                  .placeholder(R.mipmap.ic_launcher)
//                                  .error(R.mipmap.ic_launcher)
//                                  .into(imageView);

                        //With Glide - Fast, and out-performed Picasso
                        final View finalView = view;
                        final int finalPosition1 = position;
                        final int finalPosition2 = position;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvName_dia.setText( modelMangaArrayList.get(finalPosition2).getName() );
                                tvCate_dia.setText( modelMangaArrayList.get(finalPosition2).getCategory() );
                                tvDate_dia.setText( modelMangaArrayList.get(finalPosition2).getDate_add() );
                                tvDescription_dia.setText( modelMangaArrayList.get(finalPosition2).getDescription() );

                                Glide.with(finalView.getContext())
                                        .load(modelMangaArrayList.get(finalPosition1).getThumb())
                                        .apply(centerCropTransform()
                                                .placeholder(R.raw.thumbs_up)
                                                .error(R.raw.no_internet)
                                                .priority( Priority.HIGH)
                                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                                        .transition(withCrossFade())
                                        .onlyRetrieveFromCache(true)
                                        .into(imageView);
                            }
                        });


                        final int finalPosition = position;
                        btnGoto_dia.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String name = modelMangaArrayList.get( finalPosition ).getName();
                                String cate = modelMangaArrayList.get( finalPosition ).getCategory();
                                String date = modelMangaArrayList.get( finalPosition ).getDate_add();
                                String desc = modelMangaArrayList.get( finalPosition ).getDescription();
                                String img = modelMangaArrayList.get( finalPosition ).getThumb();

                                Intent intent = new Intent(getContext(), DetailNewActivity.class);

                                modelMangaArrayList = new ArrayList<>(  );
//                                intent.putParcelableArrayListExtra("data_all",list );
//                                intent.putExtra("mangaName", name );
//                                intent.putStringArrayListExtra( "key", )
                                intent.putExtra( "mangaName", name );
                                intent.putExtra( "mangaCate", cate );
                                intent.putExtra( "mangaDate", date );
                                intent.putExtra( "mangaDesc", desc);
                                intent.putExtra( "img", img);

                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                            }
                        } );

                        break;
                    }

                }
            }
        }));

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}


