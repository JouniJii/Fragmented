package com.example.fragmented;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.IbuttonsListener{

    private ConnectivityManager cm;
    private Context context;
    private Boolean isNet;
    private playerAdapter adapter;
    public ArrayList<playerObj> players = new ArrayList<>();
    private RequestQueue queue = null;
    private DBHelper sqldb;


    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (context == null) {
            context = getApplicationContext();
        }

        isNet = isInternet(context);
        if(isNet == false) {
            Toast.makeText(context, "No network available.", Toast.LENGTH_SHORT).show();
        }

        sqldb = new DBHelper(context);
        sqldb.deleteTable();
    }

    private void doJsonQuery() {
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        String url = "https://webd.savonia.fi/home/ktkoiju/j2me/test_json.php?dates&delay=1";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            getDataFromResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    public void getDataFromResponse (JSONArray response) throws JSONException {

        for (int i=0; i < response.length(); i++ ) {
            JSONObject o = response.getJSONObject(i);
            String pvm = o.getString("pvm");
            String nimi = o.getString("nimi");
            sqldb.addData(nimi, pvm);
        }
        Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show();
    }

    private boolean isInternet(Context context) {

        final Network[] allNetworks;
        cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        allNetworks = cm.getAllNetworks();
        return (allNetworks != null);
    }

    // for interface
    @Override
    public void onGetDataClicked() {
        sqldb.deleteTable();
        doJsonQuery();
    }

    // for interface
    @Override
    public void onShowDataClicked() {
        players = sqldb.getAllData();

        ListViewFragment listViewFragment = (ListViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentListView);
        listViewFragment.dataToFragment(players);

    }
}