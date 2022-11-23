package michael.linker.rewater.data.local.stub.model;

import java.util.List;

public class ScheduleListModel {
    private final List<FullScheduleModel> mFullScheduleModelList;

    public ScheduleListModel(List<FullScheduleModel> fullScheduleModelList) {
        mFullScheduleModelList = fullScheduleModelList;
    }

    public List<FullScheduleModel> getScheduleModelList() {
        return mFullScheduleModelList;
    }
}
