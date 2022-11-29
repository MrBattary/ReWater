package michael.linker.rewater.data.local.db.user.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AuthToken {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "owner_username")
    @NonNull
    public String ownerUsername;

    @ColumnInfo(name = "token")
    public String token;
}
