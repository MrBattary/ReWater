package michael.linker.rewater.config;

import michael.linker.rewater.data.local.stub.DevicesLocalData;
import michael.linker.rewater.data.local.stub.IDevicesData;
import michael.linker.rewater.data.local.stub.INetworksData;
import michael.linker.rewater.data.local.stub.ISchedulesData;
import michael.linker.rewater.data.local.stub.NetworksLocalData;
import michael.linker.rewater.data.local.stub.SchedulesLocalData;
import michael.linker.rewater.data.local.stub.links.IOneToManyDataLink;
import michael.linker.rewater.data.local.stub.links.NetworkToSchedulesLocalDataLink;
import michael.linker.rewater.data.local.stub.links.ScheduleToDevicesLocalDataLink;

public class StubDataConfiguration {
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
