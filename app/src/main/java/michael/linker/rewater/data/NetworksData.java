package michael.linker.rewater.data;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.model.network.NetworkItemModel;
import michael.linker.rewater.model.network.NetworksModel;
import michael.linker.rewater.model.status.StatusModel;
import michael.linker.rewater.constant.Status;

public class NetworksData {
    public NetworksModel getNetworks() {
        return new NetworksModel(new ArrayList<>(List.of(
                new NetworkItemModel("1", "Test1", "test1",
                        new StatusModel(Status.OK, Status.OK)),
                new NetworkItemModel("2", "Test2", "test1",
                        new StatusModel(Status.OK, Status.WARNING)),
                new NetworkItemModel("3", "Test3", "test1",
                        new StatusModel(Status.WARNING, Status.DEFECT)))));
    }
}
