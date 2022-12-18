package michael.linker.rewater.ui.advanced.schedules.model;

import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.repository.history.model.ScheduleHistoryRepositoryModel;

public class ScheduleHistoryUiModel {
    private final String mTime;
    private final HistoryStatus mHistoryStatus;

    public ScheduleHistoryUiModel(ScheduleHistoryRepositoryModel repositoryModel) {
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
