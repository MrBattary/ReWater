package michael.linker.rewater.data.local.db.user.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import michael.linker.rewater.data.local.db.user.entity.AuthToken;

@Dao
public interface AuthTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AuthToken authToken);

    @Query("SELECT owner_username, token " +
            "FROM authtoken LEFT OUTER JOIN user ON authtoken.owner_username = user.username")
    AuthToken findActive();

    @Query("DELETE FROM authtoken WHERE owner_username = :ownerUsername")
    void deleteByOwnerUsername(String ownerUsername);
}
