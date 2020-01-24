package interpret;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ObjectManager {
    // ObjectCreator が作成したオブジェクトの管理をするクラス．
    // Creator が作ったオブジェクトを保持する．
    private final Map<String, Optional> objectStore;

    ObjectManager() {
        objectStore = new HashMap<>();
    }

    void createAndSave(final String name) {
        Optional o = ObjectCreator.createInstanceByNoArgument(name);
        objectStore.put(name, o);
    }

    Optional<Object> getObjectByName(final String name) {
        return objectStore.get(name);
    }

    Optional<String> getNameByName(final String name) {
        if (objectStore.containsKey(name)) return Optional.of(name);
        return Optional.empty();
    }
}
