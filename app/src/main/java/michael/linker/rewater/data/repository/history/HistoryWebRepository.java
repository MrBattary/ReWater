package michael.linker.rewater.data.repository.history;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.data.web.api.history.HistoryApi;

public class HistoryWebRepository implements IHistoryRepository {
    private final HistoryApi mApi;

    public HistoryWebRepository() {
        mApi = new HistoryApi();
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getAllHistory(PageSizeCommonRequest request) {
        return mApi.getAllHistory(request).stream()
                .map(NetworkScheduleHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }
}
