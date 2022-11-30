package michael.linker.rewater.config;

import android.content.Context;

import androidx.room.Room;

import michael.linker.rewater.data.local.db.AppDatabase;
import michael.linker.rewater.core.App;

public class DatabaseConfiguration {
    private static final String DATABASE_NAME = "database";
    private static AppDatabase sDatabase;

    public static AppDatabase getDatabase() {
        final Context context = App.getInstance().getApplicationContext();
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
        }
        return sDatabase;
    }

    public static boolean isDatabaseOpened() {
        if (sDatabase != null) {
            return sDatabase.isOpen();
        } else {
            return false;
        }
    }
}
