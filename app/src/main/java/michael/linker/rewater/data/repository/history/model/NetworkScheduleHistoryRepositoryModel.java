package michael.linker.rewater.data.repository.history.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.web.api.history.response.GetNetworkScheduleHistoryResponse;

public class NetworkScheduleHistoryRepositoryModel {
    private final String mTime;
    private final IdNameModel mNetworkIdName;
    private final IdNameModel mScheduleIdName;
    private final HistoryStatus mHistoryStatus;

    public NetworkScheduleHistoryRepositoryModel(
            String time,
            IdNameModel networkIdName,
            IdNameModel scheduleIdName,
            int historyStatus) {
        mTime = time;
        mNetworkIdName = networkIdName;
        mScheduleIdName = scheduleIdName;
        mHistoryStatus = HistoryStatus.valueOf(historyStatus);
    }

    public NetworkScheduleHistoryRepositoryModel(GetNetworkScheduleHistoryResponse response) {
        mTime = response.getTime();
        mNetworkIdName = new IdNameModel(response.getNetwork());
        mScheduleIdName = new IdNameModel(response.getSchedule());
        mHistoryStatus = HistoryStatus.valueOf(response.getStatus());
    }

    public String getTime() {
        return mTime;
    }

    public IdNameModel getNetworkIdName() {
        return mNetworkIdName;
    }

    public IdNameModel getScheduleIdName() {
        return mScheduleIdName;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
