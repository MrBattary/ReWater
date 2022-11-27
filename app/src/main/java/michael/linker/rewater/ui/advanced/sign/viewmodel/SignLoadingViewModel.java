package michael.linker.rewater.ui.advanced.sign.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.R;
import michael.linker.rewater.config.DatabaseConfiguration;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.config.StubDataConfiguration;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.repository.user.UsersRepositoryAccessDeniedException;
import michael.linker.rewater.data.repository.user.UsersRepositoryNotFoundException;
import michael.linker.rewater.data.res.StringsProvider;

public class SignLoadingViewModel extends ViewModel {
    private final MutableLiveData<String> stageMessage, errorStageMessage;

    public SignLoadingViewModel() {
        stageMessage = new MutableLiveData<>();
        errorStageMessage = new MutableLiveData<>();
    }

    public LiveData<String> getStageMessage() {
        return stageMessage;
    }

    public LiveData<String> getErrorStageMessage() {
        return errorStageMessage;
    }

    // TODO (ML): Add the internet connection check
    public void checkInternetConnection() throws SignLoadingViewModelFailedException {
        stageMessage.setValue(
                StringsProvider.getString(R.string.loading_stage_internet_connection));
        /*this.setErrorStageMessageAndThrowException(
                R.string.loading_stage_internet_connection_failure);*/
    }

    // TODO (ML): Add the server connection check
    public void checkServerConnection() throws SignLoadingViewModelFailedException {
        stageMessage.setValue(
                StringsProvider.getString(R.string.loading_stage_server_connection));
        /*this.setErrorStageMessageAndThrowException(
                R.string.loading_stage_server_connection_failure);*/
    }

    public void loadDatabase() throws SignLoadingViewModelFailedException {
        stageMessage.setValue(
                StringsProvider.getString(R.string.loading_stage_database_connection));
        DatabaseConfiguration.getDatabase();
        /*this.setErrorStageMessageAndThrowException(
                R.string.loading_stage_database_connection_failure);*/
    }

    public void autoSignIn() throws SignViewModelFailedException {
        try {
            final IUsersRepository mUsersRepository = RepositoryConfiguration.getUsersRepository();
            mUsersRepository.refreshSessionToken();
        } catch (UsersRepositoryNotFoundException | UsersRepositoryAccessDeniedException e) {
            throw new SignViewModelFailedException(e.getMessage());
        }
    }

    public void initRepositories() {
        RepositoryConfiguration.getNetworksRepository();
        RepositoryConfiguration.getDevicesRepository();
        RepositoryConfiguration.getSchedulesRepository();
    }

    public void initData() {
        StubDataConfiguration.getNetworksData();
        StubDataConfiguration.getSchedulesData();
        StubDataConfiguration.getNetworkToSchedulesDataLink();
        StubDataConfiguration.getDevicesData();
        StubDataConfiguration.getScheduleToDevicesDataLink();
        StubDataConfiguration.getUsersData();
    }

    private void setErrorStageMessageAndThrowException(final int rId)
            throws SignViewModelFailedException {
        final String errorMsg = StringsProvider.getString(rId);
        errorStageMessage.setValue(errorMsg);
        throw new SignLoadingViewModelFailedException(errorMsg);
    }
}