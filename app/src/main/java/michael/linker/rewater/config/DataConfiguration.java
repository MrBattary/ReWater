package michael.linker.rewater.config;

import michael.linker.rewater.data.web.DevicesLocalData;
import michael.linker.rewater.data.web.IDevicesData;
import michael.linker.rewater.data.web.INetworksData;
import michael.linker.rewater.data.web.ISchedulesData;
import michael.linker.rewater.data.web.NetworksLocalData;
import michael.linker.rewater.data.web.SchedulesLocalData;
import michael.linker.rewater.data.web.links.IOneToManyDataLink;
import michael.linker.rewater.data.web.links.NetworkToSchedulesLocalDataLink;
import michael.linker.rewater.data.web.links.ScheduleToDevicesLocalDataLink;

public class DataConfiguration {
    private static INetworksData sNetworksData;
    private static IDevicesData sDevicesData;
    private static ISchedulesData sSchedulesData;
    private static IOneToManyDataLink sNetworkToSchedulesDataLink;
    private static IOneToManyDataLink sScheduleToDevicesDataLink;

    public static INetworksData getNetworksData() {
        if (sNetworksData == null) {
            sNetworksData = new NetworksLocalData();
        }
        return sNetworksData;
    }

    public static IDevicesData getDevicesData() {
        if (sDevicesData == null) {
            sDevicesData = new DevicesLocalData();
        }
        return sDevicesData;
    }

    public static ISchedulesData getSchedulesData() {
        if (sSchedulesData == null) {
            sSchedulesData = new SchedulesLocalData();
        }
        return sSchedulesData;
    }

    public static IOneToManyDataLink getNetworkToSchedulesDataLink() {
        if (sNetworkToSchedulesDataLink == null) {
            sNetworkToSchedulesDataLink = new NetworkToSchedulesLocalDataLink();
        }
        return sNetworkToSchedulesDataLink;
    }

    public static IOneToManyDataLink getScheduleToDevicesDataLink() {
        if (sScheduleToDevicesDataLink == null) {
            sScheduleToDevicesDataLink = new ScheduleToDevicesLocalDataLink();
        }
        return sScheduleToDevicesDataLink;
    }
}
