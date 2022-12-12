package michael.linker.rewater.ui.elementary.history.model;

import michael.linker.rewater.ui.advanced.home.model.HomeHistoryUiModel;

public class HistoryCardNetworkScheduleDateTimeModel extends HistoryCardScheduleDateTimeModel {
    private final String mNetworkName;

    public HistoryCardNetworkScheduleDateTimeModel(HomeHistoryUiModel uiModel) {
        super(uiModel.getTime(), uiModel.getHistoryStatus(), uiModel.getScheduleIdName());
        mNetworkName = uiModel.getNetworkIdName().getName();
    }

    public String getNetworkName() {
        return mNetworkName;
    }
}
