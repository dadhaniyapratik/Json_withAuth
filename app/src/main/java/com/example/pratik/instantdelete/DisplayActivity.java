package com.example.pratik.instantdelete;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayActivity extends ListActivity {



    private static final String RESULT = "result";
    private static final String CURRENT_MAX_ID = "current_max_id";
    private static final String MESSAGE = "message";

    private static final String ERROR = "error";

    private static final String ID = "id";
    private static final String ID_TALLTABLES = "id_tAllTables";
    private static final String FDAYNUMBER = "fDayNumber";
    private static final String FIMAGEPRIMARY = "fImagePrimary";
    private static final String FIMAGESECONDARY = "fImageSecondary";
    private static final String FTITLE = "fTitle";
    private static final String FDESCRIPTION = "fDescription";
    private static final String FVERSION = "fVersion";
    private static final String FSHOWDESCRIPTION = "Show Description";

    private static String url = "http://52.51.41.49/api/virtual_games/index.php?action=read_training";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) findViewById(R.id.listView);

        new GetStudents().execute();


    }


    private ArrayList<HashMap<String, String>> ParseJSON(String json) {
        if (json != null) try {
// Hashmap for ListView
            ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

            JSONObject id = new JSONObject(json);
            String result = id.getString(RESULT);
            String currentmaxid = id.getString(CURRENT_MAX_ID);
            String message = id.getString(MESSAGE);

            //   JSONArray jArray = new JSONArray(json);
            JSONArray jArray = id.getJSONArray("data");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                {

                    JSONObject show_details = json_data.getJSONObject("all_table_data");
                    {


                        String error = "";

                        if (show_details.has(ERROR)) {
                            error = show_details.getString(ERROR);
                        }


                        String id1 = "";
                        if (json_data.has(ID)) {
                            id1 = json_data.getString(ID);
                        }

                        String idalltable = "";
                        if (json_data.has(ID_TALLTABLES)) {
                            idalltable = json_data.getString(ID_TALLTABLES);
                        }

                        String fdaynumber = "";
                        if (json_data.has(FDAYNUMBER)) {
                            fdaynumber = json_data.getString(FDAYNUMBER);
                        }

                        String fimageprimary = "";
                        if (json_data.has(FIMAGEPRIMARY)) {
                            fimageprimary = json_data.getString(FIMAGEPRIMARY);
                        }




                        String ftitle = "";
                        if (json_data.has(FTITLE)) {
                            ftitle = json_data.getString(FTITLE);
                        }

                        String fdescription = "";
                        if (json_data.has(FDESCRIPTION)) {
                            fdescription = json_data.getString(FDESCRIPTION);
                        }

                        String fversion = "";
                        if (json_data.has(FVERSION)) {
                            fversion = json_data.getString(FVERSION);
                        }


                        HashMap<String, String> student = new HashMap<String, String>();

// adding every child node to HashMap key => value

                        student.put(RESULT, result);
                        student.put(CURRENT_MAX_ID, currentmaxid);
                        student.put(MESSAGE, message);
                        student.put(ERROR, error);
                        student.put(ID, id1);
                        student.put(ID_TALLTABLES, idalltable);
                        student.put(FDAYNUMBER, fdaynumber);
                        student.put(FIMAGEPRIMARY, fimageprimary);
                        student.put(FTITLE, ftitle);
                        student.put(FDESCRIPTION, fdescription);
                        student.put(FVERSION,fversion );

//                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//
////Loading image from below url into imageView
//                        Glide.with(this)
//                                .load("http://52.51.41.49/api/virtual_games/images/primary_01.png")
//                                .into(imageView);
//
                       studentList.add(student);






                    }
                }
            }

            return studentList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }
    private class GetStudents extends AsyncTask<Void, Void, Void> {

        // Hashmap for ListView
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();


        //ProgressDialog proDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress loading dialog
           /* proDialog = new ProgressDialog(DisplayActivity.this);
            proDialog.setMessage("Please wait...");
            proDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... arg0) {
// Creating service handler class instance
            ServiceCall serviceCall = new ServiceCall();
            try {

// Making a request to url and getting response
                String jsonStr = serviceCall.doHTTPGetRequest(url);
                Log.v("jsonStr:", jsonStr);
                studentList = ParseJSON(jsonStr);

            } catch (Exception e) {
                e.printStackTrace();
                Log.v("Exception do in back:", e.getMessage() + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void requestresult) {
            super.onPostExecute(requestresult);

     //       ListView listView = (ListView)findViewById(R.id.listView);
            ListAdapter adapter;
            adapter = new SimpleAdapter(DisplayActivity.this, studentList,
                    R.layout.list_view, new String[]{RESULT,CURRENT_MAX_ID,MESSAGE,ERROR,ID, ID_TALLTABLES,FDAYNUMBER,FIMAGEPRIMARY,FTITLE,FDESCRIPTION,FVERSION},
                    new int[]{R.id.date, R.id.channelname, R.id.showtitle, R.id.showtime, R.id.showthumb, R.id.language, R.id.releasedate,R.id.imageView, R.id.actor, R.id.genre, R.id.createdby});

            setListAdapter(adapter);

//            AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    RelativeLayout relativeLayout = (RelativeLayout) view;
//                    TextView date = (TextView) relativeLayout.getChildAt(0);
//                }
//            };
//
//            listView.setOnItemClickListener(onItemClickListener);
        }
    }
}
