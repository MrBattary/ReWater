package michael.linker.rewater.data.web.api.common;

import michael.linker.rewater.config.BuildConfiguration;
import michael.linker.rewater.data.web.gate.HttpGateFailureException;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.HttpUrl;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.data.web.gate.exceptions.FailureHttpException;

public class CommonApi {
    private final IHttpGate mHttpGate;

    public CommonApi() {
        mHttpGate = HttpGateProvider.getHttpGate();
    }

    public boolean pingInternet() throws HttpGateFailureException, FailureHttpException {
        try {
            if (BuildConfiguration.isInternetHealthCheckEnabled()) {
                mHttpGate.get(
                        HttpUrl.Builder(HttpUrl.Protocol.HTTP)
                                .addCore(HttpUrl.Core.PING).buildUrl()
                ).close();
            }
            mHttpGate.getStatusObserver().notifyInternetAccessible();
            return true;
        } catch (HttpGateFailureException | FailureHttpException e) {
            mHttpGate.getStatusObserver().notifyInternetNotAccessible();
            return false;
        }
    }

    public boolean pingServer() throws HttpGateFailureException, FailureHttpException {
        try {
            if (BuildConfiguration.isServerHealthCheckEnabled()) {
                mHttpGate.getWithSettings(HttpUrl.Group.NETWORKS.toString()).close();
            }
            mHttpGate.getStatusObserver().notifyServerAccessible();
            return true;
        } catch (HttpGateFailureException | FailureHttpException e) {
            mHttpGate.getStatusObserver().notifyServerNotAccessible();
            return false;
        }
    }
}
