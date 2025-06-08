package com.vision.financiera.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vision.financiera.R;

import java.util.ArrayList;
import java.util.List;

public class SeleccionCategoriaActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORIA = "categoria";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_categoria);

        RecyclerView recycler = findViewById(R.id.recyclerCategorias);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));

        List<CategoriaItem> categorias = new ArrayList<>();
        categorias.add(new CategoriaItem("Supermercado", R.drawable.ic_supermercado));
        categorias.add(new CategoriaItem("Salud", R.drawable.ic_salud));
        categorias.add(new CategoriaItem("Restaurante", R.drawable.ic_restaurante));
        categorias.add(new CategoriaItem("Transporte", R.drawable.ic_supermercado));
        categorias.add(new CategoriaItem("Ocio", R.drawable.ic_restaurante));
        categorias.add(new CategoriaItem("Otros", R.drawable.ic_supermercado));

        CategoriaAdapter adapter = new CategoriaAdapter(categorias, categoria -> {
            Intent intent = new Intent(SeleccionCategoriaActivity.this, InputGastoActivity.class);
            intent.putExtra(EXTRA_CATEGORIA, categoria);
            startActivity(intent);
        });
        recycler.setAdapter(adapter);
    }
}
