package com.example.android.camera2basic;

import android.util.ArrayMap;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import cz.msebera.android.httpclient.Header;
import com.example.android.camera2basic.Camera2BasicFragment.ToastDisplayer;

public class ReverseImageSearcher {
    private HttpClient httpClient;
    private ContentSummarizer contentSummarizer;

    public ReverseImageSearcher(HttpClient client, ContentSummarizer contentSummarizer) {
        this.httpClient = client;
        this.contentSummarizer = contentSummarizer;
    }

    public void search(String urlToImage, final ToastDisplayer toastDisplayer) {
        Map<String, Object> requestParams = new ArrayMap<>();
        requestParams.put("image_url", urlToImage); //"http://i.imgur.com/h9KDYlh.jpg"); Iverson Pic

        httpClient.post(
                "https://run.blockspring.com/api_v2/blocks/reverse-image-search",
                requestParams,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray directMatches = response.getJSONArray("direct_matches");
                            fetchSummary(toastDisplayer, directMatches);
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


    private void fetchSummary(ToastDisplayer toastDisplayer, JSONArray matches) throws JSONException {
        contentSummarizer.summarize(matches.getString(0), toastDisplayer); //"https://en.wikipedia.org/wiki/2001_NBA_Finals"
    }
}
