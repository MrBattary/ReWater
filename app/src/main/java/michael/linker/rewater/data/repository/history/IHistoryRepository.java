package michael.linker.rewater.data.repository.history;

import java.util.List;

import michael.linker.rewater.data.repository.history.model.NetworkHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.ScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;

public interface IHistoryRepository {

    /**
     * Getting a consolidated list of history events in all networks.
     *
     * @param request page size request
     * @return the list of history models in it or empty list
     */
    List<NetworkScheduleHistoryRepositoryModel> getConsolidatedHistory(
            PageSizeCommonRequest request);

    /**
     * Get history of network.
     *
     * @param networkId network id
     * @param request page size request
     * @return the list of history models in it or empty list
     */
    List<NetworkHistoryRepositoryModel> getNetworkHistory(String networkId,
            PageSizeCommonRequest request);

    /**
     * Get history of schedule.
     *
     * @param scheduleId schedule id
     * @param request page size request
     * @return the list of history models in it or empty list
     */
    List<ScheduleHistoryRepositoryModel> getScheduleHistory(String scheduleId,
            PageSizeCommonRequest request);

    /**
     * Get history of device.
     *
     * @param deviceId device id
     * @param request page size request
     * @return the list of history models in it or empty list
     */
    List<NetworkScheduleHistoryRepositoryModel> getDeviceHistory(String deviceId,
            PageSizeCommonRequest request);
}
