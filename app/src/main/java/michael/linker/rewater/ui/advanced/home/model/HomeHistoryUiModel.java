package michael.linker.rewater.ui.advanced.home.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.HistoryStatus;

public class HomeHistoryUiModel {
    private final String mTime;
    private final IdNameModel mNetworkIdName;
    private final IdNameModel mScheduleIdName;
    private final HistoryStatus mHistoryStatus;

    public HomeHistoryUiModel(NetworkScheduleHistoryRepositoryModel repositoryModel) {
        mTime = repositoryModel.getTime();
        mNetworkIdName = repositoryModel.getNetworkIdName();
        mScheduleIdName = repositoryModel.getScheduleIdName();
        mHistoryStatus = repositoryModel.getHistoryStatus();
    }
}
