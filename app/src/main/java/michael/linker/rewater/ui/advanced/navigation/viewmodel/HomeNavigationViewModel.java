package michael.linker.rewater.ui.advanced.navigation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.networks.INetworksRepository;

public class HomeNavigationViewModel extends ViewModel {
    private final INetworksRepository mNetworksRepository;


    public HomeNavigationViewModel() {
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
    }

    public LiveData<Status> getCurrentStatus() {
        return mNetworksRepository.getNetworksOverallStatusLiveData();
    }
}
