package michael.linker.rewater.ui.advanced.schedules.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;

public class SchedulesViewModel extends ViewModel {
    private final ISchedulesRepository mSchedulesRepository;
    private final MutableLiveData<List<ScheduleUiModel>> mScheduleUiModels;

    public SchedulesViewModel() {
        mSchedulesRepository = RepositoryConfiguration.getSchedulesRepository();

        mScheduleUiModels = new MutableLiveData<>();
    }

    public LiveData<List<ScheduleUiModel>> getScheduleList() {
        return mScheduleUiModels;
    }

    public void setParentNetworkIdAndLoadSchedules(final String networkId) {
        final List<ScheduleRepositoryModel> schedules =
                mSchedulesRepository.getScheduleListByNetworkId(networkId);
        mScheduleUiModels.setValue(schedules.stream()
                .map(ScheduleUiModel::new)
                .collect(Collectors.toList()));
    }
}