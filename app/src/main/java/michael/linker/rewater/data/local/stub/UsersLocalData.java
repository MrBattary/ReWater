package michael.linker.rewater.data.local.stub;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import michael.linker.rewater.R;
import michael.linker.rewater.data.local.stub.model.AuthTokenModel;
import michael.linker.rewater.data.local.stub.model.FullUserModel;
import michael.linker.rewater.data.local.stub.model.SessionTokenModel;
import michael.linker.rewater.data.local.stub.model.SignInUserModel;
import michael.linker.rewater.data.res.StringsProvider;

public class UsersLocalData implements IUsersData {
    private final Map<String, FullUserModel> mUsers;
    private final List<String> mAssignedTokens;

    public UsersLocalData() {
        mUsers = new HashMap<>();
        mUsers.put("ReWater", new FullUserModel("ReWater",
                "55e1ebd3ebe4f1b46a5ccc9866df4d74e99fe240397e155d04664e5ce2d8e5dc",
                "admin@rewater.com"));
        mAssignedTokens = new ArrayList<>();
    }

    @Override
    public void signUp(FullUserModel model) throws UsersDataException {
        if (mUsers.containsKey(model.getUsername())) {
            throw new UsersDataException(
                    StringsProvider.getString(R.string.sign_up_failure_username_used));
        }
        if (mUsers.values().stream().anyMatch(user -> user.getEmail().equals(model.getEmail()))) {
            throw new UsersDataException(
                    StringsProvider.getString(R.string.sign_up_failure_email_used));
        }
        mUsers.put(model.getUsername(), model);
    }

    @Override
    public AuthTokenModel signIn(SignInUserModel model) throws UsersDataException {
        if (!mUsers.containsKey(model.getUsername()) || !mUsers.get(
                model.getUsername()).getPassword().equals(model.getPassword())) {
            throw new UsersDataException(StringsProvider.getString(R.string.sign_in_failure));
        } else {
            final String token = UUID.randomUUID().toString();
            mAssignedTokens.add(token);
            return new AuthTokenModel(token);
        }
    }

    @Override
    public SessionTokenModel refreshToken(AuthTokenModel tokenModel) throws UsersDataException {
        /*Random random = new Random();
        if (!mAssignedTokens.contains(tokenModel.getToken()) && random.nextInt(2) == 0) {
            throw new UsersDataException();
        } else {
            return new SessionTokenModel(UUID.randomUUID().toString(),
                    Instant.now().plusSeconds(3600).toString());
        }*/
        return new SessionTokenModel(UUID.randomUUID().toString(),
                Instant.now().plusSeconds(3600).toString());
    }
}
