package michael.linker.rewater.data.web.api.schedules.request;

import java.util.List;

import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.web.api.common.part.PeriodPart;
import michael.linker.rewater.data.web.api.common.part.VolumePart;

public class UpdateScheduleRequest {
    private final String name;
    private final PeriodPart period;
    private final VolumePart volume;
    private final List<String> deviceIds;

    public UpdateScheduleRequest(
            String name,
            PeriodPart period,
            VolumePart volume,
            List<String> deviceIds) {
        this.name = name;
        this.period = period;
        this.volume = volume;
        this.deviceIds = deviceIds;
    }

    public UpdateScheduleRequest(final CreateOrUpdateScheduleRepositoryModel model) {
        name = model.getName();
        period = new PeriodPart(model.getPeriod());
        volume = new VolumePart(model.getVolume());
        deviceIds = model.getDeviceModelIds();
    }

    public String getName() {
        return name;
    }

    public PeriodPart getPeriod() {
        return period;
    }

    public VolumePart getVolume() {
        return volume;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }
}
