package com.example.dayanidhi.meetutu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class Register extends AppCompatActivity {
    Button next;
    EditText first_name,last_name,email,number,password;
    String firstname,lastname,emailid,mobilenumber,passwordc;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name=(EditText) findViewById(R.id.first_name);
                last_name=(EditText) findViewById(R.id.last_name);
                email=(EditText) findViewById(R.id.input_email);
                number=(EditText) findViewById(R.id.mobile_number);
                password=(EditText) findViewById(R.id.input_password);
                firstname=first_name.getText().toString();
                lastname=last_name.getText().toString();
                emailid=email.getText().toString();
                mobilenumber=number.getText().toString();
                passwordc=password.getText().toString();
              new Register_cloudent().execute();
               // Intent intent = new Intent(Register.this, Login.class);
               // startActivity(intent);
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
    //Register connecting to cloudent

    private class Register_cloudent extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Register.this, "Message", "Registering...");

            //  progressDialog.show(SignUpActivity.this, "Message", "Sending OTP..");
            // Do something like display a progress bar
        }
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //  String myString = params[0];
            try {
                // Create a new CloudantClient instance for account endpoint example.cloudant.com

                CloudantClient client = ClientBuilder.account("7f1f486a-1104-47bd-add4-62663474ac15-bluemix")
                        .username("7f1f486a-1104-47bd-add4-62663474ac15-bluemix")
                        .password("93b4b2805dcaa6ecbe5277cd7baf4b9bd67d78750f78398a242cf1afabd0486b")
                        .build();

                Database db = client.database("testing", true);

// A Java type that can be serialized to JSON

                db.save(new AccountReg(firstname,lastname,emailid,mobilenumber,passwordc));


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
            Intent intent = new Intent(Register.this, Check.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();

            // Do things like hide the progress bar or change a TextView
        }
    }
}
