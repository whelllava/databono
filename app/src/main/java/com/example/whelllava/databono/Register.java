package com.example.whelllava.databono;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
    Button submit;
    EditText username , pass , email ;
    RadioButton male, female ;

    public class Writedata extends AsyncTask<String,Void,String>{

      String output = "";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Register.this, s + "connected", Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.30.2/register.php");
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                OutputStream out = httpURLConnection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out , "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                String mData = URLEncoder.encode("name" , "UTF-8") + "=" + URLEncoder.encode(strings[0],"UTF-8") + "&" +
                               URLEncoder.encode("email" , "UTF-8") + "=" + URLEncoder.encode(strings[1],"UTF-8") + "&" +
                               URLEncoder.encode("Pass" , "UTF-8") + "="+ URLEncoder.encode(strings[2],"UTF-8") + "&" +
                               URLEncoder.encode("gender" , "UTF-8") + "="+ URLEncoder.encode(strings[3],"UTF-8");

                bufferedWriter.write(mData);
                bufferedWriter.flush();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data != -1 ){
                    char current = (char) data;
                    output += current;
                    data = reader.read();
                }


            } catch (Exception e) {
                output = "error ...";
            }

            return output;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submit = (Button) findViewById(R.id.submit);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.Password);
        email = (EditText) findViewById(R.id.email);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String gender = "";

                if (male.isChecked()){
                    gender = "0";
                }else if (female.isChecked()){
                    gender = "1";
                }

                new Writedata().execute(username.getText().toString(),email.getText().toString(),pass.getText().toString(),gender);
                startActivity(new Intent(Register.this , Login.class));


            }
        });

    }
}
