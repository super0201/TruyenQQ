package network;

import java.util.List;

import model.ModelCategory;
import model.ModelListManga;
import model.ModelSearch;
import model.ServerResponse;
import model.ModelUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkAPI {
    @GET("api_register.php")
    Call<ServerResponse> registerUser(@Query("user") String user,
                                      @Query("pass") String pass,
                                      @Query("name") String name,
                                      @Query("date") String date);

    @GET("api_update_user.php")
    Call<ServerResponse> updateUser(@Query("user") String user,
                                    @Query("email") String email,
                                    @Query("name") String name,
                                    @Query("date") String date);

    @GET("api_check_user.php")
    Call<ServerResponse> checkLogin(@Query("user") String user,
                                    @Query("pass") String pass);

    @GET("api_get_user.php")
    Call<ModelUser> getUser(@Query("user") String user);

    @GET("api_get_comic.php")
    Call<ModelListManga> getDataComic(@Query("index") Integer index);

    @GET("api_get_cate.php")
    Call<List<ModelCategory>> getCate(@Query("index") Integer index);

    @GET("api_cate_search.php")
    Call<List<ModelSearch>> getComicByCate(@Query("cate") String cate,
                                           @Query("index") Integer index);

//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
}
