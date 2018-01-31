package com.example.whelllava.databono;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class showit extends AppCompatActivity {


    RecyclerView recyclerView;

    public class retrievedata extends AsyncTask<String, Void, String> {

        String output = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.30.2/retrievedata.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    output += current;

                    data = reader.read();

                }


            } catch (Exception e) {
                output = "error";
            }
            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(showit.this, s, Toast.LENGTH_LONG).show();
            String[] users = s.split("#");
            ArrayList<Model> modelArrayList = new ArrayList<>();
            Model model;

            for (int i = 0; i < users.length; i++) {
                String[] array = users[i].split(";");
                model = new Model();
                model.setName(array[0]);
                model.setEmail(array[1]);
                if (array[2].equals("0")) {
                    model.setGender(R.drawable.male);
                } else {
                    model.setGender(R.drawable.female);
                }

                modelArrayList.add(model);

            }

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(showit.this));
            Adabter adabter = new Adabter(modelArrayList);
            recyclerView.setAdapter(adabter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showit);
        new retrievedata().execute();


    }
}

class Adabter extends RecyclerView.Adapter<Adabter.ViewHolder> {
    ArrayList<Model> model = new ArrayList<>();


    public Adabter(ArrayList<Model> model) {
        this.model = model;

    }

    @Override
    public Adabter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_items, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.readuser.setText(model.get(position).getName());
        holder.emaiuser.setText(model.get(position).getEmail());

        switch (model.get(position).getGender()){
            case 0:
                holder.genderimg.setImageResource(R.drawable.male);
                break;
            case 1:
                holder.genderimg.setImageResource(R.drawable.female);
        }
        holder.genderimg.setImageResource(model.get(position).getGender());
        holder.profileimg.setImageResource(R.drawable.ic_launcher_background);


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView readuser, emaiuser;
        ImageView profileimg, genderimg;

        public ViewHolder(View itemView) {
            super(itemView);
            readuser = (TextView) itemView.findViewById(R.id.username_read);
            emaiuser = (TextView) itemView.findViewById(R.id.email_read);
            profileimg = (ImageView) itemView.findViewById(R.id.profile_img);
            genderimg = (ImageView) itemView.findViewById(R.id.gender_view);
        }
    }
}
