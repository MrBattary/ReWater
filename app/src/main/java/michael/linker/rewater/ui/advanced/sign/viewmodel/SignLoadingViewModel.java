package michael.linker.rewater.ui.advanced.sign.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Single;
import michael.linker.rewater.R;
import michael.linker.rewater.config.DatabaseConfiguration;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.config.StubDataConfiguration;
import michael.linker.rewater.data.repository.user.UsersRepositoryAccessDeniedException;
import michael.linker.rewater.data.repository.user.UsersRepositoryNotFoundException;
import michael.linker.rewater.data.res.StringsProvider;

public class SignLoadingViewModel extends ViewModel {
    private final MutableLiveData<String> stageMessage, errorStageMessage;

    public SignLoadingViewModel() {
        stageMessage = new MutableLiveData<>();
        errorStageMessage = new MutableLiveData<>();
    }

    public void setInitStageMessage(final String msg) {
        stageMessage.setValue(msg);
    }

    public void postErrorStageMessage(final String errorMsg) {
        errorStageMessage.postValue(errorMsg);
    }

    public LiveData<String> getStageMessage() {
        return stageMessage;
    }

    public LiveData<String> getErrorStageMessage() {
        return errorStageMessage;
    }

    // TODO (ML): Add the internet connection check
    public Single<Boolean> checkInternetConnection() throws SignLoadingViewModelFailedException {
        return Single.fromCallable(() -> {
                    try {
                        return true;
                    } catch (RuntimeException e) {
                        this.setErrorStageMessageAndThrowException(
                                R.string.loading_stage_internet_connection_failure);
                        return false;
                    }
                })
                .doOnSuccess(b -> stageMessage.postValue(
                        StringsProvider.getString(R.string.loading_stage_server_connection)));

    }

    // TODO (ML): Add the server connection check
    public Single<Boolean> checkServerConnection() throws SignLoadingViewModelFailedException {
        return Single.fromCallable(() -> {
                    try {
                        return true;
                    } catch (RuntimeException e) {
                        this.setErrorStageMessageAndThrowException(
                                R.string.loading_stage_server_connection_failure);
                        return false;
                    }
                })
                .doOnSuccess(b -> stageMessage.postValue(
                        StringsProvider.getString(R.string.loading_stage_database_connection)));
    }

    public Single<Boolean> loadDatabase() throws SignLoadingViewModelFailedException {
        return Single.fromCallable(() -> {
                    try {
                        if (!DatabaseConfiguration.isDatabaseOpened()) {
                            DatabaseConfiguration.getDatabase();
                        }
                        return true;
                    } catch (RuntimeException e) {
                        this.setErrorStageMessageAndThrowException(
                                R.string.loading_stage_database_connection_failure);
                        return false;
                    }
                })
                .doOnSuccess(b -> stageMessage.postValue(
                        StringsProvider.getString(R.string.loading_stage_auto_login)));
    }

    public Single<Boolean> autoSignIn() throws SignViewModelFailedException {
        return Single.fromCallable(() -> {
            try {
                RepositoryConfiguration.getUsersRepository().refreshSessionToken();
                return true;
            } catch (UsersRepositoryNotFoundException | UsersRepositoryAccessDeniedException e) {
                throw new SignViewModelFailedException(e.getMessage());
            }
        });
    }

    public Single<Boolean> initRepositories() {
        return Single.fromCallable(() -> {
                    try {
                        RepositoryConfiguration.getNetworksRepository();
                        RepositoryConfiguration.getDevicesRepository();
                        RepositoryConfiguration.getSchedulesRepository();
                        return true;
                    } catch (RuntimeException e) {
                        this.setErrorStageMessageAndThrowException(
                                R.string.loading_stage_repository_installation_failure);
                        return false;
                    }
                })
                .doOnSuccess(b -> stageMessage.postValue(
                        StringsProvider.getString(R.string.loading_stage_data_installation)));

    }

    public Single<Boolean> initData() {
        return Single.fromCallable(() -> {
            try {
                StubDataConfiguration.getNetworksData();
                StubDataConfiguration.getSchedulesData();
                StubDataConfiguration.getNetworkToSchedulesDataLink();
                StubDataConfiguration.getDevicesData();
                StubDataConfiguration.getScheduleToDevicesDataLink();
                StubDataConfiguration.getUsersData();
                return true;
            } catch (RuntimeException e) {
                this.setErrorStageMessageAndThrowException(
                        R.string.loading_stage_data_installation_failure);
                return false;
            }
        });
    }

    private void setErrorStageMessageAndThrowException(final int rId)
            throws SignLoadingViewModelFailedException {
        final String errorMsg = StringsProvider.getString(rId);
        errorStageMessage.postValue(errorMsg);
        throw new SignLoadingViewModelFailedException(errorMsg);
    }
}