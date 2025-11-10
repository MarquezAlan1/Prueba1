package ArcaLtda.org_service.service;

import ArcaLtda.org_service.model.Unit;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrgService {

    // in-memory store: id -> Unit
    private final Map<String, Unit> store = new ConcurrentHashMap<>();

    public OrgService() {
        // seed root unit (opcional)
        Unit root = new Unit("Company", null);
        store.put(root.getId(), root);
    }

    public Unit createUnit(String name, String parentId) {
        if (parentId != null && parentId.length() > 0 && !store.containsKey(parentId)) {
            throw new IllegalArgumentException("Parent unit not found: " + parentId);
        }
        Unit unit = new Unit(name, parentId);
        store.put(unit.getId(), unit);
        return unit;
    }

    public List<Unit> listUnitsFlat() {
        return new ArrayList<>(store.values());
    }

    public List<Unit> listUnitsTree() {
        // create shallow copies to avoid mutating originals children
        Map<String, Unit> copy = new HashMap<>();
        for (Unit u : store.values()) {
            Unit c = new Unit();
            c.setId(u.getId());
            c.setName(u.getName());
            c.setParentId(u.getParentId());
            c.setManagerId(u.getManagerId());
            copy.put(c.getId(), c);
        }

        List<Unit> roots = new ArrayList<>();
        for (Unit u : copy.values()) {
            if (u.getParentId() == null) {
                roots.add(u);
            } else {
                Unit parent = copy.get(u.getParentId());
                if (parent != null) parent.getChildren().add(u);
                else roots.add(u); // broken parent -> treat as root
            }
        }
        return roots;
    }

    public Unit assignManager(String unitId, String managerId) {
        Unit u = store.get(unitId);
        if (u == null) throw new NoSuchElementException("Unit not found: " + unitId);
        u.setManagerId(managerId);
        return u;
    }

    public Unit getById(String id) {
        return store.get(id);
    }
}
