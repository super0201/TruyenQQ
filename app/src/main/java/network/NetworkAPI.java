package network;

import model.ListManga;
import model.ServerResponse;
import model.User;
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

    @GET("api_get_user.php")
    Call<User> getUser(@Query("user") String user);

    @GET("api_get_comic.php")
    Call<ListManga> getDataComic(@Query("index") Integer index);
//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
}
