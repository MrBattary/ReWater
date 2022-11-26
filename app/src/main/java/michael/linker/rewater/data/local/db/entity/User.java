package michael.linker.rewater.data.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    @NonNull public String mUsername;

    @ColumnInfo(name = "active")
    public Boolean active;
}
