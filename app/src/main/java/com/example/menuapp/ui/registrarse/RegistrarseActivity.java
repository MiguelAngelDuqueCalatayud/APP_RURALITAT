package com.example.menuapp.ui.registrarse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menuapp.MainActivity;
import com.example.menuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {


    EditText nombrePersona;
    EditText vinculacion;
    EditText direccion;
    EditText poblacion;
    EditText pais;
    EditText correo;
    EditText contrasena;
    EditText contrasenaConfirmacion;
    Button loginBtn;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registrarse );

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        nombrePersona = findViewById( R.id.nombrePersona );
        vinculacion = findViewById( R.id.vinculacion );
        direccion = findViewById( R.id.direccion );
        poblacion = findViewById( R.id.poblacion );
        pais = findViewById( R.id.pais );
        correo = findViewById( R.id.correoelectronico );
        contrasena = findViewById( R.id.contrasenaRegister );
        contrasenaConfirmacion = findViewById( R.id.contrasenaConfirmacion );
        loginBtn = findViewById( R.id.btnIngresar );

        loginBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombrePersona.getText().toString().trim();
                String vinc = vinculacion.getText().toString().trim();
                String dir = direccion.getText().toString().trim();
                String pobl = poblacion.getText().toString().trim();
                String country = pais.getText().toString().trim();
                String correoelec = correo.getText().toString().trim();
                String contra = contrasena.getText().toString().trim();
                String contraRegis = contrasenaConfirmacion.getText().toString().trim();
                String passEncripted = encriptarPassword(contra);

                if (nombre.isEmpty() && vinc.isEmpty() && dir.isEmpty() && pobl.isEmpty() && country.isEmpty() && correoelec.isEmpty() && contra.isEmpty() && contraRegis.isEmpty()) {
                    Toast.makeText( RegistrarseActivity.this, "Complete los datos", Toast.LENGTH_SHORT ).show();
                } else {
                    registerUser( nombre, vinc, dir, pobl, country, correoelec, contra, contraRegis );
                }
            }
        } );


    }

    private void registerUser(String nombre, String vinc, String dir, String pobl, String country, String correoelec, String contra, String contraRegis) {
        mAuth.createUserWithEmailAndPassword( correoelec, contra ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()) {
                   String id = mAuth.getCurrentUser().getUid();
                   DocumentReference documentReference = mFirestore.collection( "usuarios" ).document( id );
                   Map<String, Object> map = new HashMap<>();
                   map.put( "id", id );
                   map.put( "nombre", nombre );
                   map.put( "vinculacion", vinc );
                   map.put( "direccion", dir );
                   map.put( "poblacion", pobl );
                   map.put( "pais", country );
                   map.put( "correo", correoelec );
                   map.put( "contrasenya", contra );
                   map.put( "contrasenya confirmacion", contraRegis );


                   System.out.println( nombre + vinc + dir + pobl + country + correoelec + contra );

                   documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Log.d( "TAG", "onSuccess: Datos registrados " + id );
                       }
                   } );
                   Toast.makeText( RegistrarseActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT ).show();
                   startActivity( new Intent( RegistrarseActivity.this, MainActivity.class ) );

               } else {
                   Toast.makeText( RegistrarseActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT ).show();
               }
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    public static String encriptarPassword(String contrasena) {

        try {
            // Crea una instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance( "MD5" );

            // Convierte la contraseña a un array de bytes y calcula su hash
            byte[] messageDigest = md.digest(contrasena.getBytes());

            // Convierte el hash en una cadena hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append( String.format( "%02x", b ) );
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Maneja la excepción si el algoritmo MD5 no está disponible
            e.printStackTrace();
        }
        return null;
    }
}