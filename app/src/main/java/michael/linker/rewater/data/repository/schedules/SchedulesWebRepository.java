package michael.linker.rewater.data.repository.schedules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.data.web.api.schedules.SchedulesApi;
import michael.linker.rewater.data.web.api.schedules.request.CreateScheduleRequest;
import michael.linker.rewater.data.web.api.schedules.request.UpdateScheduleRequest;
import michael.linker.rewater.data.web.gate.exceptions.group.ClientErrorException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;

public class SchedulesWebRepository implements ISchedulesRepository {
    private final SchedulesApi mApi;

    public SchedulesWebRepository() {
        mApi = new SchedulesApi();
    }

    @Override
    public List<ScheduleRepositoryModel> getAllSchedules() {
        try {
            return mApi.getAllSchedules().stream()
                    .map(ScheduleRepositoryModel::new)
                    .collect(Collectors.toList());
        } catch (ClientErrorException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ScheduleRepositoryModel> getScheduleListByNetworkId(String networkId) {
        try {
            return mApi.getSchedulesOfNetworkWithId(networkId).stream()
                    .map(ScheduleRepositoryModel::new)
                    .collect(Collectors.toList());
        } catch (ClientErrorException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public ScheduleRepositoryModel getScheduleById(String scheduleId)
            throws SchedulesRepositoryNotFoundException {
        try {
            return new ScheduleRepositoryModel(mApi.getScheduleById(scheduleId));
        } catch (NotFoundHttpException e) {
            throw new SchedulesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_schedule_not_found),
                    scheduleId));
        }
    }

    @Override
    public void createSchedule(String networkId, CreateOrUpdateScheduleRepositoryModel model)
            throws SchedulesRepositoryAlreadyExistsException {
        try {
            mApi.createSchedule(new CreateScheduleRequest(networkId, model));
        } catch (BadRequestHttpException e) {
            throw new SchedulesRepositoryAlreadyExistsException(String.format(
                    StringsProvider.getString(R.string.internal_repository_schedule_already_exists),
                    model.getName()));
        }
    }

    @Override
    public void updateSchedule(String scheduleId, CreateOrUpdateScheduleRepositoryModel model)
            throws SchedulesRepositoryNotFoundException {
        try {
            mApi.updateSchedule(scheduleId, new UpdateScheduleRequest(model));
        } catch (NotFoundHttpException e) {
            throw new SchedulesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_schedule_not_found),
                    scheduleId));
        }
    }

    @Override
    public void removeSchedule(String scheduleId) {
        try {
            mApi.deleteSchedule(scheduleId);
        } catch (ClientErrorException ignored) {
        }
    }
}
