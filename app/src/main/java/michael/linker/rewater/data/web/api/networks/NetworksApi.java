package michael.linker.rewater.data.web.api.networks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.api.networks.request.CreateOrUpdateNetworkRequest;
import michael.linker.rewater.data.web.api.networks.response.GetNetworkResponse;
import michael.linker.rewater.data.web.gate.HttpGateFailureException;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworksApi {
    private static final HttpUrl.Group GROUP = HttpUrl.Group.NETWORKS;
    private final IHttpGate mHttpGate;
    private final Gson mGson;

    public NetworksApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
        mGson = new Gson();
    }

    public List<GetNetworkResponse> getAllNetworks() {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString());
            Type networksListType = new TypeToken<ArrayList<GetNetworkResponse>>() {
            }.getType();
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), networksListType);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return new ArrayList<>();
        } catch (FailureHttpException e) {
            return new ArrayList<>();
        }
    }

    public GetNetworkResponse getNetworkById(final String networkId) throws NotFoundHttpException {
        try {
            Response response = mHttpGate.getWithSettings(GROUP.toString() + networkId);
            try (ResponseBody responseBody = response.body()) {
                return mGson.fromJson(responseBody.charStream(), GetNetworkResponse.class);
            }
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void createNetwork(final CreateOrUpdateNetworkRequest request)
            throws BadRequestHttpException {
        try {
            mHttpGate.postWithSettings(GROUP.toString(), mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new BadRequestHttpException();
        } catch (FailureHttpException e) {
            throw new BadRequestHttpException();
        }
    }

    public void updateNetwork(final String networkId, final CreateOrUpdateNetworkRequest request)
            throws NotFoundHttpException {
        try {
            mHttpGate.putWithSettings(GROUP.toString() + networkId, mGson.toJson(request)).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            throw new NotFoundHttpException();
        } catch (FailureHttpException e) {
            throw new NotFoundHttpException();
        }
    }

    public void deleteNetworkById(final String networkId) {
        try {
            mHttpGate.deleteWithSettings(GROUP.toString() + networkId).close();
        } catch (HttpGateFailureException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
        } catch (FailureHttpException ignored) {
        }
    }
}
