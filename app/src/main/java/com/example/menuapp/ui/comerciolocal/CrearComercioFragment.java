package com.example.menuapp.ui.comerciolocal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menuapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearComercioFragment extends DialogFragment {

    Button botonagregar;
    EditText nombre, correo, telefono, descripcion, tipo;
    private FirebaseFirestore mfirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_crear_comercio, container, false );
        mfirestore = FirebaseFirestore.getInstance();

        nombre = v.findViewById(R.id.nombreCrear);
        correo = v.findViewById(R.id.correoCrear);
        telefono = v.findViewById(R.id.telefonoCrear);
        descripcion = v.findViewById(R.id.descripcionCrear);
        tipo = v.findViewById(R.id.tipoCrear);
        botonagregar = v.findViewById(R.id.boton_agregar);

        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreComercio = nombre.getText().toString().trim();
                String correoComercio = correo.getText().toString().trim();
                String telefonoComercio = telefono.getText().toString().trim();
                String descripcionComercio = descripcion.getText().toString().trim();
                String tipoComercio = tipo.getText().toString().trim();

                if(nombreComercio.isEmpty() && correoComercio.isEmpty() && telefonoComercio.isEmpty() && descripcionComercio.isEmpty() && tipoComercio.isEmpty()) {
                    Toast.makeText(getContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else {
                    postComercio(nombreComercio, correoComercio, telefonoComercio, descripcionComercio, tipoComercio);
                }
            }
        });

        return v;
    }

    private void postComercio(String nombreComercio, String correoComercio, String telefonoComercio, String descripcionComercio, String tipoComercio) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombreComercio);
        map.put("correo", correoComercio);
        map.put("telefono", telefonoComercio);
        map.put("descripcion", descripcionComercio);
        map.put("tipo", tipoComercio);


        mfirestore.collection("comercio").add(map).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Insertado con exito.", Toast.LENGTH_SHORT ).show();
                getDialog().dismiss();
            }
        }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al ingresar.", Toast.LENGTH_SHORT ).show();
            }
        });
    }
}