package com.gabrielnwogu.citihelp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    protected PreferenceManager pref;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.pref = new PreferenceManager(getContext());

        String mapsResponse = pref.getMapsResponse();

        ArrayList<Place> data = new ArrayList<Place>();

        try {
            JSONObject obj = new JSONObject(mapsResponse);
            JSONArray results = obj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                String name = results.getJSONObject(i).getString("name");
                String address = results.getJSONObject(i).getString("formatted_address");
                JSONArray photos = results.getJSONObject(i).getJSONArray("photos");
                String photo_reference = photos.getJSONObject(0).getString("photo_reference");
                data.add(new Place(name, address, photo_reference));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CustomAdapter adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);


    }
}