package yurezcv.cv.coffeeapp.bus;

public class ServerErrorEvent {

    private final String errorMessage;

    public ServerErrorEvent(String message) {
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
