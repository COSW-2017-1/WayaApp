package abcd.com.waya.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import abcd.com.waya.R;
import abcd.com.waya.adapters.RecyclerEventAdapter;
import abcd.com.waya.entities.DataEvent;
import abcd.com.waya.entities.singleton.DataEventSingleton;

/**
 * Created by PERSONAL on 30/03/2017.
 */

public class EventsFrag extends Fragment{

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    //Elementos del RecyclerView
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DataEventSingleton des = DataEventSingleton.getInstance();
    //Vista del fragmento
    View view;

    private static EventsFrag instance = null;

    public EventsFrag() {
    }

    public static EventsFrag getInstance() {
        if(instance == null) {
            instance = new EventsFrag();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_list, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //----------------------------------------------------------------------------------------------
        //Make call to AsyncTask
        try{
            des.getData().get(0);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_event_list);
            layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerEventAdapter(des.getData());
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            new AsyncFetch().execute();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(view.getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tCargando Eventos...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://wayafiesta.herokuapp.com/eventos");

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file


            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (200 == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<DataEvent> data = new ArrayList<>();

            pdLoading.dismiss();

            try {
                JSONArray jArray = new JSONArray(result);
                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataEvent eventData = new DataEvent();
                    eventData.title = json_data.getString("name");
                    eventData.eventDate = json_data.getLong("fecha");
                    data.add(eventData);
                }

                des.setData(data);

                // Setup and Handover data to recyclerview
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_event_list);
                layoutManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new RecyclerEventAdapter(data);
                recyclerView.setAdapter(adapter);
                //mAdapter = new AdapterFish(MainActivity.this, data);

            } catch (JSONException e) {
                System.out.println("ERROR JSON -> " + e.toString());
                Toast.makeText(getContext(), "ERROR // -> " + e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
}
