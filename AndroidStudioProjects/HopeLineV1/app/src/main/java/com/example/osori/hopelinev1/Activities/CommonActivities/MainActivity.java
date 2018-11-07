package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.UserActivities.UserHomePageActivity;
import com.example.osori.hopelinev1.Activities.UserActivities.UserSignOnActivity;
import com.example.osori.hopelinev1.LoadingChatActivity;
import com.example.osori.hopelinev1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button btnGuest, btnUser, btnMentor;
    RadioGroup radioGroup;
    ArrayList<String> topicsChosen;
    TextView txtJson;
    private String TAG = "HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnGuest = (Button)findViewById(R.id.btn_guestSignOn);
        btnUser = (Button)findViewById(R.id.btn_userSignOn);
        btnMentor = (Button)findViewById(R.id.btn_mentorSignOn);
        radioGroup = (RadioGroup)findViewById(R.id.rb_topicList);
        topicsChosen = new ArrayList<String>();

        new JsonTask().execute("https://hopelineapi.azurewebsites.net/api/allresources/topics");
        btnMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged in Mentor", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, );
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged in User", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), UserHomePageActivity.class);
                Log.d(TAG,"Button Clicked");

                startActivity(intent);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadingChatActivity.class);
                if(topicsChosen.isEmpty()){
                    intent.putExtra("emptyTopics", true);
                }else{
                    intent.putExtra("emptyTopics", false);
                    intent.putStringArrayListExtra("Topics", topicsChosen);
                }
                startActivity(intent);
            }
        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray jsonArray = new JSONArray(buffer.toString());
                            Log.d("Response obj", jsonArray.get(0).toString());

                            for(int i = 0; i < jsonArray.length(); i++){
                                CheckBox checkBox = new CheckBox(MainActivity.this);
                                JSONObject jsonObject = new JSONObject(jsonArray.getJSONObject(i).toString());
                                checkBox.setText(jsonObject.get("name").toString());
                                checkBox.setTag(jsonObject.get("id").toString());
                                radioGroup.addView(checkBox);
                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if(isChecked){
                                            topicsChosen.add(checkBox.getTag().toString());
                                            Log.d("Checkbox", "Checked " + checkBox.getTag());
                                        }else{
                                            topicsChosen.remove(checkBox.getTag().toString());
                                            Log.d("Checkbox", "Not Checked " + checkBox.getTag());
                                        }
                                    }
                                    });
                                Log.d("Response obj loop", jsonObject.get("name").toString());
                            }
                        }catch (Exception e){
                            Log.d("Response", "Error, couldn't parse json: " + e);
                        }
                    }
                });
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}





