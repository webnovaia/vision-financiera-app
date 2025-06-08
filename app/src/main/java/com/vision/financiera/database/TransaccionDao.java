package com.vision.financiera.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.vision.financiera.model.Transaccion;
import java.util.List;

@Dao
public interface TransaccionDao {

    @Insert
    void insertar(Transaccion transaccion);

    @Query("SELECT * FROM transaccion ORDER BY fecha DESC")
    List<Transaccion> obtenerTodas();

    @Query("SELECT SUM(monto) FROM transaccion WHERE tipo = 'Ingreso'")
    double obtenerTotalIngresos();

    @Query("SELECT SUM(monto) FROM transaccion WHERE tipo = 'Gasto'")
    double obtenerTotalGastos();
}
