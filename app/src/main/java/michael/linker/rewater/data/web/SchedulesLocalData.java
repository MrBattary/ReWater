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
                        1, 15, 0, 0,
                        100, 0,
                        List.of("832b9239-b362-47c1-bfbc-96589bcad643")));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "ad567f2d-10ce-45e2-8fa5-a58ffa14bc1a",
                        "Third schedule very very very very very long name",
                        99, 31, 23, 59,
                        999, 999,
                        List.of("353f5967-c7bd-4c57-8528-4f1ab7b88005")));
    }

    @Override
    public ScheduleListModel getScheduleList() {
        return new ScheduleListModel(mFullScheduleModels);
    }

    @Override
    public FullScheduleModel getScheduleById(final String id) {
        for (FullScheduleModel scheduleModel : mFullScheduleModels) {
            if (scheduleModel.getId().equals(id)) {
                return scheduleModel;
            }
        }
        return null;
    }
}
