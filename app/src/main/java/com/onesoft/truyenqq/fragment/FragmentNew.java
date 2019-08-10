package com.onesoft.truyenqq.fragment;

import android.app.ProgressDialog;
<<<<<<< Updated upstream
=======
import android.content.Context;
import android.graphics.Bitmap;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
<<<<<<< Updated upstream
=======
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
>>>>>>> Stashed changes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.truyenqq.R;
import com.onesoft.truyenqq.adapter.MyAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
<<<<<<< Updated upstream
=======
import java.util.zip.Inflater;
>>>>>>> Stashed changes

import model.ListManga;
import model.Manga;
import network.InternetConnection;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNew extends Fragment {

    private ListView lvManga;
    private MyAdapter adapter;
    private View parentLayout;
    private ArrayList<Manga> mangaArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_new, container, false);

        lvManga = (ListView) view.findViewById( R.id.lvManga );
        Show( view );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void Show(View view){
//        final Context context = getActivity().getApplicationContext();
        view.getApplicationWindowToken();

        if (InternetConnection.checkConnection( view.getContext()) ) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog( view.getContext() );
            dialog.setTitle( "Info" );
            dialog.setMessage( "Nothing" );
            dialog.show();
            //Creating an object of our api interface
            NetworkAPI api = ServiceAPI.getDataComic();
            // Calling JSON
            Call<ListManga> call = api.getDataComic();

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<ListManga>() {
                @Override

                public void onResponse(Call<ListManga> call, Response<ListManga> response)
                {
                    //Dismiss Dialog
                    dialog.dismiss();

                    if(response.isSuccessful()) {
                        // Got Successfully
                        mangaArrayList = response.body().getMangas();
                        // Binding that List to Adapter
                        adapter = new MyAdapter(getContext(), mangaArrayList);
                        lvManga.setAdapter(adapter);
                    } else {
                        Snackbar.make(parentLayout, "loading...", Snackbar.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ListManga> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }else{
            Snackbar.make(parentLayout,"FAIL", Snackbar.LENGTH_LONG).show();
        }

        lvManga.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText( view.getContext(),"Position"+i +mangaArrayList.get( i ).getDescription(),Toast.LENGTH_SHORT ).show();

                String name = mangaArrayList.get( i ).getName();
                TextView tvName_dia,tvCate_dia,tvDate_dia,tvDescription_dia;
                ImageView imageView;

                LayoutInflater inflater = getLayoutInflater();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder( view.getContext() );
                view = inflater.inflate( R.layout.dialog_item_manga,null );
                mBuilder.setView( view ).setTitle( "Information: " ).setCancelable( true ).create().show();
                for(i=0;i<mangaArrayList.size();i++){
                    if (name.equals( mangaArrayList.get( i ).getName() )){
                        tvName_dia = (TextView) view.findViewById( R.id.tvName_dia );
                        tvCate_dia = (TextView) view.findViewById( R.id.tvCate_dia );
                        tvDate_dia = (TextView) view.findViewById( R.id.tvDate_dia );
                        tvDescription_dia = (TextView) view.findViewById( R.id.tvDescription_dia );
                        imageView = (ImageView)view.findViewById( R.id.ivManga_dia );

                        tvName_dia.setText( mangaArrayList.get( i ).getName() );
                        tvCate_dia.setText( mangaArrayList.get( i ).getCategory() );
                        tvDate_dia.setText( mangaArrayList.get( i ).getDate_add() );
                        tvDescription_dia.setText( mangaArrayList.get( i ).getDescription() );

                        Picasso.with( view.getContext() ).load( mangaArrayList.get( i ).getThumb() ).placeholder( R.mipmap.ic_launcher ).error( R.mipmap.ic_launcher ).into( imageView );
                        break;
                    }

                }
            }
        } );
    }

    public void ShowDialog(View view){





    }
}
