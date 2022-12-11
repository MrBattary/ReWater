package michael.linker.rewater.data.web.api.history.response;

import michael.linker.rewater.data.web.api.common.part.IdNamePart;

public class GetNetworkScheduleHistoryResponse {
    private final String time;
    private final IdNamePart network;
    private final IdNamePart schedule;
    private final Integer status;

    public GetNetworkScheduleHistoryResponse(
            String time,
            IdNamePart network,
            IdNamePart schedule,
            Integer status) {
        this.time = time;
        this.network = network;
        this.schedule = schedule;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public IdNamePart getNetwork() {
        return network;
    }

    public IdNamePart getSchedule() {
        return schedule;
    }

    public Integer getStatus() {
        return status;
    }
}
