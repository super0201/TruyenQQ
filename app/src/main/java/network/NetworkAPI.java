//package network;
//
//import java.util.List;
//
//import model.ModelCategory;
//import model.ModelNew;
//import model.ModelPopular;
//import model.ModelSearch;
//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Query;
//
//public interface NetworkAPI {
//    @GET("API_call_all.php")
//    Call<List<ModelNew>> getAll(@Query("index") int index);
//
//    @GET("API_call_cate.php")
//    Call<List<ModelCategory>> getCate(@Query("index") int index);
//
//    @GET("API_call_popular.php")
//    Call<List<ModelPopular>> getPopular(@Query("index") int index);
//
//    @GET("API_search.php")
//    Call<List<ModelSearch>> getSearch(@Query("index") int index, @Query("input") String input);
//}
