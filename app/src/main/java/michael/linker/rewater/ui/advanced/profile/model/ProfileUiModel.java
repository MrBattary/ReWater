package michael.linker.rewater.ui.advanced.profile.model;

import michael.linker.rewater.data.repository.user.model.UserRepositoryModel;

public class ProfileUiModel {
    private final String mUsername;

    public ProfileUiModel(final String username) {
        mUsername = username;
    }

    public ProfileUiModel(final UserRepositoryModel model) {
        mUsername = model.getUsername();
    }

    public String getUsername() {
        return mUsername;
    }
}
