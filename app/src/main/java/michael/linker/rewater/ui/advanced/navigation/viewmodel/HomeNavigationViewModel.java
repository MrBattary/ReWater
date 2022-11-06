package michael.linker.rewater.ui.advanced.navigation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.data.model.Status;

public class HomeNavigationViewModel extends ViewModel {
    private final MutableLiveData<Status> mCurrentStatus;

    public HomeNavigationViewModel() {
        mCurrentStatus = new MutableLiveData<>();
        // TODO (ML): Connect to the repository
        mCurrentStatus.setValue(Status.OK);
    }

    public MutableLiveData<Status> getCurrentStatus() {
        return mCurrentStatus;
    }
}
