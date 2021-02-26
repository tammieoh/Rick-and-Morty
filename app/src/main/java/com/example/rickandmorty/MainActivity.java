package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String base_url = "https://rickandmortyapi.com/api";
    private TabLayout tabLayout;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView_title);
        tabLayout = findViewById(R.id.tabLayout);

        Random rand = new Random();
        int character_num = rand.nextInt(671) + 1;
        int episode_num = rand.nextInt(41) + 1;

        String file = "file:///android_asset/images/RickandMorty_Logo.png";
        Picasso.get().load(file).into(imageView);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tab", Integer.toString(tab.getPosition()));
                if(tab.getPosition() == 0) {
                    CharacterFragment characterFragment = new CharacterFragment();
                    loadFragment(characterFragment);
                }
                else if(tab.getPosition() == 1) {
                    LocationFragment locationFragment = new LocationFragment();
                    loadFragment(locationFragment);
                }
                else {
                    EpisodeFragment episodeFragment = new EpisodeFragment();
                    loadFragment(episodeFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}