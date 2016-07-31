package com.example.android.learnmore;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import cz.msebera.android.httpclient.Header;

public class ContentSummarizer {
    private HttpClient httpClient;
    private final Activity activity;
    private Handler progressDialogHandler;

    public ContentSummarizer(HttpClient client, Activity activity, Handler progressDialogHandler) {
        this.httpClient = client;
        this.activity = activity;
        this.progressDialogHandler = progressDialogHandler;
    }

    public void summarize(String urlToSummarize, final ToastDisplayer toastDisplayer) {
        Map<String, Object> requestParams = new ArrayMap<>();
        requestParams.put("url_input", urlToSummarize);
        requestParams.put("sentence_count", 1);

        httpClient.post(
                "https://run.blockspring.com/api_v2/blocks/60fcacaff3e26678d4a78f35d824ca7c",
                requestParams,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Message message = progressDialogHandler.obtainMessage();
                        message.obj = "dismiss";
                        progressDialogHandler.sendMessage(message);

                        String summary = null;
                        try {
                            summary = response.getString("Summary");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toastDisplayer.showToast("Could not parse summary");
                        }

                        if (summary == null || summary.trim().length() == 0) {
                            toastDisplayer.showToast("Summary was empty");
                            return;
                        }

                        launchInformationActivity(summary);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Message message = progressDialogHandler.obtainMessage();
                        message.obj = "dismiss";
                        progressDialogHandler.sendMessage(message);

                        toastDisplayer.showToast("Request to Summarize Content Failed");
                    }
                });
    }

    private void launchInformationActivity(String data) {
        Intent intent = new Intent(this.activity, InformationActivity.class);
        intent.putExtra("data", data);
        this.activity.startActivity(intent);
    }
}
