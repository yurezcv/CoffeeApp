package yurezcv.cv.coffeeapp.api;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import yurezcv.cv.coffeeapp.bus.BusProvider;
import yurezcv.cv.coffeeapp.bus.ServerErrorEvent;
import yurezcv.cv.coffeeapp.bus.ServerResponseEvent;
import yurezcv.cv.coffeeapp.types.Coffee;

public class RequestsManager {

    private static final String TAG = RequestsManager.class.getSimpleName();

    private CoffeeAPI mCoffeeAPI;

    public RequestsManager() {
        CoffeeClient client = new CoffeeClient();
        mCoffeeAPI = client.getAPIClient();
    }

    public void getCoffeeList() {
        Callback<List<Coffee>> callback = new Callback<List<Coffee>>() {
            @Override
            public void success(List<Coffee> coffee, Response response) {
                BusProvider.getInstance().post(new ServerResponseEvent(coffee));
            }

            @Override
            public void failure(RetrofitError error) {
                handleError(error);
            }
        };
        mCoffeeAPI.getCoffee(callback);
    }

    public void getCoffeeById(String id) {
        Callback<Coffee> callback = new Callback<Coffee>() {
            @Override
            public void success(Coffee coffee, Response response) {
                BusProvider.getInstance().post(new ServerResponseEvent(coffee));
            }

            @Override
            public void failure(RetrofitError error) {
                handleError(error);
            }
        };
        mCoffeeAPI.getCoffee(id, callback);
    }

    // Error handling method
    private void handleError(RetrofitError error) {
        BusProvider.getInstance().post(new ServerErrorEvent(error.getMessage()));
    }
}
