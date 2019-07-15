package com.example.pelculas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.pelculas.Modelos.Pelicula;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GsonBuilder gsonBuilder=new GsonBuilder();
    private Gson gson=gsonBuilder.create();
    private ArrayList<Pelicula> peliculas=new ArrayList<>();
    private RecyclerView recyclerView;
    private ListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        recyclerView=findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        obtenerDatos();

    }
    public void obtenerDatos(){
        AndroidNetworking.get("https://civ-movies.s3-sa-east-1.amazonaws.com/data.json")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i =0; i< response.length();i++){
                            try {
                                Pelicula pelicula=new Pelicula();
                                JSONObject object= response.getJSONObject(i);

                                JSONArray arrayGeneros= object.getJSONArray("genres");
                                JSONArray arrayRatings= object.getJSONArray("ratings");
                                JSONArray arrayActors= object.getJSONArray("actors");
                                ArrayList<String> tempGenres= new ArrayList<>();
                                ArrayList<Integer> tempRatings= new ArrayList<>();
                                ArrayList<String> tempActors= new ArrayList<>();
                                //Bucles
                                for (int j=0; j< arrayGeneros.length();j++){
                                    tempGenres.add(arrayGeneros.getString(j));
                                }
                                for (int j=0; j< arrayRatings.length();j++){
                                    tempRatings.add(arrayRatings.getInt(j));
                                }
                                for (int j=0; j< arrayActors.length();j++){
                                    tempActors.add(arrayActors.getString(j));
                                }
                                //Se agregan todos los subobjetos
                                pelicula.setTitle(object.getString("title"));
                                pelicula.setId(object.getInt("id"));
                                pelicula.setAverageRating(object.getInt("averageRating"));
                                pelicula.setRatings(tempRatings);
                                pelicula.setGenres(tempGenres);
                                pelicula.setActors(tempActors);
                                pelicula.setYear(object.getString("year"));
                                pelicula.setPosterUrl(object.getString("posterurl"));
                                pelicula.setImdbRating((float)object.getDouble("imdbRating"));

                                peliculas.add(pelicula);
                                //Log.d("tag","obj"+i+":::"+object.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter=new ListaAdapter(getApplicationContext(),peliculas);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
