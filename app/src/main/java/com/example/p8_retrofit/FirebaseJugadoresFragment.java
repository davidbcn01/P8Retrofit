package com.example.p8_retrofit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.p8_retrofit.databinding.FragmentFirebasejugadoresBinding;
import com.example.p8_retrofit.databinding.ViewholderContenidoBinding;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class FirebaseJugadoresFragment extends Fragment {
    private FragmentFirebasejugadoresBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentFirebasejugadoresBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseJugadoresViewModel firebaseJugadoresViewModel = new ViewModelProvider(this).get(FirebaseJugadoresViewModel.class);

        firebaseJugadoresViewModel.obtener();

        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        firebaseJugadoresViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<FirebaseJugadores.Jugadores>() {
            @Override
            public void onChanged(FirebaseJugadores.Jugadores respuesta) {
                contenidosAdapter.establecerListaContenido(respuesta.documents);
                respuesta.documents.forEach(contenido -> Log.e("ABCD", contenido.fields.Nombre.stringValue + ", " + contenido.fields.Equipo.stringValue + ", " + contenido.fields.Carta.stringValue));
            }
        });
    }

    static class ContenidoViewHolder extends ViewHolder {
        ViewholderContenidoBinding binding;

        public ContenidoViewHolder(@NonNull ViewholderContenidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    class ContenidosAdapter extends RecyclerView.Adapter<ContenidoViewHolder>{
        List<FirebaseJugadores.Jugador> jugadoresList;

        @NonNull
        @Override
        public ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContenidoViewHolder(ViewholderContenidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContenidoViewHolder holder, int position) {
            FirebaseJugadores.Jugador jugador = jugadoresList.get(position);

            holder.binding.nombre.setText(jugador.fields.Nombre.stringValue);
            holder.binding.equipo.setText(jugador.fields.Equipo.stringValue);
            Glide.with(requireActivity()).load(jugador.fields.Carta.stringValue).into(holder.binding.carta);
        }

        @Override
        public int getItemCount() {
            return jugadoresList == null ? 0 : jugadoresList.size();
        }

        void establecerListaContenido(List<FirebaseJugadores.Jugador> jugadoresList){
            this.jugadoresList = jugadoresList;
            notifyDataSetChanged();
        }
    }
}
