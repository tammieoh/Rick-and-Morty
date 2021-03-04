package com.example.rickandmorty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String base_url = "https://rickandmortyapi.com/api";
    private String character_url = "https://rickandmortyapi.com/api/character";
    private String episode_url = "https://rickandmortyapi.com/api/episode";
//    private String location_url = private String url = "https://rickandmortyapi.com/api/location";
//    ArrayList<String> location_names = new ArrayList<>(), location_types = new ArrayList<>(), location_dimensions = new ArrayList<>();
//    private ArrayList<Location> locations = new ArrayList<>();
    private TabLayout tabLayout;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        imageView = findViewById(R.id.imageView_title);
        tabLayout = findViewById(R.id.tabLayout);

//        Random rand = new Random();
//        int character_num = rand.nextInt(671) + 1;
//        int episode_num = rand.nextInt(41) + 1;

        String file = "file:///android_asset/images/RickandMorty_Logo.png";
        Picasso.get().load(file).into(imageView);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tab", Integer.toString(tab.getPosition()));
                if(tab.getPosition() == 0) {
                    HomeFragment homeFragment = new HomeFragment();
                    loadFragment(homeFragment);
                }
                else if(tab.getPosition() == 1) {
                // set the header because of the api endpoint
                    client.addHeader("Accept", "application/json");
                    // send a get request to the api url
                    client.get(character_url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject json = new JSONObject(new String(responseBody));
                                String count = json.getJSONObject("info").getString("count");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Log.d("count", count);
                                editor.putInt("character_count", Integer.parseInt(count));
                                editor.apply();
                                CharacterFragment characterFragment = new CharacterFragment();
                                loadFragment(characterFragment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("url", base_url);
                        }
                    });
                }
                else if(tab.getPosition() == 2) {
                    // set the header because of the api endpoint
//                    client.addHeader("Accept", "application/json");
//                    // send a get request to the api url
//                    client.get(url, new AsyncHttpResponseHandler() {
//
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                            try {
//                                JSONObject json = new JSONObject(new String(responseBody));
//                                for (int i = 0; i < json.getJSONArray("results").length(); i++) {
//                                    location_names.add(json.getJSONArray("results").getJSONObject(i).get("name").toString());
//                                    location_types.add(json.getJSONArray("results").getJSONObject(i).get("type").toString());
//                                    location_dimensions.add(json.getJSONArray("results").getJSONObject(i).get("dimension").toString());
//                                }
//
//                                for (int i = 0; i < location_names.size(); i++) {
//                                    Location location = new Location(location_names.get(i),
//                                            location_types.get(i),
//                                            location_dimensions.get(i));
//                                    locations.add(location);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                        }

                        LocationFragment locationFragment = new LocationFragment();
                        loadFragment(locationFragment);
//                    });
                }
                else {
                    // set the header because of the api endpoint
                    client.addHeader("Accept", "application/json");
                    // send a get request to the api url
                    client.get(episode_url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONObject json = new JSONObject(new String(responseBody));
                                String count = json.getJSONObject("info").getString("count");
                                editor.putInt("episode_count", Integer.parseInt(count));
                                editor.apply();
                                EpisodeFragment episodeFragment = new EpisodeFragment();
                                loadFragment(episodeFragment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("url", base_url);
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    // set the header because of the api endpoint
                    client.addHeader("Accept", "application/json");
                    // send a get request to the api url
                    client.get(character_url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONObject json = new JSONObject(new String(responseBody));
                                String count = json.getJSONObject("info").getString("count");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Log.d("count", count);
                                editor.putInt("character_count", Integer.parseInt(count));
                                editor.apply();
                                CharacterFragment characterFragment = new CharacterFragment();
                                loadFragment(characterFragment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("url", base_url);
                        }
                    });
                }
                else if(tab.getPosition() == 1) {
                    LocationFragment locationFragment = new LocationFragment();
                    loadFragment(locationFragment);
                }
                else {
                    // set the header because of the api endpoint
                    client.addHeader("Accept", "application/json");
                    // send a get request to the api url
                    client.get(episode_url, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONObject json = new JSONObject(new String(responseBody));
                                String count = json.getJSONObject("info").getString("count");
                                editor.putInt("episode_count", Integer.parseInt(count));
                                editor.apply();
                                EpisodeFragment episodeFragment = new EpisodeFragment();
                                loadFragment(episodeFragment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("url", base_url);
                        }
                    });
                }
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
