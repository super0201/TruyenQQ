package network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static utils.Constants.ROOT_URL;
import static utils.Constants.URL_FOLDER;

public class ServiceAPI {

    public static String GET_DATA_COMIC = ROOT_URL + URL_FOLDER ;
    public static String BASE_URL = "https://mangahay.net/manga/";
    public static String FOLDER_USER = "api_for_user/";
    public static String FOLDER_CATE = "api_for_cate/";
    public static String FOLDER_COMIC = "api_for_comic/";

    public static <S> S userService(Class<S> serviceClass) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + FOLDER_USER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }

    public static <S> S cateService(Class<S> serviceClass) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + FOLDER_CATE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }


    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(GET_DATA_COMIC)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkAPI getDataComic() {
        return getRetrofitInstance().create( NetworkAPI.class );
    }
}
