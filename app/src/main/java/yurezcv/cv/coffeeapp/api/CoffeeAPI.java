package yurezcv.cv.coffeeapp.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import yurezcv.cv.coffeeapp.types.Coffee;

public interface CoffeeAPI {

    @GET("/api/coffee/")
    void getCoffee(Callback<List<Coffee>> callback);

    @GET("/api/coffee/{id}")
    void getCoffee(@Path("id") String id, Callback<Coffee> callback);

}
