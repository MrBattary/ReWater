package michael.linker.rewater.data.local.stub.links;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkToSchedulesLocalDataLink extends OneToManyDataLink {

    public NetworkToSchedulesLocalDataLink() {
        super(new HashMap<>(Map.of(
                "9feefdbe-f35a-4ccb-93dc-0baf6955805b",
                List.of("c0382b72-d6c1-488e-bd6c-b758c93947f1",
                        "2078faea-1db6-46f2-b7d7-bb350646417c"),
                "ca23b691-2141-4395-b560-c79d6d71ff46",
                List.of("ad567f2d-10ce-45e2-8fa5-a58ffa14bc1a")
        )));
    }
}
