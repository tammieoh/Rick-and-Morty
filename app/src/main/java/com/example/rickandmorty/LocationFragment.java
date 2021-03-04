package com.example.rickandmorty;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

public class LocationFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    ArrayList<String> location_names = new ArrayList<>(), location_types = new ArrayList<>(), location_dimensions = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private String url = "https://rickandmortyapi.com/api/location";
    private static AsyncHttpClient client = new AsyncHttpClient();
//    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);
        recyclerView = view.findViewById(R.id.location_recyclerView);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("onclicklistener clicked");
//            }
//        });

//        sharedPreferences = getContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
//        location_names = new ArrayList<>();
//        location_types = new ArrayList<>();
//        location_dimensions = new ArrayList<>();
//        locations = new ArrayList<>();

        // set the header because of the api endpoint
        client.addHeader("Accept", "application/json");
//        adapter = new LocationAdapter(getContext(),locations);
//        recyclerView.setAdapter(adapter);
        // send a get request to the api url
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    for(int i = 0; i < json.getJSONArray("results").length(); i++) {
                        location_names.add(json.getJSONArray("results").getJSONObject(i).get("name").toString());
                        location_types.add(json.getJSONArray("results").getJSONObject(i).get("type").toString());
                        location_dimensions.add(json.getJSONArray("results").getJSONObject(i).get("dimension").toString());
                    }

                    for(int i = 0; i < location_names.size(); i++) {
                        Location location = new Location(location_names.get(i),
                                location_types.get(i),
                                location_dimensions.get(i));
                        locations.add(location);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.put

                    }
                    System.out.println(locations.get(0).getName());
                    adapter = new LocationAdapter(locations);
                    recyclerView.setAdapter(adapter);
//                    adapter.setNewList(locations);
//                    adapter.notifyDataSetChanged();
                    System.out.println(adapter.getItemCount());
                    //attach the adapter to the recyclerview
//                    recyclerView.setAdapter(adapter);
                    // set layoutmanager
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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




//        for(int i = 0; i < location_names.size(); i++) {
//            Location location = new Location(location_names.get(i),
//                    location_types.get(i),
//                    location_dimensions.get(i));
//            locations.add(location);
//        }
//        // create a location adapter
//        adapter = new LocationAdapter(locations);
//        System.out.println(adapter.getItemCount());
//        //attach the adapter to the recyclerview
//        recyclerView.setAdapter(adapter);
//
//        // set layoutmanager
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


//    public void attachAdapter(ArrayList<Location> locations) {
//        // create a location adapter
//        adapter = new LocationAdapter(locations);
//        System.out.println(adapter.getItemCount());
//        //attach the adapter to the recyclerview
//        recyclerView.setAdapter(adapter);
//
//        // set layoutmanager
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//    }
}
