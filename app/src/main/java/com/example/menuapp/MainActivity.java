package com.example.menuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.menuapp.ui.comerciolocal.ComercioLocal;
import com.example.menuapp.ui.home.Home;
import com.example.menuapp.ui.iniciarsesion.IniciarSesionActivity;
import com.example.menuapp.ui.registrarse.RegistrarseActivity;
import com.example.menuapp.ui.servicios.Servicios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //List<ListElement> elements;

    //private ActivityMainActivityBinding binding;


    private FirebaseAuth mAuth;

    Button botonCompra;
    Button botonParticipacion;
    Button botonServicios = findViewById(R.id.buttonServicios);
    Button botonComercioLocal = findViewById(R.id.buttonComercio);
    Button botonEventos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        mAuth = FirebaseAuth.getInstance();

        botonServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Servicios.class);
                startActivity(intent);
            }
        });
        botonComercioLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ComercioLocal.class);
                startActivity(intent);
            }
        });
    }

        //init();
        //setContentView(binding.getRoot());


/*
    public void init() {
        elements = new ArrayList<>();
        elements.add(new ListElement("#775447", "Miguel Angel", "Alzira", "Activo"));
        elements.add(new ListElement("#607d8b", "Ramon", "Roma", "Activo"));
        elements.add(new ListElement("#03a9f4", "Carlos", "New York", "Cancelado"));
        elements.add(new ListElement("#f44336", "Hector", "Paris", "Inactivo"));
        elements.add(new ListElement("#009688", "Clara", "Londres", "Activo"));

        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }*/



    public void irServicios(View view) {
        Intent i = new Intent(this, Servicios.class);
        startActivity(i);
    }

    public void irComercioLocal(View view) {
        Intent i = new Intent(this, ComercioLocal.class);
        startActivity(i);
    }

    public void irRegistrarse(View view) {
        Intent i = new Intent(this, RegistrarseActivity.class);
        startActivity(i);
    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) {
            startActivity( new Intent(MainActivity.this, IniciarSesionActivity.class ) );
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de la aplicación?").setPositiveButton( "Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown( keyCode, event );
    }
}