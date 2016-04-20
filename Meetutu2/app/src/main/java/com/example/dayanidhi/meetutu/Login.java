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

public class Login extends AppCompatActivity {
Button login;
    EditText username,password;
    String user,pass,output;
    private ProgressDialog progressDialog;
    public  AccountReg doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        username=(EditText) findViewById(R.id.input_email);
        password=(EditText) findViewById(R.id.input_password);
        login =(Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                pass=password.getText().toString();
                new Ret_num().execute(user);
              // Intent intent = new Intent(Login.this, MainActivity.class);
             //  startActivity(intent);
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

    //Checking username and password

    private class Ret_num extends AsyncTask<String, Integer, String> {


        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Login.this, "Message", "loading..");

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
                output = doc.Password;
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

            if(output.equals(pass))
            {
                Toast.makeText(getApplicationContext(), "Login Sucessfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, MapsActivity.class);
                intent.putExtra("Id", user);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }

            // Do things like hide the progress bar or change a TextView
        }


    }

}
