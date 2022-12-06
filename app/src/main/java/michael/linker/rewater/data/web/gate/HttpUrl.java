package michael.linker.rewater.data.web.gate;

import androidx.annotation.NonNull;

public class HttpUrl {
    public static class Protocol {
        public static final Protocol HTTP;
        public static final Protocol HTTPS;

        private static final String HTTP_PROTOCOL = "http://";
        private static final String HTTPS_PROTOCOL = "https://";

        static {
            HTTP = new Protocol(HTTP_PROTOCOL);
            HTTPS = new Protocol(HTTPS_PROTOCOL);
        }

        private final String mProtocol;

        private Protocol(String protocol) {
            mProtocol = protocol;
        }

        @NonNull
        @Override
        public String toString() {
            return mProtocol;
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

        private final String mCore;

        public Core(String core) {
            mCore = core;
        }

        @NonNull
        @Override
        public String toString() {
            return mCore;
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

        private final String mGroup;

        private Group(String group) {
            mGroup = group;
        }

        @NonNull
        @Override
        public String toString() {
            return mGroup;
        }
    }
}
