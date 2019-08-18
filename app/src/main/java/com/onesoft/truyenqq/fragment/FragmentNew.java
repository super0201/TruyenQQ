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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.github.silvestrpredko.dotprogressbar.DotProgressBarBuilder;
import com.onesoft.truyenqq.R;
import com.onesoft.truyenqq.activity.DetailNewActivity;
import com.onesoft.truyenqq.adapter.RecyclerItemClickListener;
import com.onesoft.truyenqq.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

import model.ListManga;
import model.Manga;
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
    RecyclerViewAdapter adapter;
    private ArrayList<Manga> mangaArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.layout_fragment_new, container, false);

        rvManga = view.findViewById(R.id.rvManga);

        ShowView(view);

        //set onClick event for item
        rvManga.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        Intent intent = new Intent(getContext(), DetailNewActivity.class);
//                        intent.putParcelableArrayListExtra("data", list);
//                        intent.putExtra("pos", position);
//                        startActivity(intent);
//                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//
//                        Toast.makeText( getContext(),"name: "+mangaArrayList.get( position ).getName(),Toast.LENGTH_SHORT ).show();
                    }
                }));

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
                Call<ListManga> call = api.getDataComic(index);

                // Enqueue Callback will be call when get response...
                call.enqueue(new Callback<ListManga>() {
                    @Override
                    public void onResponse(Call<ListManga> call, Response<ListManga> response) {
                        if(response.isSuccessful()) {
                            // Got Successfully
                            mangaArrayList = response.body().getMangas();

                            // Binding that List to Adapter
                            adapter = new RecyclerViewAdapter( mangaArrayList,getContext());
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

                            rvManga.setLayoutManager( manager );
                            rvManga.setAdapter( adapter );
                        } else {
                            Snackbar.make(getView(), "There is something wrong...hmm?!", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListManga> call, Throwable t) {

                    }
                });
            }
        });
        rvManga.addOnItemTouchListener( new RecyclerItemClickListener( getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(View view, int position) {
                String name = mangaArrayList.get( position ).getName();
                TextView tvName_dia,tvCate_dia,tvDate_dia,tvDescription_dia;ImageView imageView;

                LayoutInflater inflater = getLayoutInflater();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(view.getContext() );
                view = inflater.inflate( R.layout.item_dialog_manga,null );
                mBuilder.setView( view ).setTitle( "Information: " ).setCancelable( true ).create().show();
                for(position=0; position<mangaArrayList.size(); position++){
                    if (name.equals( mangaArrayList.get( position ).getName() )){
                        tvName_dia = view.findViewById( R.id.tvName_dia );
                        tvCate_dia = view.findViewById( R.id.tvCate_dia );
                        tvDate_dia = view.findViewById( R.id.tvDate_dia );
                        tvDescription_dia = view.findViewById( R.id.tvDescription_dia );
                        imageView = view.findViewById( R.id.ivManga_dia );

                        Button btnGoto_dia = (Button)view.findViewById( R.id.btnGoto_dia ) ;

                        tvName_dia.setText( mangaArrayList.get( position ).getName() );
                        tvCate_dia.setText( mangaArrayList.get( position ).getCategory() );
                        tvDate_dia.setText( mangaArrayList.get( position ).getDate_add() );
                        tvDescription_dia.setText( mangaArrayList.get( position ).getDescription() );


                        //With Picasso - Slow request and process image
                        //Let's practice with Glide

//                        Picasso.with(view.getContext())
//                                  .load(mangaArrayList.get(i).getThumb())
//                                  .placeholder(R.mipmap.ic_launcher)
//                                  .error(R.mipmap.ic_launcher)
//                                  .into(imageView);

                        //With Glide - Fast, and out-performed Picasso
                        Glide.with(view.getContext())
                                .load(mangaArrayList.get(position).getThumb())
                                .apply(centerCropTransform()
                                        .placeholder(R.raw.thumbs_up)
                                        .error(R.raw.no_internet)
                                        .priority( Priority.HIGH))
                                .transition(withCrossFade())
                                .thumbnail(0.1f)
                                .into(imageView);

                        final int finalPosition = position;
                        btnGoto_dia.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String name = mangaArrayList.get( finalPosition ).getName();
                                String cate = mangaArrayList.get( finalPosition ).getCategory();
                                String date = mangaArrayList.get( finalPosition ).getDate_add();
                                String desc = mangaArrayList.get( finalPosition ).getDescription();
                                String img = mangaArrayList.get( finalPosition ).getThumb();

                                Intent intent = new Intent(getContext(), DetailNewActivity.class);

                                mangaArrayList = new ArrayList<>(  );
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

//    public void Show(View view){
////        final Context context = getActivity().getApplicationContext();
//        view.getApplicationWindowToken();
//
//        if (InternetConnection.checkConnection( view.getContext()) ) {
//            //Dot progress bar with nice view - should use this often
//            new DotProgressBarBuilder(getContext())
//                    .setDotAmount(4)
//                    .setStartColor(Color.BLACK)
//                    .setAnimationDirection(DotProgressBar.LEFT_DIRECTION)
//                    .build();
//
//            //This is how you create thread in a fragment - high loading speed baby!
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    //Creating an object of our api interface
//                    NetworkAPI api = ServiceAPI.getDataComic();
//
//                    // Calling JSON
//                    Call<ListManga> call = api.getDataComic();
//
//                    // Enqueue Callback will be call when get response...
//                    call.enqueue(new Callback<ListManga>() {
//                        @Override
//
//                        public void onResponse(Call<ListManga> call, Response<ListManga> response)
//                        {
//                            //Dismiss Dialog
////                    dialog.dismiss();
//
//                            if(response.isSuccessful()) {
//
//                                // Got Successfully
//                                mangaArrayList = response.body().getMangas();
//
//                                // Binding that List to Adapter
//                                adapter = new MyAdapter(getContext(), mangaArrayList);
//                                lvManga.setAdapter(adapter);
//
//                            } else {
//
//                                Snackbar.make(parentLayout, "There is something wrong...hmm?!", Snackbar.LENGTH_LONG).show();
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<ListManga> call, Throwable t) {
////                    dialog.dismiss();
//                        }
//                    });
//                }
//            });
//
//
//        lvManga.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText( view.getContext(),"Position"+ i + mangaArrayList.get(i).getDescription(),Toast.LENGTH_SHORT ).show();
//
//                String name = mangaArrayList.get( i ).getName();
//                TextView tvName_dia,tvCate_dia,tvDate_dia,tvDescription_dia;
//                ImageView imageView;
//
//                LayoutInflater inflater = getLayoutInflater();
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder( view.getContext() );
//                view = inflater.inflate( R.layout.dialog_item_manga,null );
//                mBuilder.setView( view ).setTitle( "Information: " ).setCancelable( true ).create().show();
//                for(i=0; i<mangaArrayList.size(); i++){
//                    if (name.equals( mangaArrayList.get( i ).getName() )){
//                        tvName_dia = view.findViewById( R.id.tvName_dia );
//                        tvCate_dia = view.findViewById( R.id.tvCate_dia );
//                        tvDate_dia = view.findViewById( R.id.tvDate_dia );
//                        tvDescription_dia = view.findViewById( R.id.tvDescription_dia );
//                        imageView = view.findViewById( R.id.ivManga_dia );
//
//                        tvName_dia.setText( mangaArrayList.get( i ).getName() );
//                        tvCate_dia.setText( mangaArrayList.get( i ).getCategory() );
//                        tvDate_dia.setText( mangaArrayList.get( i ).getDate_add() );
//                        tvDescription_dia.setText( mangaArrayList.get( i ).getDescription() );
//
//
//                        //With Picasso - Slow request and process image
//                        //Let's practice with Glide
//
////                        Picasso.with(view.getContext())
////                                  .load(mangaArrayList.get(i).getThumb())
////                                  .placeholder(R.mipmap.ic_launcher)
////                                  .error(R.mipmap.ic_launcher)
////                                  .into(imageView);
//
//                        //With Glide - Fast, and out-performed Picasso
//                        Glide.with(view.getContext())
//                                .load(mangaArrayList.get(i).getThumb())
//                                .apply(centerCropTransform()
//                                        .placeholder(R.raw.thumbs_up)
//                                        .error(R.raw.no_internet)
//                                        .priority(Priority.HIGH))
//                                .transition(withCrossFade())
//                                .thumbnail(0.1f)
//                                .into(imageView);
//
//                        break;
//                    }
//
//                }
//            }
//        } );
//    }}

}


