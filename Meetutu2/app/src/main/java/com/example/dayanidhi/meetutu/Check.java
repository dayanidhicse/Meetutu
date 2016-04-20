package com.example.dayanidhi.meetutu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class Check extends AppCompatActivity {
    EditText first_name;
    String skills,id;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        first_name=(EditText) findViewById(R.id.first_name);
       Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skills=first_name.getText().toString();
                Intent intent = new Intent(Check.this, Test.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//

    private class Register_cloudent extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Check.this, "Message", "Registering...");

            //  progressDialog.show(SignUpActivity.this, "Message", "Sending OTP..");
            // Do something like display a progress bar
        }
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //  String myString = params[0];
            try {
                // Create a new CloudantClient instance for account endpoint example.cloudant.com

                CloudantClient client = ClientBuilder.account("861e7e22-f244-4966-b974-94b43c911bd5-bluemix")
                        .username("861e7e22-f244-4966-b974-94b43c911bd5-bluemix")
                        .password("152e662e06fbe50463b7cf744250a4bc06c4f76647a332bb983da7029bb0cac2")
                        .build();

                Database db = client.database("testing", false);

// A Java type that can be serialized to JSON

                db.save(new AccountReg(skills,id));


// Create an Account and save it in the database


            }catch(Exception e){System.out.println(e.toString());
                Toast.makeText(getApplicationContext(), "Try After Sometime !!", Toast.LENGTH_LONG).show();
            }

            return "Done !!";
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
            progressDialog.dismiss();
            Intent intent = new Intent(Check.this, Test.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();

            // Do things like hide the progress bar or change a TextView
        }
    }
}
