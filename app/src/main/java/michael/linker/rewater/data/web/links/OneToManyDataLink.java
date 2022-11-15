package michael.linker.rewater.data.web.links;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class OneToManyDataLink implements IOneToManyDataLink {
    Map<String, List<String>> oneToManyMap;

    public OneToManyDataLink(final Map<String, List<String>> map) {
        oneToManyMap = map;
    }

    @Override
    public void addDataLink(@NonNull String leftEntityId, @NonNull String rightEntityId) {
        if (!oneToManyMap.containsKey(leftEntityId)) {
            oneToManyMap.put(leftEntityId, List.of(rightEntityId));
        } else {
            final List<String> scheduleIdList = new ArrayList<>(
                    Objects.requireNonNull(oneToManyMap.get(leftEntityId)));
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
        return oneToManyMap.getOrDefault(leftEntityId, new ArrayList<>());
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
                oneToManyMap.replace(
                        entry.getKey(),
                        new ArrayList<>(oneToManyMap.get(entry.getKey())
                                .stream()
                                .filter(id -> !id.equals(rightEntityId))
                                .collect(Collectors.toList()
                                ))
                );
            }
        }
    }
}
