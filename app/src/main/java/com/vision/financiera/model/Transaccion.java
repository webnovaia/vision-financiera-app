package com.vision.financiera.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Transaccion {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String descripcion;
    public double monto;
    public String categoria;
    public String tipo;
    public Date fecha;
}