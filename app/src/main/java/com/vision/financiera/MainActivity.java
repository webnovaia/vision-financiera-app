package com.vision.financiera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.*;
import com.vision.financiera.database.AppDatabase;
import com.vision.financiera.model.Transaccion;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText etDescripcion, etMonto;
    Spinner spCategoria, spTipo;
    Button btnGuardar, btnNuevoGasto;
    TextView tvResumen;
    ListView listaTransacciones;
    PieChart pieChart;
    AdView adView;
    ArrayAdapter<String> adapter;
    List<String> datosLista = new ArrayList<>();
    boolean esPremium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDescripcion = findViewById(R.id.etDescripcion);
        etMonto = findViewById(R.id.etMonto);
        spCategoria = findViewById(R.id.spCategoria);
        spTipo = findViewById(R.id.spTipo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnNuevoGasto = findViewById(R.id.btnNuevoGasto);
        tvResumen = findViewById(R.id.tvResumen);
        listaTransacciones = findViewById(R.id.listaTransacciones);
        pieChart = findViewById(R.id.pieChart);
        adView = findViewById(R.id.adView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosLista);
        listaTransacciones.setAdapter(adapter);
        cargarLista();

        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setVisibility(esPremium ? View.GONE : View.VISIBLE);

        btnNuevoGasto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.vision.financiera.ui.SeleccionCategoriaActivity.class);
            startActivity(intent);
        });

        btnGuardar.setOnClickListener(v -> {
            Transaccion t = new Transaccion();
            t.descripcion = etDescripcion.getText().toString();
            t.monto = Double.parseDouble(etMonto.getText().toString());
            t.categoria = spCategoria.getSelectedItem().toString();
            t.tipo = spTipo.getSelectedItem().toString();
            t.fecha = new Date();

            AppDatabase.getInstance(MainActivity.this).transaccionDao().insertar(t);
            datosLista.add(t.tipo + ": " + t.categoria + " - €" + t.monto);
            adapter.notifyDataSetChanged();

            cargarResumen();
            cargarGrafico();
        });

        cargarResumen();
        cargarGrafico();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
        cargarResumen();
        cargarGrafico();
    }

    private void cargarLista() {
        datosLista.clear();
        List<Transaccion> lista = AppDatabase.getInstance(this).transaccionDao().obtenerTodas();
        for (Transaccion t : lista) {
            datosLista.add(t.tipo + ": " + t.categoria + " - €" + t.monto);
        }
        adapter.notifyDataSetChanged();
    }

    private void cargarResumen() {
        double ingresos = AppDatabase.getInstance(this).transaccionDao().obtenerTotalIngresos();
        double gastos = AppDatabase.getInstance(this).transaccionDao().obtenerTotalGastos();
        tvResumen.setText("Ingresos: €" + ingresos + "\nGastos: €" + gastos);
    }

    private void cargarGrafico() {
        List<Transaccion> lista = AppDatabase.getInstance(this).transaccionDao().obtenerTodas();
        Map<String, Float> categoriaMontos = new HashMap<>();

        for (Transaccion t : lista) {
            if ("Gasto".equals(t.tipo)) {
                float actual = categoriaMontos.containsKey(t.categoria) ? categoriaMontos.get(t.categoria) : 0f;
                categoriaMontos.put(t.categoria, actual + (float) t.monto);
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categoriaMontos.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Gastos por Categoría");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}