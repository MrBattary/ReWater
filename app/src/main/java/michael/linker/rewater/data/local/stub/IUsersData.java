package michael.linker.rewater.data.local.stub;

import michael.linker.rewater.data.local.stub.model.AuthTokenModel;
import michael.linker.rewater.data.local.stub.model.SessionTokenModel;
import michael.linker.rewater.data.local.stub.model.SignInUserModel;
import michael.linker.rewater.data.local.stub.model.FullUserModel;

public interface IUsersData {
    void signUp(FullUserModel model) throws UsersDataException;

    AuthTokenModel signIn(SignInUserModel model) throws UsersDataException;

    SessionTokenModel refreshToken(AuthTokenModel tokenModel) throws UsersDataException;
}
