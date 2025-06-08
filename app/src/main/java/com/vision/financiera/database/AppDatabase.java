package com.vision.financiera.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters; // <-- IMPORTANTE
import android.content.Context;

import com.vision.financiera.model.Transaccion;

@Database(entities = {Transaccion.class}, version = 1)
@TypeConverters({Converters.class})  // <-- REGISTRO DEL CONVERSOR
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TransaccionDao transaccionDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vision_financiera_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
