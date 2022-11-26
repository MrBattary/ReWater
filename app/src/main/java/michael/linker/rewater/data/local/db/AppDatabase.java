package michael.linker.rewater.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import michael.linker.rewater.data.local.db.dao.AuthTokenDao;
import michael.linker.rewater.data.local.db.dao.SessionTokenDao;
import michael.linker.rewater.data.local.db.dao.UserDao;
import michael.linker.rewater.data.local.db.entity.AuthToken;
import michael.linker.rewater.data.local.db.entity.SessionToken;
import michael.linker.rewater.data.local.db.entity.User;

@Database(
        entities = {
                User.class,
                SessionToken.class,
                AuthToken.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract AuthTokenDao authTokenDao();

    public abstract SessionTokenDao sessionTokenDao();
}
