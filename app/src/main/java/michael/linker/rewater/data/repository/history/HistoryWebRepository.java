package michael.linker.rewater.data.repository.history;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.data.repository.history.model.HistoryNetworkHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.data.web.api.history.HistoryApi;

public class HistoryWebRepository implements IHistoryRepository {
    private final HistoryApi mApi;

    public HistoryWebRepository() {
        mApi = new HistoryApi();
    }

    @Override
    public List<HistoryNetworkHistoryRepositoryModel> getAllHistory(PageSizeCommonRequest request) {
        return mApi.getAllHistory(request).stream()
                .map(HistoryNetworkHistoryRepositoryModel::new)
                .collect(Collectors.toList());
    }
}
