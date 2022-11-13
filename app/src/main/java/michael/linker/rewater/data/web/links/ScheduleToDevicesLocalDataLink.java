package michael.linker.rewater.data.web.links;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleToDevicesLocalDataLink extends OneToManyDataLink {

    public ScheduleToDevicesLocalDataLink() {
        super(new HashMap<>(Map.of(
                "c0382b72-d6c1-488e-bd6c-b758c93947f1",
                List.of("a2726b77-2214-4aa2-b4af-2fac0a4f84bc",
                        "832b9239-b362-47c1-bfbc-96589bcad643"),
                "2078faea-1db6-46f2-b7d7-bb350646417c",
                List.of("794fa505-1cdc-480e-b198-e062a36e8fa7"),
                "ad567f2d-10ce-45e2-8fa5-a58ffa14bc1a",
                List.of("353f5967-c7bd-4c57-8528-4f1ab7b88005")
        )));
    }
}
