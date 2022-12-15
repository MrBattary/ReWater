package michael.linker.rewater.ui.elementary.history.model;

import michael.linker.rewater.data.model.status.HistoryStatus;

public class HistoryCardDateTimeBaseModel {
    private final String mTime;
    private final HistoryStatus mHistoryStatus;

    public HistoryCardDateTimeBaseModel(
            String time,
            HistoryStatus historyStatus) {
        mTime = time;
        mHistoryStatus = historyStatus;
    }

    public String getTime() {
        return mTime;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
