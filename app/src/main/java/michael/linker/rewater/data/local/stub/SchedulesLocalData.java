package michael.linker.rewater.data.local.stub;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.local.stub.model.FullScheduleModel;

public class SchedulesLocalData implements ISchedulesData {
    private final List<FullScheduleModel> mFullScheduleModels;

    public SchedulesLocalData() {
        mFullScheduleModels = new ArrayList<>();
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "c0382b72-d6c1-488e-bd6c-b758c93947f1",
                        "Cacti",
                        new WateringPeriodModel(15, 0, 0),
                        new WaterVolumeMetricModel(0, 200)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "2078faea-1db6-46f2-b7d7-bb350646417c",
                        "Roses",
                        new WateringPeriodModel(3, 12, 0),
                        new WaterVolumeMetricModel(0, 150)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "1e048b1d-355a-4fdb-9701-737239420629",
                        "Violets",
                        new WateringPeriodModel(5, 3, 0),
                        new WaterVolumeMetricModel(0, 110)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "33881327-1e0f-4860-958a-8420969aa7eb",
                        "Chrysanthemums",
                        new WateringPeriodModel(7, 2, 15),
                        new WaterVolumeMetricModel(0, 250)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "a3aad045-962b-4d36-8705-e3382cc85389",
                        "Diffenbachia",
                        new WateringPeriodModel(7, 8, 0),
                        new WaterVolumeMetricModel(2, 500)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "e56ddb26-5bec-4ff5-8328-4e847b20d30e",
                        "Ficuses",
                        new WateringPeriodModel(2, 16, 0),
                        new WaterVolumeMetricModel(0, 130)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "b775ff15-1695-46e3-b715-9a2546bf5e8f",
                        "Begonias",
                        new WateringPeriodModel(1, 15, 0),
                        new WaterVolumeMetricModel(0, 80)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "45ce03b9-64a6-4d00-9596-a6f1460d646b",
                        "Tomatoes",
                        new WateringPeriodModel(4, 0, 0),
                        new WaterVolumeMetricModel(4, 0)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "d8fb34a7-d553-416a-a45c-a00d10a0bfc2",
                        "Cucumbers",
                        new WateringPeriodModel(3, 8, 0),
                        new WaterVolumeMetricModel(8, 0)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "1dd7f020-82e2-444c-93a4-fb8416d6bb1d",
                        "Cabbages",
                        new WateringPeriodModel(2, 12, 0),
                        new WaterVolumeMetricModel(10, 0)));
        mFullScheduleModels.add(
                new FullScheduleModel(
                        "ad567f2d-10ce-45e2-8fa5-a58ffa14bc1a",
                        "Emptiness",
                        new WateringPeriodModel(99, 23, 59),
                        new WaterVolumeMetricModel(99, 999)));
    }

    @Override
    public List<FullScheduleModel> getScheduleList() {
        return new ArrayList<>(mFullScheduleModels);
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

    @Override
    public void createSchedule(FullScheduleModel newModel) {
        mFullScheduleModels.add(newModel);
    }

    @Override
    public void removeSchedule(String id) {
        for (int i = 0; i < mFullScheduleModels.size(); i++) {
            if (id.equals(mFullScheduleModels.get(i).getId())) {
                mFullScheduleModels.remove(i);
                return;
            }
        }
    }
}
