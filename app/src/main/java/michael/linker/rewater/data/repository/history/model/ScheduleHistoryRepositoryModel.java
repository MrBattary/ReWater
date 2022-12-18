package michael.linker.rewater.data.repository.history.model;

import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.web.api.history.response.GetScheduleHistoryResponse;

public class ScheduleHistoryRepositoryModel {
    private final String mTime;
    private final HistoryStatus mHistoryStatus;

    public ScheduleHistoryRepositoryModel(
            String time,
            int historyStatus) {
        mTime = time;
        mHistoryStatus = HistoryStatus.valueOf(historyStatus);
    }

    public ScheduleHistoryRepositoryModel(NetworkScheduleHistoryRepositoryModel model) {
        mTime = model.getTime();
        mHistoryStatus = model.getHistoryStatus();
    }

    public ScheduleHistoryRepositoryModel(GetScheduleHistoryResponse response) {
        mTime = response.getTime();
        mHistoryStatus = HistoryStatus.valueOf(response.getStatus());
    }

    public String getTime() {
        return mTime;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
