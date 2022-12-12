package michael.linker.rewater.data.web.api.history;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.data.web.api.history.response.GetNetworkHistoryResponse;
import michael.linker.rewater.data.web.api.history.response.GetNetworkScheduleHistoryResponse;
import michael.linker.rewater.data.web.api.history.response.GetScheduleHistoryResponse;
import michael.linker.rewater.data.web.gate.HttpGateFailureException;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HistoryApi {
    private static final HttpUrl.Group GROUP = HttpUrl.Group.HISTORY;
    private final IHttpGate mHttpGate;
    private final Gson mGson;

    public HistoryApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
        mGson = new Gson();
    }

    public List<GetNetworkScheduleHistoryResponse> getConsolidatedHistory(PageSizeCommonRequest request) {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString() +
                    HttpUrl.Group.NETWORKS.toString() +
                    new HttpUrl.Query().addQueryParameter(
                                    HttpUrl.Query.Param.PAGE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getPage())))
                            .addQueryParameter(HttpUrl.Query.Param.SIZE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getSize())))
                            .toString()
            );
            Type historyList = new TypeToken<ArrayList<GetNetworkScheduleHistoryResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), historyList);
            }
        } catch (HttpGateFailureException | FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public List<GetNetworkHistoryResponse> getNetworkHistory(
            String networkId,
            PageSizeCommonRequest request) {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString() +
                    HttpUrl.Group.NETWORKS.toString() +
                    "/" + networkId +
                    new HttpUrl.Query().addQueryParameter(
                                    HttpUrl.Query.Param.PAGE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getPage())))
                            .addQueryParameter(HttpUrl.Query.Param.SIZE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getSize())))
                            .toString()
            );
            Type historyList = new TypeToken<ArrayList<GetNetworkHistoryResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), historyList);
            }
        } catch (HttpGateFailureException | FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public List<GetScheduleHistoryResponse> getScheduleHistory(
            String scheduleId,
            PageSizeCommonRequest request) {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString() +
                    HttpUrl.Group.SCHEDULES.toString() +
                    "/" + scheduleId +
                    new HttpUrl.Query().addQueryParameter(
                                    HttpUrl.Query.Param.PAGE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getPage())))
                            .addQueryParameter(HttpUrl.Query.Param.SIZE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getSize())))
                            .toString()
            );
            Type historyList = new TypeToken<ArrayList<GetScheduleHistoryResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), historyList);
            }
        } catch (HttpGateFailureException | FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public List<GetNetworkScheduleHistoryResponse> getDeviceHistory(
            String deviceId,
            PageSizeCommonRequest request) {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString() +
                    HttpUrl.Group.DEVICES.toString() +
                    "/" + deviceId +
                    new HttpUrl.Query().addQueryParameter(
                                    HttpUrl.Query.Param.PAGE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getPage())))
                            .addQueryParameter(HttpUrl.Query.Param.SIZE,
                                    new HttpUrl.Query.Value(Integer.toString(request.getSize())))
                            .toString()
            );
            Type historyList = new TypeToken<ArrayList<GetNetworkScheduleHistoryResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), historyList);
            }
        } catch (HttpGateFailureException | FailureHttpException e) {
            return new ArrayList<>();
        }
    }
}
