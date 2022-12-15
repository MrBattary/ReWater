package michael.linker.rewater.data.web.gate;

import michael.linker.rewater.config.BuildConfiguration;

public class HttpGateProvider {
    private static IHttpGate sHttpGate;

    public static IHttpGate getHttpGate() {
        if (sHttpGate == null) {
            sHttpGate = new HttpGate();
            final HttpUrl.Core core = new HttpUrl.Core(
                    HttpUrl.Core.SERVER_URL + HttpUrl.Core.API_URL);
            if (BuildConfiguration.getServerProtocol() == BuildConfiguration.ServerProtocol.HTTP) {
                sHttpGate.setNewSettings(new HttpGateSettings(HttpUrl.Protocol.HTTP, core));
            }
            if (BuildConfiguration.getServerProtocol() == BuildConfiguration.ServerProtocol.HTTPS) {
                sHttpGate.setNewSettings(new HttpGateSettings(HttpUrl.Protocol.HTTPS, core));
            }
        }
        return sHttpGate;
    }
}
