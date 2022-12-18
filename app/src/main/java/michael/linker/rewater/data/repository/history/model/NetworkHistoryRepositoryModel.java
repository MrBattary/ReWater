package michael.linker.rewater.data.repository.history.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.web.api.history.response.GetNetworkHistoryResponse;

public class NetworkHistoryRepositoryModel {
    private final String mTime;
    private final IdNameModel mScheduleIdName;
    private final HistoryStatus mHistoryStatus;

    public NetworkHistoryRepositoryModel(
            String time,
            IdNameModel scheduleIdName,
            int historyStatus) {
        mTime = time;
        mScheduleIdName = scheduleIdName;
        mHistoryStatus = HistoryStatus.valueOf(historyStatus);
    }

    public NetworkHistoryRepositoryModel(NetworkScheduleHistoryRepositoryModel model) {
        mTime = model.getTime();
        mScheduleIdName = model.getScheduleIdName();
        mHistoryStatus = model.getHistoryStatus();
    }

    public NetworkHistoryRepositoryModel(GetNetworkHistoryResponse response) {
        mTime = response.getTime();
        mScheduleIdName = new IdNameModel(response.getSchedule());
        mHistoryStatus = HistoryStatus.valueOf(response.getStatus());
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
