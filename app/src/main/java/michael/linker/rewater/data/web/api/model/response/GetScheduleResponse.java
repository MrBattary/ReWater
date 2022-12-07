package michael.linker.rewater.data.web.api.model.response;

import java.util.List;

import michael.linker.rewater.data.web.api.model.PeriodPart;
import michael.linker.rewater.data.web.api.model.VolumePart;

public class GetScheduleResponse {
    private final String id;
    private final String name;
    private final PeriodPart period;
    private final VolumePart volume;
    private final List<GetScheduleDeviceResponsePart> devices;

    public GetScheduleResponse(
            String id,
            String name,
            PeriodPart period,
            VolumePart volume,
            List<GetScheduleDeviceResponsePart> devices) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.volume = volume;
        this.devices = devices;
    }

    public String getId() {
        return id;
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

    public List<GetScheduleDeviceResponsePart> getDevices() {
        return devices;
    }
}
