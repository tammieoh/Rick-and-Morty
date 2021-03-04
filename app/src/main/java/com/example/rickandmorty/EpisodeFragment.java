package com.example.rickandmorty;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class EpisodeFragment extends Fragment {
    private View view;
    private TextView name, number, air_date, characters_ep;
    private String episode_name = "", episode_message = "", CHANNEL_ID = "";
    private Button more_info;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<String> character_names = new ArrayList<>(), character_images = new ArrayList<>();
    private String url = "https://rickandmortyapi.com/api/episode/";
    private String notification_url = "https://rickandmorty.fandom.com/wiki/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_characters);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);


        name = view.findViewById(R.id.textView_name);
        number = view.findViewById(R.id.textView_episodeNum);
        air_date = view.findViewById(R.id.textView_airDate);
        more_info = view.findViewById(R.id.button_moreInfo);
        characters_ep = view.findViewById(R.id.charactersEp_textView);

        sharedPreferences = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Random rand = new Random();
        int episode_num = rand.nextInt(sharedPreferences.getInt("episode_count", 0) + 1);

        url += Integer.toString(episode_num);

        // set the header because of the api endpoint
        client.addHeader("Accept", "application/json");
        // send a get request to the api url
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    String file = "file:///android_asset/images/RickandMorty_Logo.png";
//                    Picasso.get().load(json.get("image").toString()).into(imageView);
                    name.setText(json.get("name").toString());
                    episode_name = json.get("name").toString();
                    number.setText(json.get("episode").toString());
                    air_date.setText(json.get("air_date").toString());

                    episode_message = "To read more information about Episode " + json.get("episode").toString() +
                            ", please visit: https://rickandmorty.fandom.com/wiki/" + episode_name.replaceAll("\\s", "_");

                    notification_url += episode_name.replaceAll("\\s", "_");
                    if (json.getJSONArray("characters").length() == 0) {
                       characters_ep.setText(R.string.no_characters);
                    } else {
                        for (int i = 0; i < json.getJSONArray("characters").length(); i++) {
                            String temp_url = json.getJSONArray("characters").get(i).toString();
                            client.addHeader("Accept", "application/json");
                            // send a get request to the api url
                            client.get(temp_url, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    try {
                                        JSONObject json_character = new JSONObject(new String(responseBody));
//                                        character_names.add(json_character.get("name").toString());
//                                        character_images.add(json_character.get("image").toString());
//                                        for (int i = 0; i < characters.size(); i++) {
//                                            Character character = new Character(character_names.get(i),
//                                                    character_images.get(i));
//                                            characters.add(character);
//                                        }
                                        Character character = new Character(json_character.get("name").toString(),
                                                                    json_character.get("image").toString());
                                        characters.add(character);
                                        adapter = new CharacterAdapter(characters);
                                        adapter.notifyDataSetChanged();
                                        System.out.println(adapter.getItemCount());
                                        //attach the adapter to the recyclerview
                                        recyclerView.setAdapter(adapter);

                                        // set layoutmanager
                                        recyclerView.setLayoutManager(layoutManager);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Log.d("url", "error with getting character url");
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("url", "error with getting url");
            }
        });
        more_info.setOnClickListener(v -> createNotification(v));

        return view;
        //            @SuppressLint("SetTextI18n")
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    JSONObject json = new JSONObject(new String(responseBody));
//
//
////                    String file = "file:///android_asset/images/RickandMorty_Logo.png";
////                    Picasso.get().load(json.get("image").toString()).into(imageView);
//                    name.setText(json.get("name").toString());
//                    episode_name = json.get("name").toString();
//                    number.setText(json.get("episode").toString());
//                    air_date.setText(json.get("air_date").toString());
//
//                    episode_message = "To read more information about Episode " + json.get("episode").toString() +
//                            ", please visit: https://rickandmorty.fandom.com/wiki/" + episode_name.replaceAll("\\s", "_");
//
//                    notification_url += episode_name.replaceAll("\\s", "_");
//                    if(json.getJSONArray("characters").length() == 0) {
//                        System.out.println("None");
//                    }
//                    else {
//                        for (int i = 0; i < json.getJSONArray("characters").length(); i++) {
//                            String temp_url = json.getJSONArray("characters").get(i).toString();
//
//                            client.addHeader("Accept", "application/json");
//                            // send a get request to the api url
//                            client.get(temp_url, new AsyncHttpResponseHandler() {
//                                @Override
//                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                                    try {
//                                        JSONObject json_character = new JSONObject(new String(responseBody));
//                                        character_names.add(json_character.get("name").toString());
//                                        character_images.add(json_character.get("image").toString());
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                                    Log.d("url", "error with getting character url");
//                                }
//                            });
//                        }
//
//                        for (int i = 0; i < characters.size(); i++) {
//                            Character character = new Character(character_names.get(i),
//                                    character_images.get(i));
//                            characters.add(character);
//                        }
//                        adapter = new CharacterAdapter(characters);
//                        System.out.println(adapter.getItemCount());
//                        //attach the adapter to the recyclerview
//                        recyclerView.setAdapter(adapter);
//
//                        // set layoutmanager
//                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//                    }
//
//                     }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    Log.d("url", "error with getting character url");
//                }
//            });
//
//        more_info.setOnClickListener(v -> createNotification(v));
//
//        return view;

    }
//    public void attachAdapter(ArrayList<Character> characters) {
//        // create a location adapter
//        adapter = new CharacterAdapter(characters);
//        System.out.println(adapter.getItemCount());
//        //attach the adapter to the recyclerview
//        recyclerView.setAdapter(adapter);
//
//        // set layoutmanager
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//    }

    public void createNotification(View view) {
        int NOTIFICATION_ID = 234;
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(episode_name)
                .setContentText(episode_message)
                .setStyle(new NotificationCompat.BigTextStyle());

//        Intent resultIntent = new Intent(getContext(), MainActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notification_url));
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
//    public ArrayList<String> getCharacterInformation(String url) {
//        final ArrayList<String> results = new ArrayList<>();
//        client.get(url, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//
//                    JSONObject json = new JSONObject(new String(responseBody));
//                    results.add(json.get("name").toString());
//                    results.add(json.get("image").toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
//        return results;
//    }
}
