package com.example.dayanidhi.meetutu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class Tutour extends AppCompatActivity {
String id,idt,name,na;
    private ProgressDialog progressDialog;
    public  AccountReg doc;
    TextView name1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutour);
        id = getIntent().getExtras().getString("Id");
        idt = getIntent().getExtras().getString("Id1");
        na = getIntent().getExtras().getString("na");
        new Ret_num().execute(id);
        name1=(TextView) findViewById(R.id.name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Snackbar.make(view, "Chat with ur Tutor", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
                Intent intent = new Intent(Tutour.this, MainActivity.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tutour.this, Complete.class);
                intent.putExtra("Id", id+"_"+idt);
                startActivity(intent);
            }
        });
    }
//

    //Checking username and password

    private class Ret_num extends AsyncTask<String, Integer, String> {


        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Tutour.this, "Message", "loading..");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String given_uname = params[0];
            try {
                CloudantClient client = ClientBuilder.account("7f1f486a-1104-47bd-add4-62663474ac15-bluemix")
                        .username("7f1f486a-1104-47bd-add4-62663474ac15-bluemix")
                        .password("93b4b2805dcaa6ecbe5277cd7baf4b9bd67d78750f78398a242cf1afabd0486b")
                        .build();

                Database db = client.database("testing", false);
                doc = db.find(AccountReg.class, given_uname);
                name = doc.First_name;

            } catch (Exception e) {
                return e.toString();
                //System.out.println("Error SMS "+e);

            }


            return "FAILED";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //  progressDialog.setProgress(Integer.parseInt("" + values));
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            // System.out.println(result);
            progressDialog.dismiss();

             Toast.makeText(getApplicationContext(), name+"", Toast.LENGTH_LONG).show();

            name1.setText(na);

            // Do things like hide the progress bar or change a TextView
        }


    }

}
