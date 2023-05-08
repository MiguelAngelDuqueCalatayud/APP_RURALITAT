package com.example.menuapp.ui.comerciolocal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menuapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CrearComercioActivity extends AppCompatActivity {

    Button botonagregar;
    EditText nombre, correo, telefono, descripcion, tipo;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_comercio);

        this.setTitle("Insertar comercio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.nombreCrear);
        correo = findViewById(R.id.correoCrear);
        telefono = findViewById(R.id.telefonoCrear);
        descripcion = findViewById(R.id.descripcionCrear);
        tipo = findViewById(R.id.tipoCrear);
        botonagregar = findViewById(R.id.boton_agregar);

        botonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreComercio = nombre.getText().toString().trim();
                String correoComercio = correo.getText().toString().trim();
                String telefonoComercio = telefono.getText().toString().trim();
                String descripcionComercio = descripcion.getText().toString().trim();
                String tipoComercio = tipo.getText().toString().trim();

                if(nombreComercio.isEmpty() && correoComercio.isEmpty() && telefonoComercio.isEmpty() && descripcionComercio.isEmpty() && tipoComercio.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }else {
                    postComercio(nombreComercio, correoComercio, telefonoComercio, descripcionComercio, tipoComercio);
                }
            }
        });
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
                Toast.makeText( getApplicationContext(), "Insertado con exito.", Toast.LENGTH_SHORT ).show();
                finish();
            }
        }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( getApplicationContext(), "Error al ingresar.", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
