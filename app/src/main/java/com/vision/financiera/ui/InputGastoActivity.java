package com.vision.financiera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vision.financiera.MainActivity;
import com.vision.financiera.R;
import com.vision.financiera.database.AppDatabase;
import com.vision.financiera.model.Transaccion;

import java.util.Date;

public class InputGastoActivity extends AppCompatActivity {

    private String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_gasto);

        categoria = getIntent().getStringExtra(SeleccionCategoriaActivity.EXTRA_CATEGORIA);

        TextView tvCategoria = findViewById(R.id.tvCategoria);
        EditText etConcepto = findViewById(R.id.etConcepto);
        EditText etImporte = findViewById(R.id.etImporte);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        tvCategoria.setText(categoria);

        btnConfirmar.setOnClickListener(v -> {
            Transaccion t = new Transaccion();
            t.descripcion = etConcepto.getText().toString();
            t.monto = Double.parseDouble(etImporte.getText().toString());
            t.categoria = categoria;
            t.tipo = "Gasto";
            t.fecha = new Date();

            AppDatabase.getInstance(InputGastoActivity.this).transaccionDao().insertar(t);

            Intent intent = new Intent(InputGastoActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
