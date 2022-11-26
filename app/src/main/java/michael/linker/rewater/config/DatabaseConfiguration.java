package michael.linker.rewater.config;

import androidx.room.Room;

import michael.linker.rewater.data.local.db.AppDatabase;
import michael.linker.rewater.data.res.App;

public class DatabaseConfiguration {
    private static final String DATABASE_NAME = "database";
    private static AppDatabase sDatabase;

    public static AppDatabase getDatabase() {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(
                    App.getInstance().getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
        }
        return sDatabase;
    }
}
