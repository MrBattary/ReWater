package michael.linker.rewater.data.web.api.model.request;

import java.util.List;

import michael.linker.rewater.data.web.api.model.PeriodPart;
import michael.linker.rewater.data.web.api.model.VolumePart;

public class CreateScheduleRequest {
    private final String name;
    private final PeriodPart period;
    private final VolumePart volume;
    private final List<String> deviceIds;
    private final String networkId;

    public CreateScheduleRequest(
            String name,
            PeriodPart period,
            VolumePart volume,
            List<String> deviceIds,
            String networkId) {
        this.name = name;
        this.period = period;
        this.volume = volume;
        this.deviceIds = deviceIds;
        this.networkId = networkId;
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

    public String getNetworkId() {
        return networkId;
    }
}
