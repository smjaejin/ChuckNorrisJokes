package com.example.seanmclane.chucknorrisjokes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Joke> jokeList;
    private FragmentManager fm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new HistoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("array",jokeList);
                    fragment.setArguments(bundle);
                    break;
                case R.id.navigation_notifications:
                    break;

            }

            fm = getSupportFragmentManager();
            Log.d("main", "onNavigationItemSelected: " +(fragment != null));
            if (fragment != null){
                fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        jokeList = getJokeListFromFile();
        Log.d("", "onCreate: "+jokeList);
        if (jokeList == null)
        jokeList = new ArrayList<>();

        Fragment fragment = new HomeFragment();
        Log.d("main", "onCreate: "+(fragment != null));
        if (fragment != null){
            fm.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        Joke joke = intent.getParcelableExtra("joke");
        if(joke!= null) {
            jokeList.add(joke);
        }
        saveJokeList();
        super.onResume();
    }

    @Override
    protected void onPause() {
        saveJokeList();
        super.onPause();
    }

    private void saveJokeList() {
        Gson gson = new Gson();
        String list = gson.toJson(jokeList);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getBaseContext().openFileOutput("joke_list.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(list);
            outputStreamWriter.close();
            Log.d("Success", "writeToFile: write successful: "  + list);
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }



    public List<Joke> getJokeList() {
        return jokeList;
    }

    public void setJokeList(ArrayList<Joke> list){
        jokeList = list;
    }


    public ArrayList<Joke> getJokeListFromFile() {
        Gson gson = new Gson();
        String text = "";

        try {
            String yourFilePath = getBaseContext().getFilesDir() + "/joke_list.txt";
            File yourFile = new File(yourFilePath);
            InputStream inputStream = new FileInputStream(yourFile);
            StringBuilder stringBuilder = new StringBuilder();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                text = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "readFromFile File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        ArrayList<Joke> jokeList =(ArrayList<Joke>) gson.fromJson(text, ArrayList.class);
        return jokeList;
    }
}
