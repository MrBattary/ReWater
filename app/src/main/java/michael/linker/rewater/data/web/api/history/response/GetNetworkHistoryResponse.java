package michael.linker.rewater.data.web.api.history.response;

import michael.linker.rewater.data.web.api.common.part.IdNamePart;

public class GetNetworkHistoryResponse {
    private final String time;
    private final IdNamePart schedule;
    private final Integer status;

    public GetNetworkHistoryResponse(
            String time,
            IdNamePart schedule,
            Integer status) {
        this.time = time;
        this.schedule = schedule;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public IdNamePart getSchedule() {
        return schedule;
    }

    public Integer getStatus() {
        return status;
    }
}
