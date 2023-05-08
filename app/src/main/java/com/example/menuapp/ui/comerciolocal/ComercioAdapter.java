package com.example.menuapp.ui.comerciolocal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ComercioAdapter extends FirestoreRecyclerAdapter<Comercio, ComercioAdapter.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ComercioAdapter(@NonNull FirestoreRecyclerOptions<Comercio> options) {
        super( options );
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Comercio Comercio) {
        viewHolder.nombre.setText(Comercio.getNombre());
        viewHolder.correo.setText(Comercio.getCorreo());
        viewHolder.telefono.setText(Comercio.getTelefono());
        viewHolder.descripcion.setText(Comercio.getDescripcion());
        viewHolder.tipo.setText(Comercio.getTipo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, correo, telefono, descripcion, tipo;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            nombre = itemView.findViewById(R.id.nombre);
            correo = itemView.findViewById(R.id.correo);
            telefono = itemView.findViewById(R.id.telefono);
            descripcion = itemView.findViewById(R.id.descripcion);
            tipo = itemView.findViewById(R.id.tipo);
        }
    }

}
