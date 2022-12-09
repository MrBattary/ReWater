package michael.linker.rewater.data.web.api.devices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.api.devices.request.CreateDeviceRequest;
import michael.linker.rewater.data.web.api.devices.request.ManualWateringDeviceRequest;
import michael.linker.rewater.data.web.api.devices.request.UpdateDeviceRequest;
import michael.linker.rewater.data.web.api.devices.response.GetDeviceResponse;
import michael.linker.rewater.data.web.gate.HttpGateFailureException;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DevicesApi {
    private static final HttpUrl.Group GROUP = HttpUrl.Group.DEVICES;
    private final IHttpGate mHttpGate;
    private final Gson mGson;

    public DevicesApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
        mGson = new Gson();
    }

    public List<GetDeviceResponse> getAllDevices() {
        try {
            Response response = mHttpGate.getWithSettings(
                    new HttpUrl.GroupBuilder(GROUP)
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICES_FULL_INFO,
                                    HttpUrl.Query.Value.BOOL_TRUE)
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICES_ATTACHABLE,
                                    HttpUrl.Query.Value.BOOL_FALSE)
                            .buildUrl()
            );
            Type deviceListType = new TypeToken<ArrayList<GetDeviceResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), deviceListType);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return new ArrayList<>();
        } catch (FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public List<GetDeviceResponse> getAttachableDevices() {
        try {
            Response response = mHttpGate.getWithSettings(
                    new HttpUrl.GroupBuilder(GROUP)
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICES_FULL_INFO,
                                    HttpUrl.Query.Value.BOOL_TRUE)
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICES_ATTACHABLE,
                                    HttpUrl.Query.Value.BOOL_TRUE)
                            .buildUrl()
            );
            Type deviceListType = new TypeToken<ArrayList<GetDeviceResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), deviceListType);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return new ArrayList<>();
        } catch (FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public GetDeviceResponse getDeviceById(final String deviceId) throws NotFoundHttpException {
        try {
            Response response = mHttpGate.getWithSettings(
                    GROUP.toString() + deviceId
                            + new HttpUrl.Query()
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICE_HARDCODED_ID,
                                    HttpUrl.Query.Value.BOOL_FALSE)
                            .toString()
            );
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), GetDeviceResponse.class);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();

        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public GetDeviceResponse getDeviceByHardwareId(final String deviceHardwareId)
            throws NotFoundHttpException {
        try {
            Response response = mHttpGate.getWithSettings(
                    GROUP.toString() + deviceHardwareId
                            + new HttpUrl.Query()
                            .addQueryParameter(
                                    HttpUrl.Query.Param.DEVICE_HARDCODED_ID,
                                    HttpUrl.Query.Value.BOOL_TRUE)
                            .toString()
            );
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), GetDeviceResponse.class);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();

        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void createDevice(final CreateDeviceRequest request) throws BadRequestHttpException {
        try {
            mHttpGate.postWithSettings(GROUP.toString(), mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new BadRequestHttpException();
        } catch (FailureHttpException e) {
            throw new BadRequestHttpException();
        }
    }

    public void updateDevice(final String deviceId, final UpdateDeviceRequest request)
            throws NotFoundHttpException {
        try {
            mHttpGate.putWithSettings(GROUP.toString() + deviceId, mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void deleteDevice(final String deviceId) throws FailureHttpException {
        try {
            mHttpGate.deleteWithSettings(GROUP.toString() + deviceId).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
        } catch (FailureHttpException ignored) {
        }
    }

    public void manualWatering(final String deviceId, final ManualWateringDeviceRequest request)
            throws NotFoundHttpException, BadRequestHttpException {
        try {
            mHttpGate.postWithSettings(GROUP.toString() + deviceId, mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            if (e instanceof BadRequestHttpException) {
                throw e;
            } else {
                throw new NotFoundHttpException();
            }
        }
    }
}
