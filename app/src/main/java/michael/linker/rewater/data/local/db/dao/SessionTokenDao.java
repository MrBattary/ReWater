package michael.linker.rewater.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import michael.linker.rewater.data.local.db.entity.SessionToken;

@Dao
public interface SessionTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SessionToken sessionToken);

    @Query("SELECT owner_username, token, valid_until " +
            "FROM sessiontoken LEFT OUTER JOIN user ON sessiontoken.owner_username = user.username")
    SessionToken findActive();

    @Update
    void update(SessionToken sessionToken);

    @Query("DELETE FROM sessiontoken WHERE owner_username = :ownerUsername")
    void deleteByOwnerUsername(String ownerUsername);
}
