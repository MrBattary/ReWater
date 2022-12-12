package michael.linker.rewater.data.repository.history;

import java.util.List;

import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;

public interface IHistoryRepository {

    /**
     * Getting a list of all history events in all networks.
     *
     * @param request page size request
     * @return the list of history models in it or empty list
     */
    List<NetworkScheduleHistoryRepositoryModel> getAllHistory(PageSizeCommonRequest request);
}
