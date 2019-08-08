package network;

import model.Responde;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkAPI {
    @POST("api_register.php")
    @FormUrlEncoded
    Call<Responde> register(@Field("user") String user,
                                 @Field("pass") String re_pass,
                                    @Field("date") String date);
//    @GET("API_call_cate.php")
//    Call<List<ModelCategory>> getCate(@Query("index") int index);
//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
}
