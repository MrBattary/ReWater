package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;

public class DeviceHistoryUiModel {
    private final String mTime;
    private final HistoryStatus mHistoryStatus;

    public DeviceHistoryUiModel(NetworkScheduleHistoryRepositoryModel repositoryModel) {
        mTime = repositoryModel.getTime();
        mHistoryStatus = repositoryModel.getHistoryStatus();
    }

    public String getTime() {
        return mTime;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
