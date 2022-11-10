package michael.linker.rewater.data.web;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.model.FullScheduleModel;
import michael.linker.rewater.data.web.model.ScheduleListModel;

public class SchedulesLocalData implements ISchedulesData {
    private final List<FullScheduleModel> mFullScheduleModels;

    public SchedulesLocalData() {
        mFullScheduleModels = new ArrayList<>();
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "c0382b72-d6c1-488e-bd6c-b758c93947f1",
                        "First Schedule",
                        0, 0, 12, 30,
                        0, 500,
                        List.of("a2726b77-2214-4aa2-b4af-2fac0a4f84bc",
                                "832b9239-b362-47c1-bfbc-96589bcad643")));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "2078faea-1db6-46f2-b7d7-bb350646417c",
                        "Second Schedule",
                        1,15, 0, 0,
                        100, 0,
                        List.of("832b9239-b362-47c1-bfbc-96589bcad643")
                )
        );
    }

    @Override
    public ScheduleListModel getScheduleList() {
        return new ScheduleListModel(mFullScheduleModels);
    }

    @Override
    public FullScheduleModel getScheduleById(final String id) {
        for (FullScheduleModel scheduleModel : mFullScheduleModels) {
            if (id.equals(scheduleModel.getId())) {
                return scheduleModel;
            }
        }
        return null;
    }
}
