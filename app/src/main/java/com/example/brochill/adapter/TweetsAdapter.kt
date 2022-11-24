package com.example.brochill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brochill.dataClass.TweetResponse
import com.example.brochill.R

class TweetsAdapter(private val tweets: List<TweetResponse>) : RecyclerView.Adapter<TweetsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tweet_design,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(tweets[position])
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tweetText : TextView = itemView.findViewById(R.id.tweetsList)

        fun bindView(tweetResponse: TweetResponse) {
            tweetText.text = tweetResponse.mTweet
        }
    }
}