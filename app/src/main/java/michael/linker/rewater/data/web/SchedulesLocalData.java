package michael.linker.rewater.data.web;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
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
                        new WateringPeriodModel(0, 12, 30),
                        new WaterVolumeModel(0, 500)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "2078faea-1db6-46f2-b7d7-bb350646417c",
                        "Second Schedule",
                        new WateringPeriodModel(15, 0, 0),
                        new WaterVolumeModel(100, 0)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "ad567f2d-10ce-45e2-8fa5-a58ffa14bc1a",
                        "Third schedule very very very very very long name",
                        new WateringPeriodModel(31, 23, 59),
                        new WaterVolumeModel(999, 999)));
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

    @Override
    public void updateSchedule(String id, FullScheduleModel newModel) {
        for (int i = 0; i < mFullScheduleModels.size(); i++) {
            if (id.equals(mFullScheduleModels.get(i).getId())) {
                mFullScheduleModels.set(i, new FullScheduleModel(
                        id,
                        newModel.getName(),
                        newModel.getPeriod(),
                        newModel.getVolume()));
                return;
            }
        }
    }
}
