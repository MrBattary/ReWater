package michael.linker.rewater.ui.advanced.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.history.IHistoryRepository;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.ui.advanced.home.model.HomeHistoryUiModel;
import michael.linker.rewater.ui.advanced.home.model.HomeStatusChartDataUiModel;
import michael.linker.rewater.ui.advanced.profile.model.ProfileUiModel;

public class HomeViewModel extends ViewModel {
    private final IUsersRepository mUsersRepository;
    private final IHistoryRepository mHistoryRepository;
    private final INetworksRepository mNetworksRepository;
    private final IDevicesRepository mDevicesRepository;

    private final MutableLiveData<ProfileUiModel> mProfileModel;
    private final MutableLiveData<List<HomeHistoryUiModel>> mHistoryList;
    private final MutableLiveData<HomeStatusChartDataUiModel> mDeviceStatusModel,
            mNetworkStatusModel;

    public HomeViewModel() {
        mUsersRepository = RepositoryConfiguration.getUsersRepository();
        mHistoryRepository = RepositoryConfiguration.getHistoryRepository();
        mNetworksRepository = RepositoryConfiguration.getNetworksRepository();
        mDevicesRepository = RepositoryConfiguration.getDevicesRepository();

        mProfileModel = new MutableLiveData<>();
        mHistoryList = new MutableLiveData<>();
        mDeviceStatusModel = new MutableLiveData<>();
        mNetworkStatusModel = new MutableLiveData<>();
    }

    public void requireProfileUpdate() {
        mProfileModel.setValue(new ProfileUiModel(mUsersRepository.getActiveUserData()));
    }

    public LiveData<Status> getNetworksOverallStatus() {
        return mNetworksRepository.getNetworksOverallStatusLiveData();
    }

    public LiveData<HomeStatusChartDataUiModel> getDeviceStatusChartDataModel() {
        return mDeviceStatusModel;
    }

    public LiveData<HomeStatusChartDataUiModel> getNetworkStatusChartDataModel() {
        return mNetworkStatusModel;
    }

    public LiveData<ProfileUiModel> getProfileModel() {
        return mProfileModel;
    }

    public LiveData<List<HomeHistoryUiModel>> getHistoryList() {
        return mHistoryList;
    }

    public void requireHistoryUpdate(PageSizeCommonRequest request) {
        Single.fromCallable(() -> mHistoryRepository.getConsolidatedHistory(request))
                .doOnSuccess(historyEventList ->
                        mHistoryList.postValue(
                                historyEventList.stream()
                                        .map(HomeHistoryUiModel::new)
                                        .collect(Collectors.toList())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void requireDevicesUpdate() {
        Single.fromCallable(mDevicesRepository::getDeviceList)
                .doOnSuccess(deviceList -> {
                    List<Status> deviceStatusList = deviceList.stream()
                            .map(d -> Status.getWorstStatus(d.getStatus().toList()))
                            .collect(Collectors.toList());

                    mDeviceStatusModel.postValue(buildStatusChartDataModel(deviceStatusList));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void requireNetworksUpdate() {
        Single.fromCallable(() -> {
                    mNetworksRepository.updateNetworkList();
                    return true;
                })
                .doOnSuccess(b -> {
                    List<Status> networkStatusList = mNetworksRepository.getNetworkList().stream()
                            .map(n -> Status.getWorstStatus(n.getStatus().toList()))
                            .collect(Collectors.toList());

                    mNetworkStatusModel.postValue(buildStatusChartDataModel(networkStatusList));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private HomeStatusChartDataUiModel buildStatusChartDataModel(List<Status> statusList) {
        HomeStatusChartDataUiModel model = new HomeStatusChartDataUiModel();
        for (Status status : statusList) {
            if (status.equals(Status.UNDEFINED)) {
                model.incrementUndefinedAmount();
            } else if (status.equals(Status.OK)) {
                model.incrementFineAmount();
            } else if (status.equals(Status.WARNING)) {
                model.incrementWarningAmount();
            } else if (status.equals(Status.DEFECT)) {
                model.incrementFailureAmount();
            }
        }
        return model;
    }
}