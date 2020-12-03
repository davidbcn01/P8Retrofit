package com.example.p8_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class FirebaseJugadores {

    class Jugadores {
        List<Jugador> documents;
    }

    class Jugador {
        String name;
        Fields fields;
    }

    class Fields {
        Value Nombre;
        Value Carta;
        Value Equipo;
    }

    class Value {
        String stringValue;
    }


    public static Api api = new Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/v1/projects/p8retrofit-bb870/databases/(default)/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("Jugadores")
        Call<Jugadores> obtener();
    }
}




