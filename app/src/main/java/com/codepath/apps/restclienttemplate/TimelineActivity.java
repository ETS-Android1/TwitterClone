package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

  public static final String TAG = "TimeLineActivity";
  TwitterClient client;
  RecyclerView rv_tweets;
  List<Tweet> tweets;
  TweetsAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    client = TwitterApp.getRestClient(this);

    // Find the recycler view
    rv_tweets = findViewById(R.id.rv_tweets);

    // Init the list of tweets
    tweets = new ArrayList<>();
    adapter = new TweetsAdapter(this, tweets);

    // Configure the recyclerview: layoutmanager and adapter
    rv_tweets.setLayoutManager(new LinearLayoutManager(this));
    rv_tweets.setAdapter(adapter);

    populateHomeTimeline();
  }

  private void populateHomeTimeline() {
    client.getHomeTimeline(new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Headers headers, JSON json) {
        Log.i(TAG,"onSuccess " + json.toString());
        JSONArray jsonArray = json.jsonArray;
        try {
          tweets.addAll(Tweet.fromJsonArray(jsonArray));
          adapter.notifyDataSetChanged();
        } catch (JSONException e) {
          Log.e(TAG, "Json exception", e);
        }
      }

      @Override
      public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
        Log.i(TAG,"onFailure " + response, throwable);
      }
    });
  }
}