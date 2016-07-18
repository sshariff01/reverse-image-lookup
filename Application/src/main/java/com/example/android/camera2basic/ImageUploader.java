package com.example.android.camera2basic;

import android.util.ArrayMap;

import com.example.android.camera2basic.Camera2BasicFragment.ToastDisplayer;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ImageUploader {
    private HttpClient httpClient;
    private ReverseImageSearcher reverseImageSearcher;
    private ToastDisplayer toastDisplayer;

    public ImageUploader(HttpClient client,
                         ReverseImageSearcher reverseImageSearcher,
                         ToastDisplayer toastDisplayer) {
        this.httpClient = client;
        this.reverseImageSearcher = reverseImageSearcher;
        this.toastDisplayer = toastDisplayer;
    }

    public void upload(String imageData) {
        httpClient.addHeader("Authorization", "Client-ID 64bba2f2f1ea3a7");
        Map<String, Object> requestParams = new ArrayMap<>();
        requestParams.put("image", imageData);
        httpClient.post(
                "https://api.imgur.com/3/upload",
                requestParams,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        String imageUrl = null;
                        try {
                            JSONObject data = response.getJSONObject("data");
                            imageUrl = data.getString("link");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (imageUrl == null) {
                            toastDisplayer.showToast("Something went wrong");
                        }
                        reverseImageSearcher.search(imageUrl, toastDisplayer);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        toastDisplayer.showToast("FAILURE 1");
                    }
                });
    }
}
