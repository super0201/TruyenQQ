package network;

import java.util.List;

import model.ServerResponse;
import model.User;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {
    @POST("api_register.php")
    @FormUrlEncoded
    Call<ServerResponse> register(@Field("user") String user,
                                  @Field("pass") String pass,
                                  @Field("name") String name,
                                  @Field("date") String date);
    @GET("api_check_user.php")
    Call<ServerResponse> checkLogin(@Query("user") String user, @Query("pass") String pass);
//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
}
