package michael.linker.rewater.data.local.db.user.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import michael.linker.rewater.data.local.db.user.entity.AuthToken;
import michael.linker.rewater.data.local.db.user.entity.SessionToken;
import michael.linker.rewater.data.local.db.user.entity.User;

public class AuthTokenAndSessionToken {
    @Embedded
    public User mUser;
    @Relation(
            parentColumn = "username",
            entityColumn = "owner_username"
    )
    public SessionToken mSessionToken;
    @Relation(
            parentColumn = "username",
            entityColumn = "owner_username"
    )
    public AuthToken mAuthToken;
}
