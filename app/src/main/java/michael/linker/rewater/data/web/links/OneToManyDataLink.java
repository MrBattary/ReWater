package michael.linker.rewater.data.web.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OneToManyDataLink implements IOneToManyDataLink {
    Map<String, List<String>> oneToManyMap;

    public OneToManyDataLink(final Map<String, List<String>> map) {
        oneToManyMap = map;
    }

    @Override
    public void addDataLink(String leftEntityId, String rightEntityId) {
        if (!oneToManyMap.containsKey(leftEntityId)) {
            oneToManyMap.put(leftEntityId, List.of(rightEntityId));
        } else {
            final List<String> scheduleIdList = oneToManyMap.get(leftEntityId);
            scheduleIdList.add(rightEntityId);
            oneToManyMap.replace(leftEntityId, scheduleIdList);
        }
    }

    @Override
    public List<String> getLeftEntityIdList() {
        return new ArrayList<>(oneToManyMap.keySet());
    }

    @Override
    public List<String> getRightEntityIdListByLeftEntityId(String leftEntityId) {
        return oneToManyMap.get(leftEntityId);
    }

    @Override
    public String getLeftEntityIdByRightEntityId(String rightEntityId) {
        for (Map.Entry<String, List<String>> entry : oneToManyMap.entrySet()) {
            if (entry.getValue().contains(rightEntityId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void removeAllRightEntityIdsByLeftEntityId(String leftEntityId) {
        oneToManyMap.remove(leftEntityId);
    }

    @Override
    public void removeRightEntityId(String rightEntityId) {
        for (Map.Entry<String, List<String>> entry : oneToManyMap.entrySet()) {
            final List<String> scheduleIdList = entry.getValue();
            if (scheduleIdList.contains(rightEntityId)) {
                scheduleIdList.removeIf(id -> id.equals(rightEntityId));
                oneToManyMap.replace(entry.getKey(), scheduleIdList);
            }
        }
    }
}
