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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.onesoft.truyenqq.DetailUserActivity;
import com.onesoft.truyenqq.MainActivity;
import com.onesoft.truyenqq.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import model.ModelUser;
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
    private FragmentTransaction ft;
    CallbackManager callbackManager;
    String name, email, thumb, user;
    Date date;
    LinearLayout lnProfile;
    TextView tvName, tvUsername;
    ImageView imvProfile;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_user, container, false);

        //registerUser ServiceAPI and call getJSON from server
        api = ServiceAPI.userService(NetworkAPI.class);

        //create callback manager
        callbackManager = CallbackManager.Factory.create();

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
            editor.remove("user");
            editor.remove("pass");
            editor.remove("isLoggedIn");
            editor.remove("isTempLoggedIn");
            editor.commit();

            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);
            }
        });

        lnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailUserActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("thumb", thumb);
                intent.putExtra("date", date);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        getUserDetail(view);
//
//        getUserProfile(AccessToken.getCurrentAccessToken());
        return view;
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            tvName.setText(first_name + last_name);
                            tvUsername.setText(email);
//                            Picasso.with(MainActivity.this).load(image_url).into(imageView);

                            Glide.with(getContext()).load(image_url)
                                    .apply(centerCropTransform()
                                            .placeholder(R.raw.loading)
                                            .error(R.raw.error)
                                            .priority(Priority.HIGH)
                                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                                    .transition(withCrossFade())
                                    .thumbnail(0.5f)
                                    .into(imvProfile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void getUserDetail(View view){
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user = prefs.getString("user", null);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Call<ModelUser> call = api.getUser(user);
                call.enqueue(new Callback<ModelUser>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                        if(response.isSuccessful()){
                            //get all value for intent purpose
                            name = response.body().getName();
                            email = response.body().getEmail();
                            thumb = response.body().getThumb();
                            date = response.body().getDate_add();

                            tvName.setText(response.body().getName());
                            tvUsername.setText(response.body().getUsername());

                            Glide.with(getContext()).load(response.body().getThumb())
                                    .apply(centerCropTransform()
                                            .placeholder(R.raw.loading)
                                            .error(R.raw.error)
                                            .priority(Priority.HIGH)
                                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                                    .transition(withCrossFade())
                                    .thumbnail(0.5f)
                                    .into(imvProfile);
                        } else {
                            Log.e(TAG, " Response Error " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelUser> call, Throwable t) {
                        Log.e(TAG," Response Error "+ t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)){
            // recreate your fragment here
            ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }

        //register callback
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
