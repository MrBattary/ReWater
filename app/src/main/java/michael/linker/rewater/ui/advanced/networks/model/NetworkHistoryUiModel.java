package michael.linker.rewater.ui.advanced.networks.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.repository.history.model.NetworkHistoryRepositoryModel;

public class NetworkHistoryUiModel {
    private final String mTime;
    private final IdNameModel mScheduleIdName;
    private final HistoryStatus mHistoryStatus;

    public NetworkHistoryUiModel(NetworkHistoryRepositoryModel repositoryModel) {
        mTime = repositoryModel.getTime();
        mScheduleIdName = repositoryModel.getScheduleIdName();
        mHistoryStatus = repositoryModel.getHistoryStatus();
    }

    public String getTime() {
        return mTime;
    }

    public IdNameModel getScheduleIdName() {
        return mScheduleIdName;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
