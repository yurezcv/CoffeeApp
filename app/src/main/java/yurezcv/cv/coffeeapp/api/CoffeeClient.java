package yurezcv.cv.coffeeapp.api;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import yurezcv.cv.coffeeapp.Values;

public class CoffeeClient {

    private static final String TAG = CoffeeClient.class.getSimpleName();
    private CoffeeAPI coffeeAPI;

    public CoffeeClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC).setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        if (Values.DEBUG)
                            Log.i(TAG, msg);
                    }
                })
                .setEndpoint(Values.API_URL)
                .setConverter(new JacksonConverter())
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();

        coffeeAPI = restAdapter.create(CoffeeAPI.class);
    }

    public CoffeeAPI getAPIClient() {

        return coffeeAPI;
    }

}
