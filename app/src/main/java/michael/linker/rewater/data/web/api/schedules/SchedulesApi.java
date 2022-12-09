package michael.linker.rewater.data.web.api.schedules;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.api.schedules.request.CreateScheduleRequest;
import michael.linker.rewater.data.web.api.schedules.request.UpdateScheduleRequest;
import michael.linker.rewater.data.web.api.schedules.response.GetScheduleResponse;
import michael.linker.rewater.data.web.gate.HttpGateFailureException;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SchedulesApi {
    private static final HttpUrl.Group GROUP = HttpUrl.Group.SCHEDULES;
    private final IHttpGate mHttpGate;
    private final Gson mGson;

    public SchedulesApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
        mGson = new Gson();
    }

    public List<GetScheduleResponse> getAllSchedules() {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString());
            Type scheduleListType = new TypeToken<ArrayList<GetScheduleResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), scheduleListType);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return new ArrayList<>();
        } catch (FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public List<GetScheduleResponse> getSchedulesOfNetworkWithId(final String networkId) {
        try {
            Response response = mHttpGate.getWithSettings(
                    new HttpUrl.GroupBuilder(GROUP)
                            .addQueryParameter(
                                    HttpUrl.Query.Param.NETWORK_ID,
                                    new HttpUrl.Query.Value(networkId))
                            .buildUrl());
            Type scheduleListType = new TypeToken<ArrayList<GetScheduleResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), scheduleListType);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return new ArrayList<>();
        } catch (FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public GetScheduleResponse getScheduleById(final String scheduleId)
            throws NotFoundHttpException {
        try {
            Response response = mHttpGate.getWithSettings(
                    GROUP.toString() + scheduleId);
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), GetScheduleResponse.class);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void createSchedule(final CreateScheduleRequest request) throws BadRequestHttpException {
        try {
            mHttpGate.postWithSettings(GROUP.toString(), mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new BadRequestHttpException();
        } catch (FailureHttpException e) {
            throw new BadRequestHttpException();
        }
    }

    public void updateSchedule(final String scheduleId, final UpdateScheduleRequest request)
            throws NotFoundHttpException {
        try {
            mHttpGate.putWithSettings(GROUP.toString() + scheduleId, mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void deleteSchedule(final String scheduleId) {
        try {
            mHttpGate.deleteWithSettings(GROUP.toString() + scheduleId).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
        } catch (FailureHttpException ignored) {
        }
    }
}
