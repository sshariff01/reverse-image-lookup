package com.example.android.learnmore;

import android.util.ArrayMap;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import cz.msebera.android.httpclient.Header;

public class ContentSummarizer {
    private HttpClient httpClient;
    private TextSynthesizer textSynthesizer;

    public ContentSummarizer(HttpClient client, TextSynthesizer textSynthesizer) {
        this.httpClient = client;
        this.textSynthesizer = textSynthesizer;
    }

    public void summarize(String urlToSummarize, final ToastDisplayer toastDisplayer) {
        Map<String, Object> requestParams = new ArrayMap<>();
        requestParams.put("url_input", urlToSummarize);
        requestParams.put("sentence_count", 5);

        httpClient.post(
                "https://run.blockspring.com/api_v2/blocks/60fcacaff3e26678d4a78f35d824ca7c",
                requestParams,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String summary = response.getString("Summary");
                            toastDisplayer.showToast(summary);
                            synthesizeText(summary);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);

                        toastDisplayer.showToast("FAILURE 3");
                    }
                });
    }

    private void synthesizeText(String text) {
        textSynthesizer.synthesize(text);
    }
}
