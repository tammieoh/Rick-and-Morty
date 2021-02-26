package com.example.rickandmorty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CharacterFragment extends Fragment {

    private View view;
    private TextView name, status, species, gender, origin, location, episodes;
    private ImageView imageView;
    private String url = "https://rickandmortyapi.com/api/character/";
    private static AsyncHttpClient client = new AsyncHttpClient();

//    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);

        name = view.findViewById(R.id.textView_name);
        status = view.findViewById(R.id.textView_status);
        species = view.findViewById(R.id.textView_species);
        gender = view.findViewById(R.id.textView_gender);
        origin = view.findViewById(R.id.textView_origin);
        location = view.findViewById(R.id.textView_location);
        episodes = view.findViewById(R.id.textView_episodes);
        imageView = view.findViewById(R.id.imageView_character);

//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Random rand = new Random();
        int character_num = rand.nextInt(671) + 1;

        url += Integer.toString(character_num);

        // set the header because of the api endpoint
        client.addHeader("Accept", "application/json");
        // send a get request to the api url
        client.get(url, new AsyncHttpResponseHandler() {

//            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    name.setText(json.get("name").toString());
//                    String file = "file:///android_asset/images/RickandMorty_Logo.png";
                    Picasso.get().load(json.get("image").toString()).into(imageView);

                    status.setText(getString(R.string.status_textView, json.get("status").toString()));
                    species.setText(getString(R.string.species_textView, json.get("species").toString()));
                    gender.setText(getString(R.string.gender_textView, json.get("gender").toString()));
                    origin.setText(getString(R.string.origin_textView, json.getJSONObject("origin").get("name").toString()));
                    location.setText(getString(R.string.location_textView, json.getJSONObject("location").get("name").toString()));

//                    ArrayList<String> episodes = new ArrayList<>();
                    String link = "";
                    String episode_String = getResources().getString(R.string.episodes_textView) + " ";
                    // 42
                    for(int i = 0; i < json.getJSONArray(("episode")).length(); i++) {
                        link = json.getJSONArray("episode").get(i).toString();
                        if(i == json.getJSONArray(("episode")).length() - 1) {
                            episode_String += link.substring(40);
                        }
                        else {
                            episode_String += link.substring(40) + ", ";
                        }

                    }
                    episodes.setText(episode_String);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("url", url);
                Toast.makeText(getActivity(), "Error in GET REQUEST", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}
