package michael.linker.rewater.data.repository.history;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import michael.linker.rewater.config.StubDataConfiguration;
import michael.linker.rewater.data.local.stub.INetworksData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.model.FullNetworkModel;
import michael.linker.rewater.data.local.stub.model.FullScheduleModel;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.repository.history.model.NetworkHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.repository.history.model.ScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;

public class HistoryLocalRepository implements IHistoryRepository {
    private final Random mRand;

    private final INetworksData mNetworksData;
    private final ISchedulesData mSchedulesData;
    private final IOneToManyDataLink mNetworkToSchedulesDataLink;
    private final IOneToManyDataLink mScheduleToDevicesDataLink;

    private final List<NetworkScheduleHistoryRepositoryModel>
            mGeneratedConsolidatedHistoryEventsList;

    public HistoryLocalRepository(GenerationConfig config) {
        mRand = new Random();

        mNetworksData = StubDataConfiguration.getNetworksData();
        mSchedulesData = StubDataConfiguration.getSchedulesData();
        mNetworkToSchedulesDataLink = StubDataConfiguration.getNetworkToSchedulesDataLink();
        mScheduleToDevicesDataLink = StubDataConfiguration.getScheduleToDevicesDataLink();

        mGeneratedConsolidatedHistoryEventsList = generateConsolidatedHistoryEvents(
                config.getConsolidatedHistoryEventsNumber()
                        + config.getDevicesHistoryEventsNumber());
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getConsolidatedHistory(
            PageSizeCommonRequest request) {
        List<NetworkScheduleHistoryRepositoryModel> historyList = new ArrayList<>();

        final int requestSize = request.getPage() * request.getSize();

        for (int i = requestSize; i < requestSize + request.getSize(); i++) {
            if (i < mGeneratedConsolidatedHistoryEventsList.size()) {
                historyList.add(mGeneratedConsolidatedHistoryEventsList.get(i));
            } else {
                break;
            }
        }

        return historyList;
    }

    @Override
    public List<NetworkHistoryRepositoryModel> getNetworkHistory(String networkId,
            PageSizeCommonRequest request) {
        List<NetworkHistoryRepositoryModel> historyList = new ArrayList<>();

        final int requestSize = request.getPage() * request.getSize();

        for (NetworkScheduleHistoryRepositoryModel generatedModel :
                mGeneratedConsolidatedHistoryEventsList) {
            if (networkId.equals(generatedModel.getNetworkIdName().getId())
                    && historyList.size() < requestSize) {
                historyList.add(new NetworkHistoryRepositoryModel(generatedModel));
            }
        }

        return historyList;
    }

    @Override
    public List<ScheduleHistoryRepositoryModel> getScheduleHistory(String scheduleId,
            PageSizeCommonRequest request) {
        List<ScheduleHistoryRepositoryModel> historyList = new ArrayList<>();

        final int requestSize = request.getPage() * request.getSize();

        for (NetworkScheduleHistoryRepositoryModel generatedModel :
                mGeneratedConsolidatedHistoryEventsList) {
            if (scheduleId.equals(generatedModel.getScheduleIdName().getId())
                    && historyList.size() < requestSize) {
                historyList.add(new ScheduleHistoryRepositoryModel(generatedModel));
            }
        }

        return historyList;
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getDeviceHistory(String deviceId,
            PageSizeCommonRequest request) {
        List<NetworkScheduleHistoryRepositoryModel> historyList = new ArrayList<>();
        final String scheduleId =
                mScheduleToDevicesDataLink.getLeftEntityIdByRightEntityId(deviceId);

        final int requestSize = request.getPage() * request.getSize();

        for (NetworkScheduleHistoryRepositoryModel generatedModel :
                mGeneratedConsolidatedHistoryEventsList) {
            if (scheduleId.equals(generatedModel.getScheduleIdName().getId())
                    && historyList.size() < requestSize) {
                historyList.add(generatedModel);
            }
        }

        return historyList;
    }

    public static class GenerationConfig {
        private final int mConsolidatedHistoryEventsNumber, mDevicesHistoryEventsNumber;

        public GenerationConfig(int consolidatedHistorySize, int devicesHistory) {
            mConsolidatedHistoryEventsNumber = consolidatedHistorySize;
            mDevicesHistoryEventsNumber = devicesHistory;
        }

        public int getConsolidatedHistoryEventsNumber() {
            return mConsolidatedHistoryEventsNumber;
        }

        public int getDevicesHistoryEventsNumber() {
            return mDevicesHistoryEventsNumber;
        }
    }

    private List<NetworkScheduleHistoryRepositoryModel> generateConsolidatedHistoryEvents(
            int generationSize) {
        List<NetworkScheduleHistoryRepositoryModel> historyList = new ArrayList<>();

        final List<String> networksIdList = mNetworkToSchedulesDataLink.getLeftEntityIdList();
        for (int i = 0; i < generationSize; i++) {
            final String randomNetworkId = networksIdList.get(mRand.nextInt(networksIdList.size()));
            List<String> schedulesIdList;
            do {
                schedulesIdList =
                        mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(
                                randomNetworkId);
            } while (schedulesIdList.size() == 0);

            final String randomScheduleId = schedulesIdList.get(
                    mRand.nextInt(schedulesIdList.size()));
            SimpleDateFormat randomDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm",
                    Locale.getDefault());
            int year = randBetween(2022, 2022);
            int month = randBetween(0, 12);
            int hour = randBetween(0, 23);
            int min = randBetween(0, 59);
            GregorianCalendar gc = new GregorianCalendar(year, month, 1);
            int day = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
            gc.set(year, month, day, hour, min);


            final FullNetworkModel randomNetworkModel = mNetworksData.getNetworkById(
                    randomNetworkId);
            final FullScheduleModel randomScheduleModel =
                    mSchedulesData.getScheduleById(randomScheduleId);

            historyList.add(new NetworkScheduleHistoryRepositoryModel(
                    randomDateTime.format(gc.getTime()),
                    new IdNameModel(randomNetworkId, randomNetworkModel.getName()),
                    new IdNameModel(randomScheduleId, randomScheduleModel.getName()),
                    mRand.nextInt(2)
            ));
        }

        return historyList;
    }

    private int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
