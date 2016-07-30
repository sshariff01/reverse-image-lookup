package com.example.android.learnmore;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.Map;

public class HttpClient extends AsyncHttpClient {
    public void post(String url, Map<String, Object> params, ResponseHandlerInterface responseHandler) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", "br_34928_bd7a6d5cafe3df0da3ac87bc6e30ea28cbdb13c8");
        for (Map.Entry<String, Object> param : params.entrySet()) {
            requestParams.put(param.getKey(), param.getValue());
        }

        this.post(null, url, requestParams, responseHandler);
    }
}
