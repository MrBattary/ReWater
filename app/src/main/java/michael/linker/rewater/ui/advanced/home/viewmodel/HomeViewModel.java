package michael.linker.rewater.ui.advanced.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.ui.advanced.profile.model.ProfileUiModel;

public class HomeViewModel extends ViewModel {
    private final IUsersRepository mUsersRepository;

    private final MutableLiveData<ProfileUiModel> mProfileModel;

    public HomeViewModel() {
        mUsersRepository = RepositoryConfiguration.getUsersRepository();

        mProfileModel = new MutableLiveData<>();
    }

    public void requireProfileUpdate() {
        mProfileModel.setValue(new ProfileUiModel(mUsersRepository.getActiveUserData()));
    }

    public LiveData<ProfileUiModel> getProfileModel() {
        return mProfileModel;
    }
}