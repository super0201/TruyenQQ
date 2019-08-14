package com.onesoft.truyenqq.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onesoft.truyenqq.R;

import java.util.ArrayList;

import model.User;
import network.NetworkAPI;
import network.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class FragmentUser extends Fragment {
    private static final String TAG = "FragmentUser";
    private NetworkAPI api;
    LinearLayout lnProfile;
    TextView tvName, tvUsername;
    ImageView imvProfile;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        //register ServiceAPI and call getJSON from server
        api = ServiceAPI.createService(NetworkAPI.class);

        lnProfile = view.findViewById(R.id.lnProfileSum);
        tvName = view.findViewById(R.id.tvProfileName);
        tvUsername = view.findViewById(R.id.tvRole);
        imvProfile = view.findViewById(R.id.imvProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();

                getActivity().moveTaskToBack(true);
                getActivity().finish();
            }
        });

        getUserDetail(view);

        return view;
    }

    public void getUserDetail(View view){
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String user = prefs.getString("user", null);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Call<User> call = api.getUser(user);
                call.enqueue(new Callback<User>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            tvName.setText(response.body().getName());

                            tvUsername.setText(response.body().getUsername());
                            Glide.with(getContext()).load(response.body().getThumb())
                                    .apply(centerCropTransform()
                                            .placeholder(R.raw.loading)
                                            .error(R.raw.error)
                                            .priority(Priority.HIGH)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .transition(withCrossFade())
                                    .thumbnail(0.1f)
                                    .into(imvProfile);
                        } else {
                            Log.e(TAG, " Response Error " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e(TAG," Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }
}
