package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

  Context context;
  List<Tweet> tweets;

  // Pass in the context and list of tweets
  public TweetsAdapter(Context context, List<Tweet> tweets) {
    this.context = context;
    this.tweets = tweets;
  }

  // For each row, inflate the layout for the tweet

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    // Get data at position
    Tweet tweet = tweets.get(position);

    // Bind tweet (data) at position that we get with the viewholder at position x
    holder.bind(tweet);
  }

  @Override
  public int getItemCount() {
    return tweets.size();
  }

  // Clean all elements of the recycler
  public void clear() {
    tweets.clear();
    notifyDataSetChanged();
  }

  // Add a list of items -- change to type used
  public void addAll(List<Tweet> list) {
    tweets.addAll(list);
    notifyDataSetChanged();
  }


  // Bind values based on position of element in list


  // Define a viewholder
  public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView tv_body;
    TextView tv_screen_name;
    TextView timestamp;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      imageView = itemView.findViewById(R.id.iv_profile_image);
      tv_body = itemView.findViewById(R.id.tv_body);
      tv_screen_name = itemView.findViewById(R.id.tv_screen_name);
      timestamp = itemView.findViewById(R.id.tv_timestamp);
    }

    public void bind(Tweet tweet) {
      tv_body.setText(tweet.body);
      tv_screen_name.setText(tweet.user.screen_name);
      String time = tweet.getFormattedTimestamp();
      timestamp.setText(time);
      Glide.with(context).load(tweet.user.public_image_url).into(imageView);
    }
  }
}
