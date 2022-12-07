package michael.linker.rewater.data.web.api.schedules.response;

import java.util.List;

import michael.linker.rewater.data.web.api.part.PeriodPart;
import michael.linker.rewater.data.web.api.part.VolumePart;

public class GetScheduleResponse {
    private final String id;
    private final String name;
    private final PeriodPart period;
    private final VolumePart volume;
    private final List<GetScheduleDeviceResponsePart> devices;
    private final String networkId;

    public GetScheduleResponse(
            String id,
            String name,
            PeriodPart period,
            VolumePart volume,
            List<GetScheduleDeviceResponsePart> devices,
            String networkId) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.volume = volume;
        this.devices = devices;
        this.networkId = networkId;
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

    public String getNetworkId() {
        return networkId;
    }
}
