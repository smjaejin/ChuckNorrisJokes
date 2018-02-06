package com.example.seanmclane.chucknorrisjokes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by per6 on 1/29/18.
 */

public class HomeFragment extends Fragment {

    private TextView jokeText;
    private Joke joke;
    private List<Joke> jokeList;
    private ChuckNorrisJokesAPI api;
    private Button jokeNewButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        jokeList = new ArrayList<>();

        jokeText = rootView.findViewById(R.id.jokeTextView);

        jokeNewButton = rootView.findViewById(R.id.joke_button);
        jokeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newJoke();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChuckNorrisJokesAPI.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ChuckNorrisJokesAPI.class);


        newJoke();
        return rootView;
    }

    private void newJoke() {
        Call<Joke> call = api.getRandomJoke();
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                joke = response.body();
                Log.d("call", "onResponse: "+joke);
                if (!jokeList.contains(joke)){
                    jokeList.add(joke);
                    jokeText.setText(joke.toString());
                }
                else newJoke();

                MainActivity main = (MainActivity) getActivity();
                if (main != null) {
                    ArrayList list = (ArrayList) main.getJokeList();
                    list.add(0,joke);

                    main.setJokeList(list);
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
