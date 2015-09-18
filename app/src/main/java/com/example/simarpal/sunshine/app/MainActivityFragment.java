package com.example.simarpal.sunshine.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.simarpal.sunshine.app.R.layout.list_item_forecast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] weatherArray = {"Today-sunny-88/63","Tomorrow-sunny-88/63","Monday-cloudy-88/63","Tuesday-sunny-88/63"
                ,"Wednesday-rsin-88/63","Friday-hail-88/63"};
        ArrayList<String> weatherData = new ArrayList<String>();
        weatherData.add("Today-sunny-88/63");
        weatherData.add("Tomorrow-sunny-88/63");
        weatherData.add("Monday-cloudy-88/63");
        weatherData.add("Tuesday-sunny-88/63");
        weatherData.add("Wednesday-rsin-88/63");
        weatherData.add("Friday-hail-88/63");
        ArrayAdapter mForecastAdapter = new ArrayAdapter<String>(getActivity(), list_item_forecast, R.id.list_item_forecast_textview,weatherData);
        ListView listView = (ListView) getView().findViewById(R.id.list_item_forecast);
        listView.setAdapter(mForecastAdapter);
    }
}
