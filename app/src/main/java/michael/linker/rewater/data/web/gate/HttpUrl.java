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

        private static final String HTTP_PROTOCOL = "http:/";
        private static final String HTTPS_PROTOCOL = "https:/";

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

        private static final String GOOGLE_URL = "/google.com";
        public static final String ANDROID_LOCAL_URL = "/10.0.2.2";
        public static final String LOCAL_URL = "/192.168.0.205";
        public static final String API_URL = "/api";

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
        private final Query mQuery;

        public CoreBuilder(Core core) {
            super(core.mCore);
            mQuery = new Query();
        }

        public CoreBuilder(String core) {
            super(core);
            mQuery = new Query();
        }

        public CoreBuilder(String core, Query query) {
            super(core);
            mQuery = query;
        }

        public CoreBuilder addCore(Core core) {
            return new CoreBuilder(this.mCore + core.mCore, mQuery);
        }

        public GroupBuilder addGroup(Group group) {
            return new GroupBuilder(this.mCore + group.mGroup, mQuery);
        }

        public CoreBuilder addQueryParameter(Query.Param key, Query.Value value) {
            mQuery.addQueryParameter(key, value);
            return this;
        }

        public String buildUrl() {
            return mCore + mQuery.toString();
        }
    }

    public static class Group {
        public static final Group NETWORKS;
        public static final Group SCHEDULES;
        public static final Group DEVICES;

        private static final String NETWORKS_GROUP = "/networks";
        private static final String SCHEDULES_GROUP = "/schedules";
        private static final String DEVICES_GROUP = "/devices";

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
        private final Query mQuery;

        public GroupBuilder(Group group) {
            super(group.mGroup);
            mQuery = new Query();
        }

        public GroupBuilder(String group, Query query) {
            super(group);
            mQuery = query;
        }

        public String buildUrl() {
            return mGroup + mQuery.toString();
        }

        public GroupBuilder addQueryParameter(Query.Param key, Query.Value value) {
            mQuery.addQueryParameter(key, value);
            return this;
        }
    }

    public static class Query {

        private final Map<String, String> queryParams;

        public Query() {
            queryParams = new HashMap<>();
        }

        public Query addQueryParameter(Param key, Value value) {
            queryParams.put(key.toString(), value.toString());
            return this;
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

        public static class Param {
            public static final Param NETWORK_ID;
            public static final Param DEVICE_ID;
            public static final Param DEVICE_HARDCODED_ID;
            public static final Param DEVICES_FULL_INFO;
            public static final Param DEVICES_ATTACHABLE;

            private static final String NETWORK_ID_PARAM = "network_id";
            private static final String DEVICE_ID_PARAM = "device_id";
            private static final String DEVICE_HARDCODED_ID_PARAM = "hardcoded_id";
            private static final String DEVICES_FULL_INFO_PARAM = "full_info";
            private static final String DEVICES_ATTACHABLE_PARAM = "attachable";

            static {
                NETWORK_ID = new Param(NETWORK_ID_PARAM);
                DEVICE_ID = new Param(DEVICE_ID_PARAM);
                DEVICE_HARDCODED_ID = new Param(DEVICE_HARDCODED_ID_PARAM);
                DEVICES_FULL_INFO = new Param(DEVICES_FULL_INFO_PARAM);
                DEVICES_ATTACHABLE = new Param(DEVICES_ATTACHABLE_PARAM);
            }

            private final String mParam;

            private Param(String param) {
                mParam = param;
            }

            @NonNull
            @Override
            public String toString() {
                return mParam;
            }
        }

        public static class Value {
            public static final Value BOOL_TRUE;
            public static final Value BOOL_FALSE;

            private static final String BOOL_TRUE_VALUE = "true";
            private static final String BOOL_FALSE_VALUE = "false";

            static {
                BOOL_TRUE = new Value(BOOL_TRUE_VALUE);
                BOOL_FALSE = new Value(BOOL_FALSE_VALUE);
            }

            private final String mValue;

            public Value(String value) {
                mValue = value;
            }

            @NonNull
            @Override
            public String toString() {
                return mValue;
            }
        }
    }
}
