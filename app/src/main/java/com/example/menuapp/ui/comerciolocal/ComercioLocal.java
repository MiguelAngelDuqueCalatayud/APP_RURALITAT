package com.example.menuapp.ui.comerciolocal;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ComercioLocal extends AppCompatActivity {

    Button boton_add, boton_add_fragment;
    RecyclerView mRecycler;
    ComercioAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comerclocal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager (new LinearLayoutManager(this));
        CollectionReference query = mFirestore.collection("Comercio Local");

        FirestoreRecyclerOptions<Comercio> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Comercio>().setQuery(query, Comercio.class).build();

        mAdapter = new ComercioAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        boton_add = findViewById(R.id.agregarComercio);
        boton_add_fragment = findViewById(R.id.agregarFragment);

        boton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComercioLocal.this, CrearComercioActivity.class));
            }
        });
        boton_add_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearComercioFragment fm = new CrearComercioFragment();
                fm.show(getSupportFragmentManager(), "Navegar a fragment");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
