package com.example.seanmclane.chucknorrisjokes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by per6 on 1/25/18.
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.MyViewHolder> {

    private List<Joke> jokes;
    private Context context;

    public JokeAdapter(List<Joke> jokes, Context context) {
        this.jokes = jokes;
        this.context = context;
    }

    //creates the ViewHolder by inflating the layout and returning it
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.joke_item_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    //Assigns info from the joke object to each widget in the layout
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Joke joke = jokes.get(position);
        holder.jokeText.setText(joke.toString());
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView jokeText;

        public MyViewHolder(View itemView) {
            super(itemView);
            jokeText = itemView.findViewById(R.id.jokeTextView);
        }
    }

}
