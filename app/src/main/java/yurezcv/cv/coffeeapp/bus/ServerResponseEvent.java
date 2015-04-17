package yurezcv.cv.coffeeapp.bus;

import java.util.ArrayList;
import java.util.List;

import yurezcv.cv.coffeeapp.types.Coffee;

public class ServerResponseEvent {

    private List<Coffee> data;

    public ServerResponseEvent(List<Coffee> data) {
        this.data = data;
    }

    public ServerResponseEvent(Coffee item) {
        this.data = new ArrayList<>();
        data.add(item);
    }

    public List<Coffee> getData() {
        return data;
    }

}
