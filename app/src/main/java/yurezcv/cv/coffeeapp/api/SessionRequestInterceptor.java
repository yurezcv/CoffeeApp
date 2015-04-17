package yurezcv.cv.coffeeapp.api;

import retrofit.RequestInterceptor;
import yurezcv.cv.coffeeapp.Values;

class SessionRequestInterceptor implements RequestInterceptor
{
    private static final String HEADER_KEY = "Authorization";

    @Override
    public void intercept(RequestInterceptor.RequestFacade request)
    {
        request.addHeader(HEADER_KEY, Values.API_KEY);
    }

}
