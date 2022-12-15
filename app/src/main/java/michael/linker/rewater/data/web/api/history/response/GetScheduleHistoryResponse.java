package michael.linker.rewater.data.web.api.history.response;

import michael.linker.rewater.data.web.api.common.part.IdNamePart;

public class GetScheduleHistoryResponse {
    private final String time;
    private final Integer status;

    public GetScheduleHistoryResponse(
            String time,
            IdNamePart network,
            Integer status) {
        this.time = time;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public Integer getStatus() {
        return status;
    }
}
