package michael.linker.rewater.data.web.gate;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    public static ProtocolBuilder Builder(Protocol protocol) {
        return new ProtocolBuilder(protocol.mProtocol);
    }

    public static class Protocol {
        public static final Protocol HTTP;
        public static final Protocol HTTPS;

        private static final String HTTP_PROTOCOL = "http://";
        private static final String HTTPS_PROTOCOL = "https://";

        static {
            HTTP = new Protocol(HTTP_PROTOCOL);
            HTTPS = new Protocol(HTTPS_PROTOCOL);
        }

        protected final String mProtocol;

        private Protocol(String protocol) {
            mProtocol = protocol;
        }

        @NonNull
        @Override
        public String toString() {
            return mProtocol;
        }
    }

    public static class ProtocolBuilder extends Protocol {

        public ProtocolBuilder(String protocol) {
            super(protocol);
        }

        public CoreBuilder addCore(Core core) {
            return new CoreBuilder(this.mProtocol + core.mCore);
        }
    }

    public static class Core {
        public static final Core GOOGLE;
        public static Core CORE;

        private static final String GOOGLE_URL = "google.com/";
        public static final String ANDROID_CORE_URL = "10.0.2.2/";
        public static final String LOCAL_CORE_URL = "rewater.local/";
        public static final String API_URL = "api/";

        static {
            GOOGLE = new Core(GOOGLE_URL);
        }

        protected final String mCore;

        public Core(String core) {
            mCore = core;
        }

        @NonNull
        @Override
        public String toString() {
            return mCore;
        }
    }

    public static class CoreBuilder extends Core {
        private final QueryParams mQueryParams;

        public CoreBuilder(String core) {
            super(core);
            mQueryParams = new QueryParams();
        }

        public GroupBuilder addGroup(Group group) {
            return new GroupBuilder(this.mCore + group, mQueryParams);
        }

        public void addQueryParameter(Object key, Object value) {
            mQueryParams.addQueryParameter(key, value);
        }

        public String buildUrl() {
            return mCore + mQueryParams.toString();
        }
    }

    public static class Group {
        public static final Group NETWORKS;
        public static final Group SCHEDULES;
        public static final Group DEVICES;

        private static final String NETWORKS_GROUP = "networks/";
        private static final String SCHEDULES_GROUP = "schedules/";
        private static final String DEVICES_GROUP = "devices/";

        static {
            NETWORKS = new Group(NETWORKS_GROUP);
            SCHEDULES = new Group(SCHEDULES_GROUP);
            DEVICES = new Group(DEVICES_GROUP);
        }

        protected final String mGroup;

        private Group(String group) {
            mGroup = group;
        }

        @NonNull
        @Override
        public String toString() {
            return mGroup;
        }
    }

    public static class GroupBuilder extends Group {
        private final QueryParams mQueryParams;

        public GroupBuilder(String group, QueryParams queryParams) {
            super(group);
            mQueryParams = queryParams;
        }

        public String buildUrl() {
            return mGroup;
        }

        public void addQueryParameter(Object key, Object value) {
            mQueryParams.addQueryParameter(key, value);
        }
    }

    public static class QueryParams {
        private final Map<String, String> queryParams;

        public QueryParams() {
            queryParams = new HashMap<>();
        }

        public void addQueryParameter(Object key, Object value) {
            queryParams.put(key.toString(), value.toString());
        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("?");
            for (var queryEntry : queryParams.entrySet()) {
                stringBuilder
                        .append(queryEntry.getKey())
                        .append("=")
                        .append(queryEntry.getValue())
                        .append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }
    }
}
