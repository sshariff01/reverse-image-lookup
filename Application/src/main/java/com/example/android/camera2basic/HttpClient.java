package com.example.android.camera2basic;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.Map;

public class HttpClient extends AsyncHttpClient {
    public void post(String url, Map<String, Object> params, ResponseHandlerInterface responseHandler) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", "702eb4cc4f9a58e0861da8802d96ed08");
        for (Map.Entry<String, Object> param : params.entrySet()) {
            requestParams.put(param.getKey(), param.getValue());
        }

        this.post(null, url, requestParams, responseHandler);
    }
}
