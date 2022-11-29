package michael.linker.rewater.data.local.stub.links;

import java.util.List;

public interface IOneToManyDataLink {
    void addDataLink(String leftEntityId, String rightEntityId);

    List<String> getLeftEntityIdList();

    List<String> getRightEntityIdListByLeftEntityId(String leftEntityId);

    String getLeftEntityIdByRightEntityId(String rightEntityId);

    void removeAllRightEntityIdsByLeftEntityId(String leftEntityId);

    void removeRightEntityId(String rightEntityId);
}
