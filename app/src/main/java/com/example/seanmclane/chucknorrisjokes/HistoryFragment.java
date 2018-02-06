package com.example.seanmclane.chucknorrisjokes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by per6 on 1/25/18.
 */

public class HistoryFragment extends android.support.v4.app.Fragment{


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Joke> jokeList;
    private JokeAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d("HistoryFragment", "onCreateView: HIStory Fragment");
        View rootView = inflater.inflate(R.layout.history_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        jokeList = ((MainActivity) getActivity()) .getJokeList();
        Log.d("history", "onCreateView: "+jokeList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new JokeAdapter(jokeList,getContext());
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
