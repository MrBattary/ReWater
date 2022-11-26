package michael.linker.rewater.data.local.stub;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import michael.linker.rewater.data.local.stub.model.AuthTokenModel;
import michael.linker.rewater.data.local.stub.model.FullUserModel;
import michael.linker.rewater.data.local.stub.model.SessionTokenModel;
import michael.linker.rewater.data.local.stub.model.SignInUserModel;

public class UsersLocalData implements IUsersData {
    private final Map<String, FullUserModel> mUsers;
    private final List<String> mAssignedTokens;

    public UsersLocalData() {
        mUsers = new HashMap<>();
        mUsers.put("ReWater", new FullUserModel("ReWater",
                "74c373af0384a5bd9caa158fd7f5b36286316583533102c4c2333d2958dc2c8f",
                "admin@rewater.com"));
        mAssignedTokens = new ArrayList<>();
    }

    @Override
    public void signUp(FullUserModel model) throws UsersDataException {
        if (mUsers.containsKey(model.getUsername())) {
            throw new UsersDataException();
        }
        mUsers.put(model.getUsername(), model);
    }

    @Override
    public AuthTokenModel signIn(SignInUserModel model) throws UsersDataException {
        if (!mUsers.containsKey(model.getUsername())) {
            throw new UsersDataException();
        } else {
            if (mUsers.get(model.getUsername()).getPassword().equals(model.getPassword())) {
                final String token = UUID.randomUUID().toString();
                mAssignedTokens.add(token);
                return new AuthTokenModel(token);
            } else {
                throw new UsersDataException();
            }
        }
    }

    @Override
    public SessionTokenModel refreshToken(AuthTokenModel tokenModel) throws UsersDataException {
        Random random = new Random();
        if (!mAssignedTokens.contains(tokenModel.getToken()) && random.nextInt(2) == 0) {
            throw new UsersDataException();
        } else {
            return new SessionTokenModel(UUID.randomUUID().toString(),
                    Instant.now().plusSeconds(3600).toString());
        }
    }
}
