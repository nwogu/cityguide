package com.gabrielnwogu.cityguide;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    protected PreferenceManager pref;

    protected final String api_key = "AIzaSyB8btDO1Temu--H1hkk2kWX8u2N-l-PBkc";

    protected final String base_api = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.pref = new PreferenceManager(getContext());

        Spinner city_spinner = (Spinner) view.findViewById(R.id.spinner_city);
        Spinner category_spinner = (Spinner) view.findViewById(R.id.spinner_category);
        EditText keyword = (EditText) view.findViewById(R.id.editText);

        city_spinner.setOnItemSelectedListener(this);
        category_spinner.setOnItemSelectedListener(this);

        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String keyword_input = keyword.getText().toString();
                getPref().setKeywordInput(keyword_input);


                ProgressDialog pDialog = new ProgressDialog(getContext());
                // Showing progress dialog before making http request
                pDialog.setMessage("Loading Places...");
                pDialog.show();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = getMapsUrl();

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                getPref().setMapsResponse(response);
                                pDialog.dismiss();

                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);
            }
        });

        Spinner spinnerCity = view.findViewById(R.id.spinner_city);
        Spinner spinnerCategory = view.findViewById(R.id.spinner_category);

        String jsonCities = loadFileFromAsset("cities.json");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<City>>(){}.getType();

        ArrayList<City> cities =  gson.fromJson(jsonCities, type);

        String[] categories = {"hospitals", "gyms", "schools", "churches"};

        ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(getContext(),
                android.R.layout.simple_spinner_item, cities);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categories);

        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(cityAdapter);
        spinnerCategory.setAdapter(categoriesAdapter);
    }


    public String loadFileFromAsset(String filename) {
        String content = null;
        try {

            InputStream is = getContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            content = new String(buffer,"UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return content;
    }

    protected String getMapsUrl()
    {
        String city = getPref().getCitySelected();
        String category = getPref().getCategorySelected();
        String keyword = getPref().getKeywordInput();

        return base_api + category + "+in+" + city + "+" + keyword + "&key=" + api_key;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        String selected_item = parent.getItemAtPosition(pos).toString();

        switch (parent.getId()){
            case R.id.spinner_city:
                this.getPref().setCitySelected(selected_item);
                break;
            case R.id.spinner_category:
                this.getPref().setCategorySelected(selected_item);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    protected PreferenceManager getPref()
    {
        return this.pref;
    }
}