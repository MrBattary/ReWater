package michael.linker.rewater.ui.advanced.schedules.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.history.IHistoryRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModelFailedException;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleHistoryUiModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;

public class SchedulesViewModel extends ViewModel {
    private final ISchedulesRepository mSchedulesRepository;
    private final IHistoryRepository mHistoryRepository;

    private final MutableLiveData<List<ScheduleUiModel>> mScheduleUiModels;
    private final MutableLiveData<List<ScheduleHistoryUiModel>> mScheduleHistoryUiModels;

    public SchedulesViewModel() {
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();
        mHistoryRepository = RepositoryConfiguration.getHistoryRepository();

        mScheduleUiModels = new MutableLiveData<>();
        mScheduleHistoryUiModels = new MutableLiveData<>();
    }

    public LiveData<List<ScheduleUiModel>> getScheduleList() {
        return mScheduleUiModels;
    }

    public LiveData<List<ScheduleHistoryUiModel>> getScheduleHistoryUiModels() {
        return mScheduleHistoryUiModels;
    }

    public void setParentNetworkIdAndLoadSchedules(final String networkId) {
        Single.fromCallable(() -> mSchedulesRepository.getScheduleListByNetworkId(networkId))
                .doOnSuccess(schedules ->
                        mScheduleUiModels.postValue(schedules.stream()
                                .map(ScheduleUiModel::new)
                                .collect(Collectors.toList())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void setHistoryScheduleId(final String id)
            throws NetworksViewModelFailedException {
        Single.fromCallable(() -> mHistoryRepository.getScheduleHistory(id,
                        new PageSizeCommonRequest(0, 20)))
                .doOnSuccess(
                        historyModels -> mScheduleHistoryUiModels.postValue(historyModels.stream().
                                map(ScheduleHistoryUiModel::new)
                                .collect(Collectors.toList())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}