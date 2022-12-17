package michael.linker.rewater.data.repository.history;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.data.repository.history.model.NetworkHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.ScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.data.web.api.history.HistoryApi;

public class HistoryWebRepository implements IHistoryRepository {
    private final HistoryApi mApi;

    public HistoryWebRepository() {
        mApi = new HistoryApi();
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getConsolidatedHistory(
            PageSizeCommonRequest request) {
        return mApi.getConsolidatedHistory(request).stream()
                .map(NetworkScheduleHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NetworkHistoryRepositoryModel> getNetworkHistory(String networkId,
            PageSizeCommonRequest request) {
        return mApi.getNetworkHistory(networkId, request).stream()
                .map(NetworkHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleHistoryRepositoryModel> getScheduleHistory(String scheduleId,
            PageSizeCommonRequest request) {
        return mApi.getScheduleHistory(scheduleId, request).stream()
                .map(ScheduleHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getDeviceHistory(String deviceId,
            PageSizeCommonRequest request) {
        return mApi.getDeviceHistory(deviceId, request).stream()
                .map(NetworkScheduleHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }
}
