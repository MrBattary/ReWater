package michael.linker.rewater.data.web.api;

import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpGateSettings;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import okhttp3.Response;

public class CommonApi {
    private final IHttpGate mHttpGate;

    public CommonApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
    }

    public void pingInternet() {
        HttpGateSettings savedSettings = mHttpGate.getSettings();
        mHttpGate.setNewSettings(new HttpGateSettings(HttpUrl.Protocol.HTTPS, HttpUrl.Core.GOOGLE));
        try {
            Response response = mHttpGate.get("");
            response.code();
        } finally {
            mHttpGate.setNewSettings(savedSettings);
        }
    }

    public void pingServer() {
        Response response = mHttpGate.get(HttpUrl.Group.NETWORKS.toString());
        response.code();
    }
}
