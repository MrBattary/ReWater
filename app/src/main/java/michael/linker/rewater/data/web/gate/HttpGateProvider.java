package michael.linker.rewater.data.web.gate;

public class HttpGateProvider {
    private static IHttpGate sHttpGate;

    public static IHttpGate getHttpGate() {
        if (sHttpGate == null) {
            sHttpGate = new HttpGate(
                    new HttpGateSettings(
                            HttpUrl.Protocol.HTTP,
                            new HttpUrl.Core(HttpUrl.Core.LOCAL_URL + HttpUrl.Core.API_URL)
                    ));
        }
        return sHttpGate;
    }
}
