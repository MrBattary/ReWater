package michael.linker.rewater.data.local.db.user.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    @NonNull public String username;

    @ColumnInfo(name = "active")
    public Boolean active;
}
