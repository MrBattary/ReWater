package michael.linker.rewater.data.web.api;

import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;

public class CommonApi {
    private final IHttpGate mHttpGate;

    public CommonApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
    }

    public void pingInternet() throws FailureHttpException {
        mHttpGate.get(
                HttpUrl.Builder(HttpUrl.Protocol.HTTP)
                        .addCore(HttpUrl.Core.GOOGLE).buildUrl()
        ).close();
    }

    public void pingServer() throws FailureHttpException {
        mHttpGate.getWithSettings(HttpUrl.Group.NETWORKS.toString()).close();
    }
}
