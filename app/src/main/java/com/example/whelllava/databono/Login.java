package com.example.whelllava.databono;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {
Button register , signin;
EditText user , pass ;

    public class recivedata extends AsyncTask<String,Void,String> {


        String output = "";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
            if (s.equals("Success")){
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this , showit.class));
            }else{
                Toast.makeText(Login.this, "Invalid Data", Toast.LENGTH_SHORT).show();

            }


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL("http://192.168.30.2/Readfromregister.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream out = httpURLConnection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                String tempdata = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8") + "&" +
                        URLEncoder.encode("Pass", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
                bufferedWriter.write(tempdata);
                bufferedWriter.flush();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1){
                    char current = (char) data;

                    output += current;

                    data = reader.read();

                }


            }
            catch (Exception e){
                output = "error";
            }


            return output;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         register = (Button) findViewById(R.id.go_register);
         signin = (Button) findViewById(R.id.sign_in);
         user = (EditText) findViewById(R.id.usernameasshole);
         pass = (EditText) findViewById(R.id.editText6);
         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(Login.this , Register.class));

             }
         });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((user.getText().toString()).isEmpty() || (pass.getText().toString()).isEmpty()){
                    Toast.makeText(Login.this, "plz Write ur information", Toast.LENGTH_SHORT).show();
                }else {
                    new recivedata().execute(user.getText().toString(), pass.getText().toString());



                }
            }
        });

    }
}
