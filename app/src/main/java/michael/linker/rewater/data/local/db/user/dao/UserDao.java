package michael.linker.rewater.data.local.db.user.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import michael.linker.rewater.data.local.db.user.entity.User;
import michael.linker.rewater.data.local.db.user.entity.relation.AuthTokenAndSessionToken;

@Dao
public interface UserDao {
    @Transaction
    @Query("SELECT * FROM User WHERE username = :usernameValue LIMIT 1")
    User findByUsername(final String usernameValue);

    @Transaction
    @Query("SELECT * FROM User WHERE active LIMIT 1")
    User findActive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user WHERE username = :username")
    void deleteByUsername(final String username);

    @Query("DELETE FROM user WHERE active")
    void deleteActive();

    @Transaction
    @Query("SELECT * FROM user WHERE username = :ownerUsername LIMIT 1")
    AuthTokenAndSessionToken findByOwnerUsername(final String ownerUsername);
}
