package network;

import model.ServerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPI {
    @GET("api_register.php")
    Call<ServerResponse> register(@Query("user") String user,
                                  @Query("pass") String pass,
                                  @Query("name") String name,
                                  @Query("date") String date);
    @GET("api_check_user.php")
    Call<ServerResponse> checkLogin(@Query("user") String user,
                                    @Query("pass") String pass);
//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
}
