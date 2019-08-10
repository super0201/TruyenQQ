package network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

<<<<<<< Updated upstream
import static utils.Constants.ROOT_URL;
import static utils.Constants.URL_FOLDER;

public class ServiceAPI {

=======
import static utils.Constant.ROOT_URL;
import static utils.Constant.URL_FOLDER;

public class ServiceAPI {
>>>>>>> Stashed changes
    public static String GET_DATA_COMIC = ROOT_URL + URL_FOLDER ;

    public static <S> S createService(Class<S> serviceClass) {

        OkHttpClient httpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mangahay.net/manga/api_for_user/")
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
