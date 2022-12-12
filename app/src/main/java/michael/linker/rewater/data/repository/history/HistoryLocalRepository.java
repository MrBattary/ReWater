package michael.linker.rewater.data.repository.history;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
import michael.linker.rewater.data.repository.history.model.NetworkScheduleHistoryRepositoryModel;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;

public class HistoryLocalRepository implements IHistoryRepository {
    private final Random mRand;

    private final INetworksData mNetworksData;
    private final ISchedulesData mSchedulesData;

    private final IOneToManyDataLink mNetworkToSchedulesDataLink;

    public HistoryLocalRepository() {
        mRand = new Random();

        mNetworksData = StubDataConfiguration.getNetworksData();
        mSchedulesData = StubDataConfiguration.getSchedulesData();

        mNetworkToSchedulesDataLink = StubDataConfiguration.getNetworkToSchedulesDataLink();
    }

    @Override
    public List<NetworkScheduleHistoryRepositoryModel> getAllHistory(PageSizeCommonRequest request) {
        List<NetworkScheduleHistoryRepositoryModel> historyList = new ArrayList<>();

        final List<String> networksIdList = mNetworkToSchedulesDataLink.getLeftEntityIdList();
        for (int i = 0; i < request.getSize(); i++) {
            final String randomNetworkId = networksIdList.get(mRand.nextInt(networksIdList.size()));
            List<String> schedulesIdList;
            do {
                schedulesIdList =
                        mNetworkToSchedulesDataLink.getRightEntityIdListByLeftEntityId(
                                randomNetworkId);
            } while (schedulesIdList.size() > 0);

            final LocalTime randomTime = LocalTime.MIN.minusSeconds(mRand.nextInt());
            final String randomScheduleId = schedulesIdList.get(
                    mRand.nextInt(schedulesIdList.size()));

            SimpleDateFormat randomDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm",
                    Locale.getDefault());
            int year = randBetween(2022, 2022);
            int month = randBetween(0, 11);
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
