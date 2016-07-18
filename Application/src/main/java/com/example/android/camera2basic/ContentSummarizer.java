package com.example.android.camera2basic;

import android.util.ArrayMap;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import cz.msebera.android.httpclient.Header;
import com.example.android.camera2basic.Camera2BasicFragment.ToastDisplayer;

public class ContentSummarizer {
    private HttpClient httpClient;

    public ContentSummarizer(HttpClient client) {
        this.httpClient = client;
    }

    public void summarize(String urlToSummarize, final ToastDisplayer toastDisplayer) {
        Map<String, Object> requestParams = new ArrayMap<>();
        requestParams.put("url_input", urlToSummarize); // "https://en.wikipedia.org/wiki/2001_NBA_Finals");
        requestParams.put("sentence_count", 10);

        httpClient.post(
                "https://run.blockspring.com/api_v2/blocks/60fcacaff3e26678d4a78f35d824ca7c",
                requestParams,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            toastDisplayer.showToast(response.getString("Summary"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);

                        toastDisplayer.showToast("FAILURE 2");
                    }
                });
    }
}
