package ArcaLtda.org_service.util;

import ArcaLtda.org_service.model.Unit;

import java.util.*;

public class TreeBuilder {

    public static List<Unit> buildTree(Collection<Unit> units) {
        Map<String, Unit> copy = new HashMap<>();
        for (Unit u : units) {
            Unit c = new Unit();
            c.setId(u.getId());
            c.setName(u.getName());
            c.setParentId(u.getParentId());
            c.setManagerId(u.getManagerId());
            copy.put(c.getId(), c);
        }

        List<Unit> roots = new ArrayList<>();
        for (Unit u : copy.values()) {
            if (u.getParentId() == null) roots.add(u);
            else {
                Unit parent = copy.get(u.getParentId());
                if (parent != null) parent.getChildren().add(u);
                else roots.add(u);
            }
        }
        return roots;
    }
}
