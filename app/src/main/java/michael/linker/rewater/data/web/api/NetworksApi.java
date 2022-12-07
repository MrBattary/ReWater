package michael.linker.rewater.data.web.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.api.model.request.CreateOrUpdateNetworkRequest;
import michael.linker.rewater.data.web.api.model.response.GetNetworkResponse;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;
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

    public List<GetNetworkResponse> getAllNetworks() throws FailureHttpException {
        Response response = mHttpGate.getWithSettings(GROUP.toString());
        Type networksListType = new TypeToken<ArrayList<GetNetworkResponse>>() {
        }.getType();
        try (ResponseBody responseBody = response.body()) {
            return mGson.fromJson(responseBody.charStream(), networksListType);
        }
    }

    public GetNetworkResponse getNetworkById(final String networkId) throws FailureHttpException {
        Response response = mHttpGate.getWithSettings(GROUP.toString() + networkId);
        try (ResponseBody responseBody = response.body()) {
            return mGson.fromJson(responseBody.charStream(), GetNetworkResponse.class);
        }
    }

    public void createNetwork(final CreateOrUpdateNetworkRequest request)
            throws FailureHttpException {
        mHttpGate.postWithSettings(GROUP.toString(), mGson.toJson(request)).close();
    }

    public void updateNetwork(final String networkId, final CreateOrUpdateNetworkRequest request)
            throws FailureHttpException {
        mHttpGate.putWithSettings(GROUP.toString() + networkId, mGson.toJson(request)).close();
    }

    public void deleteNetworkById(final String networkId) throws FailureHttpException {
        mHttpGate.deleteWithSettings(GROUP.toString() + networkId).close();
    }
}
