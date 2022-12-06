package michael.linker.rewater.data.web.gate;

public class HttpGateSettings {
    private final HttpUrl.Protocol mProtocol;
    private final HttpUrl.Core mCore;

    public HttpGateSettings(HttpUrl.Protocol protocol, HttpUrl.Core core) {
        mProtocol = protocol;
        mCore = core;
    }

    public HttpUrl.Protocol getProtocol() {
        return mProtocol;
    }

    public HttpUrl.Core getCore() {
        return mCore;
    }

    public String getConstructedUrl() {
        return mProtocol.toString() + mCore.toString();
    }
}
