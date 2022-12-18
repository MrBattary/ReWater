package michael.linker.rewater.data.web.gate;

import michael.linker.rewater.config.BuildConfiguration;

public class HttpGateProvider {
    private static IHttpGate sHttpGate;

    public static IHttpGate getHttpGate() {
        if (sHttpGate == null) {
            sHttpGate = new HttpGate();
            if (BuildConfiguration.getServerProtocol() == BuildConfiguration.ServerProtocol.HTTP) {
                sHttpGate.setNewSettings(
                        new HttpGateSettings(HttpUrl.Protocol.HTTP, HttpUrl.Core.CORE));
            }
            if (BuildConfiguration.getServerProtocol() == BuildConfiguration.ServerProtocol.HTTPS) {
                sHttpGate.setNewSettings(
                        new HttpGateSettings(HttpUrl.Protocol.HTTPS, HttpUrl.Core.CORE));
            }
        }
        return sHttpGate;
    }
}
