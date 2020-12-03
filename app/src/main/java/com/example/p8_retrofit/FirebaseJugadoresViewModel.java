package com.example.p8_retrofit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseJugadoresViewModel extends AndroidViewModel {

    MutableLiveData<FirebaseJugadores.Jugadores> respuestaMutableLiveData = new MutableLiveData<>();

    public FirebaseJugadoresViewModel(@NonNull Application application) {
        super(application);
    }

    public void obtener(){
        FirebaseJugadores.api.obtener().enqueue(new Callback<FirebaseJugadores.Jugadores>() {
            @Override
            public void onResponse(@NonNull Call<FirebaseJugadores.Jugadores> call, @NonNull Response<FirebaseJugadores.Jugadores> response) {
                respuestaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FirebaseJugadores.Jugadores> call, @NonNull Throwable t) {}
        });
    }
}