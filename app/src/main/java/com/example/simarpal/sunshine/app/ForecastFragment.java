package com.example.simarpal.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.simarpal.sunshine.app.R.layout.list_item_forecast;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    ListView listView;
    public ForecastFragment() {
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_refresh)
        {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        listView = (ListView) getView().findViewById(R.id.list_item_forecast);
        listView.setAdapter(mForecastAdapter);
    }
}
class FetchWeatherTask extends AsyncTask<Void,Void,Void>
{
private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

            // Create the request to OpenWeatherMap, and open the connection

            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,"Toronto")
                    .appendQueryParameter(FORMAT_PARAM,"json")
                    .appendQueryParameter(UNITS_PARAM,"metric")
                    .appendQueryParameter(DAYS_PARAM,"7").build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            // Nothing to do.
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            Log.v(LOG_TAG, " Forecast JSON String : "+ forecastJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
