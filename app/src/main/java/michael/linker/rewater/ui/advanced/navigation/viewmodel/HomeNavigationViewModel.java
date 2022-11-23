package michael.linker.rewater.ui.advanced.navigation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.data.model.status.Status;

public class HomeNavigationViewModel extends ViewModel {
    private final MutableLiveData<Status> mCurrentStatus;

    public HomeNavigationViewModel() {
        mCurrentStatus = new MutableLiveData<>();
        // TODO (ML): Connect to the repository
        mCurrentStatus.setValue(Status.OK);
    }

    public LiveData<Status> getCurrentStatus() {
        return mCurrentStatus;
    }
}
