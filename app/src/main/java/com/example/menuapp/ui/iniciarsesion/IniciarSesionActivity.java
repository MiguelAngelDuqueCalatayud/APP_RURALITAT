package com.example.menuapp.ui.iniciarsesion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menuapp.R;
import com.example.menuapp.ui.home.Home;
import com.example.menuapp.ui.registrarse.RegistrarseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IniciarSesionActivity extends AppCompatActivity {

    Button btnIngresar;
    Button registrarseBtn;
    EditText correo;
    EditText contrasenaLogin;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correoLogin);
        contrasenaLogin = findViewById(R.id.contrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        registrarseBtn = findViewById(R.id.registrarseBtn2);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = correo.getText().toString().trim();
                String passUser = contrasenaLogin.getText().toString().trim();
                System.out.println(emailUser + passUser);

                if (emailUser.length() == 0) {
                    Toast.makeText(IniciarSesionActivity.this, "Introduce el correo.", Toast.LENGTH_SHORT).show();
                } else if (passUser.length() == 0) {
                    Toast.makeText(IniciarSesionActivity.this, "Introduce la contraseña.", Toast.LENGTH_SHORT).show();
                }

                /*String passEncripted = encriptarPassword(passUser);

                String storedHashedPassword = encriptarPassword(emailUser);

                if(emailUser.isEmpty() && passUser.isEmpty()) {
                    Toast.makeText( IniciarSesionActivity.this, "Ingresar los datos.", Toast.LENGTH_SHORT ).show();
                } else {
                    loginUser(emailUser, passUser);
                }*/

                boolean correoValidado = validarCorreo(emailUser);
                if (correoValidado == false) {
                    Toast.makeText(IniciarSesionActivity.this, "Introduzca un correo valido", Toast.LENGTH_SHORT).show();
                } else {
                    String passwordEncriptada = encriptarPassword(passUser);
                    System.out.println(passwordEncriptada);

                    //Esto manda la contraseña ya encriptada y el correo para comprobar que pueda iniciar sesion, si no puede mandara un toast con el error y no avanzará
                    mAuth.signInWithEmailAndPassword(emailUser, passwordEncriptada).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(IniciarSesionActivity.this, "Bienvenid@ a OnHouse", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(IniciarSesionActivity.this, Home.class));
                            } else {
                                Toast.makeText(IniciarSesionActivity.this, "Correo o contraseña no validos", Toast.LENGTH_SHORT).show();
                                Log.w("TAG", "Error: ", task.getException());
                            }
                        }
                    });

                }
            }
        });

        registrarseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(IniciarSesionActivity.this, Home.class));
                    Toast.makeText( IniciarSesionActivity.this, "Bienvenido.", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( IniciarSesionActivity.this, "Error.", Toast.LENGTH_SHORT ).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( IniciarSesionActivity.this, "Error al iniciar sesion.", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public static boolean validarCorreo(String correo) {

        boolean correoValido = false;

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);

        if (matcher.find() == true) {
            correoValido = true;
        } else {
            correoValido = false;
        }

        return correoValido;
    }

    public static String encriptarPassword(String contrasenaLogin) {

        try {
            // Crea una instancia de MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance( "MD5" );

            // Convierte la contraseña a un array de bytes y calcula su hash
            byte[] messageDigest = md.digest(contrasenaLogin.getBytes());

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